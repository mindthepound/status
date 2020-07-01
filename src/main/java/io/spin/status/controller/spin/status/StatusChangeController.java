package io.spin.status.controller.spin.status;

import io.spin.status.domain.dynamoDB.status.server.StatusServerAlarmUser;
import io.spin.status.domain.dynamoDB.status.server.StatusServerStatusChange;
import io.spin.status.domain.dynamoDB.status.server.StatusServerSyncUser;
import io.spin.status.domain.dynamoDB.status.status.MsgSb;
import io.spin.status.domain.dynamoDB.status.status.MsgSend;
import io.spin.status.service.status.change.StatusChangeService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/status")
// @RolesAllowed("ROLE_ADMIN")
public class StatusChangeController {

    private StatusChangeService statusChangeService;

    @Autowired
    public StatusChangeController(
            StatusChangeService statusChangeService
    ) {
        this.statusChangeService = statusChangeService;
    }

    @ApiOperation(value = "log 저장", notes = "request log 처리.")
    @PostMapping("/log")
    public ResponseEntity statusChange(
            @RequestBody StatusServerStatusChange statusServerStatusChange
    ) {
        statusChangeService.saveStatusChange(statusServerStatusChange);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /* dynamoDB delete test */
    @ApiOperation(value = "dynamoDB test 삭제", notes = "dynamoDB test 삭제 처리.")
    @PostMapping("/delete")
    public ResponseEntity deleteStatusChange(
            @RequestBody StatusServerStatusChange statusChange
    ) {
        statusChangeService.deleteStatusChange(statusChange);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ApiOperation(value = "user 저장", notes = "user 를 편하게 추가하기 위해.")
    @PostMapping("/addUser")
    public ResponseEntity addUser(
            @RequestBody StatusServerSyncUser statusServerSyncUser
    ) {
        statusChangeService.saveAddUser(statusServerSyncUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ApiOperation(value = "alarm phone 추가", notes = "alarm phone을 편하게 추가하기 위해.")
    @PostMapping("/addPhone")
    public ResponseEntity addPhone(
            @RequestBody StatusServerAlarmUser statusServerAlarmUser
    ) {
        statusChangeService.saveAddPhone(statusServerAlarmUser);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ApiOperation(value = "msgSb Log 저장", notes = "msgSb Log 저장.")
    @PostMapping("/msgsbLog")
    public ResponseEntity insertMsgLog(
            @RequestBody MsgSb msgSb
    ) {
        statusChangeService.saveInsertMsgLog(msgSb);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ApiOperation(value = "msgSend Log 저장 Another methods", notes = "msgSend Log 저장 Another methods 저장.")
    @PostMapping("/msgsendLog")
    public ResponseEntity insertMsgSendLog(
            @RequestBody MsgSend msgSend
    ) {
        statusChangeService.saveInsertMsgsendLog(msgSend);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ApiOperation(value = "healthCheck 추가", notes = "healthCheck.")
    @GetMapping("/healthCheck")
    public ResponseEntity statusChange()
    {
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
