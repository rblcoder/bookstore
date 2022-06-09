package com.company.bookStore.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AspectConfig {
    private final Logger logger = LoggerFactory.getLogger(AspectConfig.class);

    @Before(value = "execution(* com.company.bookStore.controller.*.*(..))")
    public void logMessageBefore(JoinPoint joinPoint) {
        logger.info("Executing {}", joinPoint);
    }

    @After(value = "execution(* com.company.bookStore.controller.*.*(..))")
    public void logMessageAfter(JoinPoint joinPoint) {
        logger.info("Finished execution of {}", joinPoint);
    }

}
