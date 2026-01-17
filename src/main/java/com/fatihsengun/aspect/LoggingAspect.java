package com.fatihsengun.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.fatihsengun.service..*(..)) || " +
            "execution(* com.fatihsengun.repository..*(..)) || " +
            "execution(* com.fatihsengun.controller..*(..))")
    public void applicationPackagePointcut() {

    }


    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("ERROR RECEIVED: Method {}.{}() threw an error. Message: {}",
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(),
                e.getMessage() != null ? e.getMessage() : "NULL");
    }

    @Around("applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();

            long executionTime = System.currentTimeMillis() - start;

            log.info("SUCCESS: {}.{}()- Time: {} ms",
                    joinPoint.getSignature().getDeclaringType(),
                    joinPoint.getSignature().getName(),
                    executionTime);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Faulty Argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringType(), joinPoint.getSignature().getName());
            throw e;
        }
    }


}
