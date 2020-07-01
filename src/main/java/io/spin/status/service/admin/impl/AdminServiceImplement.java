package io.spin.status.service.admin.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.spin.status.domain.basic.admin.AuthToken;
import io.spin.status.domain.dynamoDB.admin.Admin;
import io.spin.status.dto.DynamoDBScanPageResult;
import io.spin.status.dto.DynamoDBSearchParam;
import io.spin.status.dto.Result;
import io.spin.status.enumeration.ResultCode;
import io.spin.status.enumeration.Roles;
import io.spin.status.repository.dynamoDB.admin.AdminDynamoDBRepository;
import io.spin.status.service.admin.AdminService;
import io.spin.status.util.DateUtil;
import io.spin.status.util.PasswordUtil;
import io.spin.status.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

@Slf4j
@Service
public class AdminServiceImplement implements AdminService {

    private AdminDynamoDBRepository adminDynamoDBRepository;

    private DateUtil dateUtil;
    private PasswordUtil passwordUtil;
    private SessionUtil sessionUtil;

    @Autowired
    public AdminServiceImplement(
            AdminDynamoDBRepository adminDynamoDBRepository,
            DateUtil dateUtil,
            PasswordUtil passwordUtil,
            SessionUtil sessionUtil
    ) {
        this.adminDynamoDBRepository = adminDynamoDBRepository;
        this.dateUtil = dateUtil;
        this.passwordUtil = passwordUtil;
        this.sessionUtil = sessionUtil;
    }

    @Override
    public Admin getAdmin(String id) {
        Admin admin = adminDynamoDBRepository.getAdmin(id);
        admin.setPw("");
        return admin;
    }

    @RolesAllowed("ROLE_SUPER")
    @Override
    public Result updateAdmin(String id, String body) {

        Admin isExistAdmin = adminDynamoDBRepository.getAdmin(id);
        if (isExistAdmin == null) return new Result(ResultCode.E951);

        Admin admin = null;
        try {
            admin = new ObjectMapper().readValue(body, Admin.class);

        } catch (Exception e) {
            return new Result(ResultCode.E999);
        }

        admin.setId(id);

        if (admin.getPw() != null) {
            admin.setLastPasswordChange(dateUtil.toISOString(new Date()));
            admin.setPw(passwordUtil.encryptPassword(new BCryptPasswordEncoder(), admin.getPw()));
        }

        adminDynamoDBRepository.updateAdmin(admin);

        return new Result(ResultCode.OK);
    }

    @Override
    public Result deleteAdmin(String id) {
        adminDynamoDBRepository.deleteAdmin(id);

        return new Result(ResultCode.OK);
    }

    @Override
    public Result changePassword(String newPassword) {

        Admin admin = adminDynamoDBRepository.getAdmin(sessionUtil.getID());
        admin.setPw(passwordUtil.encryptPassword(new BCryptPasswordEncoder(), newPassword));
        admin.setLastPasswordChange(dateUtil.toISOString(new Date()));

        adminDynamoDBRepository.updateAdmin(admin);

        return new Result(ResultCode.OK);
    }

    @Override
    public DynamoDBScanPageResult adminList(DynamoDBSearchParam dynamoDBSearchParam) {
        DynamoDBScanPageResult dynamoDBScanPageResult = new DynamoDBScanPageResult(dynamoDBSearchParam);
        dynamoDBScanPageResult.setPageResult(adminDynamoDBRepository.getAdmins(dynamoDBSearchParam));
        return dynamoDBScanPageResult;
    }

    @Override
    public Result signupAdmin(Admin admin) {

        String[] roles = admin.getRoles().split("/");
        int count = 0;
        boolean isSuper = false;
        boolean isAdmin = false;
        List roleList = new ArrayList(EnumSet.allOf(Roles.class));
        for (String role : roles) {
            role = role.substring(role.indexOf("_")+1);
            for (Object roleSet : roleList) {
                if (role.equalsIgnoreCase(String.valueOf(roleSet))) count++;
                if (role.equalsIgnoreCase(String.valueOf(Roles.SUPER))) isSuper = true;
                if (role.equalsIgnoreCase(String.valueOf(Roles.ADMIN))) isAdmin = true;
            }
        }
        if (isSuper && isAdmin && count != 2) return new Result(ResultCode.E101);
        if (isSuper && !isAdmin && count > 1) return new Result(ResultCode.E101);
        if (!isSuper && count > 1) return new Result(ResultCode.E101);

        Admin checkId = adminDynamoDBRepository.getAdmin(admin.getId());
        if (checkId != null) return new Result(ResultCode.E801);

        adminDynamoDBRepository.createAdmin(admin);

        return new Result(ResultCode.OK);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("loadUserByUserName : " + username);

        Admin admin = adminDynamoDBRepository.getAdmin(username);

        if (admin == null)
            throw new UsernameNotFoundException("Admin Not Found");

        AuthToken authToken = new AuthToken();
        authToken.setName(admin.getName());
        authToken.setUserName(admin.getId());
        authToken.setPassword(admin.getPw());
        authToken.setLastPasswordChange(admin.getLastPasswordChange());

        String[] roles = admin.getRoles().split("/");

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        for (String role : roles) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(role));
        }

        authToken.setAuthorities(grantedAuthorityList);
        authToken.setAdminType(admin.getRoles());

        return authToken;
    }
}
