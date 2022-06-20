package com.company.bookStore.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

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

    @Around("execution(* com.company.bookStore.controller.*.*(..)))")
    public Object profileAllMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        //Get intercepted method details
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();

        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();

        //Log method execution time
        logger.info("Execution time of " + className + "." + methodName + " :: " + stopWatch.getTotalTimeMillis() + " ms");

        return result;
    }
}
