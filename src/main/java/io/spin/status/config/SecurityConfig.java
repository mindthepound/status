package io.spin.status.config;

public class SecurityConfig {

    public final static String CONTENT_SECURITY_POLICY = "img-src * data:; default-src * blob:; object-src 'self'; style-src 'self' 'unsafe-inline'; script-src 'self' 'unsafe-inline' 'unsafe-eval'";

}
