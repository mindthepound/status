package io.spin.status.domain.basic.socketMessage;

import io.spin.status.enumeration.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocketMessage implements Serializable {

    private static final long serialVersionUID = 812327078625454185L;

    private MessageType type;
    private String message;
    private String error;

}
