package io.spin.status.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
// @RolesAllowed({"ROLE_ADMIN","ROLE_AD"})
public class MainController {

    @ApiOperation(value = "health Check 확인", notes = "health Check 확인합니다.")
    @GetMapping("/")
    public ResponseEntity<HttpStatus> mainView() {
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ApiOperation(value = "비밀번호 변경 페이지 확인", notes = "비밀번호 변경 페이지를 확인합니다.")
    @GetMapping("/change-pw")
    public String changePwView() {
        return "changePw";
    }

}
