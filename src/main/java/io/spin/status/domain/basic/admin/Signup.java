package io.spin.status.domain.basic.admin;

import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

import javax.validation.constraints.Email;

@Data
public class Signup implements Serializable {

    private static final long serialVersionUID = 2383338960922579725L;

    @Size(min = 2, max = 20)
    private String name;
    @Email
    @Size(min = 6, max = 50)
    private String id;
    @Size(min = 6, max = 20)
    private String pw;
    @Size(min = 6, max = 20)
    private String pwconfirm;
    @Size(min = 6, max = 20)
    private String confirm;
    @Size(min = 2, max = 100)
    private String role;

    public Signup() {
    }

    public Signup(
            String name,
            String id,
            String pw,
            String pwconfirm,
            String confirm,
            String role
    ) {
        if (!pw.equalsIgnoreCase(pwconfirm)) throw new RuntimeException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        if (!confirm.equalsIgnoreCase("zotldnjzmghkrdlszl")) throw new RuntimeException("확인키가 올바르지 않습니다.");

        this.name = name;
        this.id = id;
        this.pw = pw;
        this.pwconfirm = pwconfirm;
        this.confirm = confirm;
        this.role = role;
    }
}
