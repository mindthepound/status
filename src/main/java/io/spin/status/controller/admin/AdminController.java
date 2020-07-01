package io.spin.status.controller.admin;

import io.spin.status.domain.basic.admin.Signup;
import io.spin.status.domain.dynamoDB.admin.Admin;
import io.spin.status.dto.DynamoDBSearchParam;
import io.spin.status.dto.Result;
import io.spin.status.service.admin.AdminService;
import io.spin.status.util.DateUtil;
import io.spin.status.util.PasswordUtil;
import io.spin.status.util.SessionUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    private PasswordUtil passwordUtil;
    private SessionUtil sessionUtil;
    private DateUtil dateUtil;

    @Autowired
    public AdminController(
            AdminService adminService,
            PasswordUtil passwordUtil,
            SessionUtil sessionUtil,
            DateUtil dateUtil
    ) {
        this.adminService = adminService;
        this.passwordUtil = passwordUtil;
        this.sessionUtil = sessionUtil;
        this.dateUtil = dateUtil;
    }

    @ApiOperation(value = "관리자 리스트 페이지", notes = "슈퍼 관리자만 가능합니다.")
    @RolesAllowed("ROLE_SUPER")
    @GetMapping("/list")
    public String listView(
            Model model,
            @ModelAttribute DynamoDBSearchParam dynamoDBSearchParam
    ) {
        model.addAttribute("result", adminService.adminList(dynamoDBSearchParam));
        return "admin/list";
    }

    @ApiOperation(value = "관리자 생성 페이지", notes = "슈퍼 관리자만 가능합니다.")
    @RolesAllowed("ROLE_SUPER")
    @GetMapping("/create")
    public String createView() {
        return "admin/create";
    }

    @ApiOperation(value = "관리자 생성", notes = "슈퍼 관리자만 가능합니다.")
    @RolesAllowed("ROLE_SUPER")
    @PostMapping("/create")
    public @ResponseBody
    Result adminCreate(
            @RequestBody Signup signup
    ) {
        Admin admin = new Admin(
                signup.getId(),
                passwordUtil.encryptPassword(new BCryptPasswordEncoder(), signup.getPw()),
                signup.getName(),
                signup.getRole(),
                dateUtil.toISOString(new Date())
        );

        return adminService.signupAdmin(admin);
    }

    @ApiOperation(value = "관리자 수정", notes = "슈퍼 관리자만 가능합니다.")
    @RolesAllowed("ROLE_SUPER")
    @PatchMapping("/{id}")
    public @ResponseBody
    Result adminUpdate(
            @PathVariable String id,
            @RequestBody String body
    ) {
        return adminService.updateAdmin(id, body);
    }

    @ApiOperation(value = "관리자 삭제", notes = "관리자를 삭제합니다.\n슈퍼관리자 권한을 갖은 사람만 가능 합니다.")
    @RolesAllowed("ROLE_SUPER")
    @DeleteMapping("/admin")
    public @ResponseBody
    Result adminDelete(
            @RequestParam String id
    ) {
        return adminService.deleteAdmin(id);
    }

    @ApiOperation(value = "내 프로필 확인 페이지", notes = "비밀번호 변경을 위해 사용합니다.")
    @RolesAllowed({"ROLE_ADMIN","ROLE_AD","ROLE_MOVIGAME"})
    @GetMapping("/me")
    public String profileView(
            Model model
    ) {
        model.addAttribute("result", adminService.getAdmin(sessionUtil.getID()));
        return "admin/profile";
    }

    @ApiOperation(value = "내 프로필 수정", notes = "프로필을 수정합니다.")
    @RolesAllowed({"ROLE_ADMIN","ROLE_AD","ROLE_MOVIGAME"})
    @PutMapping("/me")
    public @ResponseBody
    Result profile(
            @RequestParam String newPassword
    ) {
        return adminService.changePassword(newPassword);
    }

}
