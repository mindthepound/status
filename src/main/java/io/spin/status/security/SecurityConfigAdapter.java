package io.spin.status.security;

import io.spin.status.config.SecurityConfig;
import io.spin.status.config.URLConfig;
import io.spin.status.service.admin.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Slf4j
@EnableWebSecurity
@Configuration
public class SecurityConfigAdapter extends WebSecurityConfigurerAdapter {

    private AdminService adminService;
    private AuthenticationSuccessHandler adminSigninSuccessHandler;
    private AuthenticationFailureHandler adminSigninFailureHandler;
    private LogoutSuccessHandler adminSignoutSuccessHandler;

    @Autowired
    public SecurityConfigAdapter(
            AdminService adminService,
            AuthenticationSuccessHandler adminSigninSuccessHandler,
            AuthenticationFailureHandler adminSigninFailureHandler,
            LogoutSuccessHandler adminSignoutSuccessHandler
    ) {
        this.adminService = adminService;
        this.adminSigninSuccessHandler = adminSigninSuccessHandler;
        this.adminSigninFailureHandler = adminSigninFailureHandler;
        this.adminSignoutSuccessHandler = adminSignoutSuccessHandler;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring().antMatchers(URLConfig.WEB_IGNORE_MATCHER);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(adminService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .accessDeniedPage(URLConfig.PERMISSION_ERROR)
                .and()
                .headers()
                .defaultsDisabled()
                .cacheControl()
                .and()
                .defaultsDisabled()
                .contentTypeOptions()
                .and()
                .frameOptions().sameOrigin()
                .httpStrictTransportSecurity().disable()
                .xssProtection().block(true)
                .and()
                .contentSecurityPolicy(SecurityConfig.CONTENT_SECURITY_POLICY)
                .and()
                .addHeaderWriter(new StaticHeadersWriter("P3P", "CP=\"ALL DSP COR MON LAW IVDi HIS IVAi DELi SAMi OUR LEG PHY UNI ONL DEM STA INT NAV PUR FIN OTC GOV\""))
                .addHeaderWriter(new StaticHeadersWriter("Pragma", "no-cache"))
                .addHeaderWriter(new StaticHeadersWriter("Cache-Control", "no-cache"))
                .and()
                .csrf().disable()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers(URLConfig.SIGNIN).anonymous()
                .antMatchers(URLConfig.AUTHENTICATION_WHITE_LIST).permitAll()
                //.antMatchers(URLConfig.REQUIRED_AUTHENTICATIONS).access("hasRole('ADMIN') or hasRole('AD') or hasRole('MOVIGAME')")
                .and()
                .formLogin()
                .loginPage(URLConfig.SIGNIN)
                .loginProcessingUrl(URLConfig.SIGNIN_PROCESS)
                .usernameParameter("id")
                .passwordParameter("pw")
                .successHandler(adminSigninSuccessHandler)
                .failureHandler(adminSigninFailureHandler)
                .and()
                .logout()
                .invalidateHttpSession(true)
                .logoutUrl(URLConfig.SIGNOUT)
                .logoutSuccessHandler(adminSignoutSuccessHandler);
    }
}
