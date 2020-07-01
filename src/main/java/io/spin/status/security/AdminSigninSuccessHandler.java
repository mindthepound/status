package io.spin.status.security;

import io.spin.status.config.Constants;
import io.spin.status.config.URLConfig;
import io.spin.status.domain.basic.admin.AuthToken;
import io.spin.status.enumeration.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Component
public class AdminSigninSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {

        log.info("success signin");

        if (authentication.getPrincipal() instanceof AuthToken) {

            AuthToken authToken = (AuthToken) authentication.getPrincipal();

            HttpSession httpSession = request.getSession();
            httpSession.setMaxInactiveInterval(60 * 60 * 24);
            httpSession.setAttribute(Constants.SESSION_IDENTIFIRE, authToken);

            Cookie cookie = new Cookie(Constants.COOKIE_TYPE, authToken.getAdminType());
            cookie.setMaxAge(60 * 60 * 24);
            response.addCookie(cookie);

            cookie = new Cookie(Constants.COOKIE_LAST_PASSWORD, authToken.getLastPasswordChange());
            cookie.setMaxAge(60 * 60 * 24);
            response.addCookie(cookie);

            String referer = String.valueOf(request.getSession().getAttribute("referer"));

            log.info("check : " + (referer != null && !referer.equalsIgnoreCase("null") && !referer.isEmpty()));

            String defaultPage = getDefaultPage(authToken);

            if (referer != null && !referer.equalsIgnoreCase("null") && !referer.isEmpty())
                setDefaultTargetUrl(referer);
            else setDefaultTargetUrl(defaultPage);

            log.info("url : " + this.getDefaultTargetUrl());

            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    private String getDefaultPage(AuthToken authToken) {

        String defaultPage = null;

        String[] roles = authToken.getAdminType().split("/");
        for (String role : roles) {
            if (role.equalsIgnoreCase(Roles.AD.getRole()))
            {
                defaultPage = "/ad/campaigns";
            }
            else if (role.equalsIgnoreCase(Roles.SUPER.getRole()))
            {
                defaultPage = "/banner/banners";
            }
            else
            {
                defaultPage = URLConfig.MAIN;
            }
        }

        return defaultPage;
    }
}
