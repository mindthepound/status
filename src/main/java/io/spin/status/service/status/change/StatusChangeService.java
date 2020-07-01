package io.spin.status.service.status.change;

import io.spin.status.domain.dynamoDB.status.server.StatusServerAlarmUser;
import io.spin.status.domain.dynamoDB.status.server.StatusServerStatusChange;
import io.spin.status.domain.dynamoDB.status.server.StatusServerSyncUser;
import io.spin.status.domain.dynamoDB.status.status.*;
import io.spin.status.dto.Result;

public interface StatusChangeService {

    // save log
    Result saveStatusChange(StatusServerStatusChange statusServerStatusChange);
    // add user
    Result saveAddUser(StatusServerSyncUser statusServerSyncUser);
    // add Phone
    Result saveAddPhone(StatusServerAlarmUser statusServerAlarmUser);
    // add MsgSb
    Result saveInsertMsgLog(MsgSb msgSb);
    // add MsgSend
    Result saveInsertMsgsendLog(MsgSend msgSend);
    // delete
    Result deleteStatusChange(StatusServerStatusChange statusServerStatusChange);
}
