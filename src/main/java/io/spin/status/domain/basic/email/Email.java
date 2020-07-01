package io.spin.status.domain.basic.email;

import io.spin.status.enumeration.EmailType;
import lombok.Data;

@Data
public class Email {

    @javax.validation.constraints.Email
    private String to;
    private String subject;
    private String body;
    private EmailType type;

}
