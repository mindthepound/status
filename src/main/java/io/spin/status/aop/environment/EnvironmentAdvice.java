package io.spin.status.aop.environment;

import io.spin.status.annotation.environment.Environment;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class EnvironmentAdvice {

    @Value("${spring.profiles.active}")
    private String profiles;

    @Around("@annotation(environment)")
    public Object environment(
            ProceedingJoinPoint joinPoint,
            Environment environment
    ) throws Throwable {

        if (
            !environment.only().equalsIgnoreCase("")
                &&
            !environment.except().equalsIgnoreCase("")
        ) {
            return null;
        } else if (
            environment.only().equalsIgnoreCase("")
                &&
            environment.except().equalsIgnoreCase("")
        ) {
            return null;
        }

        if (
            !environment.only().equalsIgnoreCase("")
                &&
            environment.only().equalsIgnoreCase(profiles)
        ) {
            return joinPoint.proceed();
        } else if (
            !environment.only().equalsIgnoreCase("")
                &&
            !environment.only().equalsIgnoreCase(profiles)
        ) {
            return null;
        }

        if (
            !environment.except().equalsIgnoreCase("")
                &&
            !environment.except().equalsIgnoreCase(profiles)
        ) {
            return joinPoint.proceed();
        } else if (
            !environment.except().equalsIgnoreCase("")
                &&
            environment.except().equalsIgnoreCase(profiles)
        ) {
            return null;
        }

        return null;
    }

}
