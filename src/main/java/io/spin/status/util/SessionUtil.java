package io.spin.status.util;

import io.spin.status.config.Constants;
import io.spin.status.domain.basic.admin.AuthToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class SessionUtil {

    public String getID() {

        return getAuthToken().getId();
    }

    public String getAdminType() {

        return getAuthToken().getAdminType();
    }

    private AuthToken getAuthToken() {
        HttpSession httpSession = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest()
                .getSession();
        return (AuthToken) httpSession
                .getAttribute(Constants.SESSION_IDENTIFIRE);
    }

}
