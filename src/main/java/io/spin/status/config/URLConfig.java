package io.spin.status.config;

public class URLConfig {

    public static final String MAIN = "/";
    public static final String STATUS = "/status";
    public static final String REPORTS = "/reports";
    public static final String SIGNUP = "/signup";
    public static final String SIGNIN = "/signin";
    public static final String SIGNIN_PROCESS = "/signin";
    public static final String SIGNOUT = "/signout";

    public static final String PERMISSION_ERROR = "/permission-error";

    public static final String[] WEB_IGNORE_MATCHER = {
            "/css/**", "/js/**", "/font/**", "/vendor/**", "/webjars/**",
            "/v2/api-docs", "/swagger-resources/**"
    };
    public static final String[] REQUIRED_AUTHENTICATIONS = {"/**"};
    public static final String[] AUTHENTICATION_WHITE_LIST = {
            STATUS, REPORTS, SIGNUP, SIGNIN, SIGNIN_PROCESS, SIGNOUT, PERMISSION_ERROR
    };

}
