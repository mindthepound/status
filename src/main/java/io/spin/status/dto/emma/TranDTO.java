package io.spin.status.dto.emma;

import lombok.Data;

import java.time.LocalDateTime;

@Data
//@ToString
public class TranDTO {

    private LocalDateTime dateClientReq;
    private String content;
    private String callback;
    private char serviceType;
    private char broadcastYn;
    private char msgStatus;
    private String recipientNum;
    private Integer id;

    public TranDTO() {
    }

    public TranDTO(LocalDateTime dateClientReq, String content, String callback, char serviceType, char broadcastYn, char msgStatus, String recipientNum, Integer id) {
        this.dateClientReq = dateClientReq;
        this.content = content;
        this.callback = callback;
        this.serviceType = serviceType;
        this.broadcastYn = broadcastYn;
        this.msgStatus = msgStatus;
        this.recipientNum = recipientNum;
        this.id = id;

    }

    @Override public String toString() { return new StringBuilder("{ dateClientReq : ").append(dateClientReq).append(", content : ").append(content).append(", callback : ").append(callback)
            .append(", serviceType : ").append(serviceType).append(", broadcastYn : ").append(broadcastYn)
            .append(", msgStatus : ").append(msgStatus).append(", recipientNum : ").append(recipientNum).append(", id: ").append(id)
            .append(" }").toString(); }


}