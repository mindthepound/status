package io.spin.status.controller;

import io.spin.status.domain.basic.admin.Signup;
import io.spin.status.domain.dynamoDB.admin.Admin;
import io.spin.status.dto.Result;
import io.spin.status.enumeration.Roles;
import io.spin.status.service.admin.AdminService;
import io.spin.status.util.DateUtil;
import io.spin.status.util.PasswordUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Slf4j
@Controller
public class LoginController {

    private AdminService adminService;

    private PasswordUtil passwordUtil;
    private DateUtil dateUtil;

    @Autowired
    public LoginController(
            AdminService adminService,
            PasswordUtil passwordUtil,
            DateUtil dateUtil
    ) {
        this.adminService = adminService;
        this.passwordUtil = passwordUtil;
        this.dateUtil = dateUtil;
    }

    @ApiOperation(value = "로그인 페이지 확인", notes = "로그인 페이지를 확인합니다.")
    @GetMapping("/signin")
    public String signinView() {
        return "signin";
    }

    @ApiOperation(value = "가입 페이지 확인", notes = "가입 페이지를 확인합니다.\n최초 가입에만 사용합니다.")
    @GetMapping("/signup")
    public String signupView() {
        return "signup";
    }

    @ApiOperation(value = "가입 처리", notes = "가입 처리합니다.\n최초 가입에만 사용합니다.\nSUPER 권한을 부여받습니다.")
    @PostMapping("/signup")
    public Result signup(@ModelAttribute Signup signup) {

        Admin admin = new Admin(
                signup.getId(),
                passwordUtil.encryptPassword(new BCryptPasswordEncoder(), signup.getPw()),
                signup.getName(),
                Roles.SUPER.getRole() + "/" + Roles.ADMIN.getRole(),
                dateUtil.toISOString(new Date())
        );

        return adminService.signupAdmin(admin);
    }

    @ApiOperation(value = "권한없음 페이지 확인", notes = "권한없음 페이지를 확인합니다.")
    @GetMapping("/permission-error")
    public String permissionError() {
        return "errors/permission_error";
    }

}
