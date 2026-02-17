package com.fatihsengun.aspect;

import com.fatihsengun.exception.BaseException;
import com.fatihsengun.exception.ErrorMessage;
import com.fatihsengun.exception.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.fatihsengun.service..*(..)) || execution(* com.fatihsengun.controller..*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            log.info("▶ IN: {}.{}() - Arguments: {}", className, methodName, joinPoint.getArgs());

            Object result = joinPoint.proceed();

            stopWatch.stop();

            log.info("◀ OUT: {}.{}() - Time: {} ms - Result: {}",
                    className, methodName, stopWatch.getTotalTimeMillis(), result);

            return result;

        } catch (Throwable e) {
            log.error("✖ HATA: {}.{}() metodunda patladı! Mesaj: {}", className, methodName, e.getMessage());
            throw e;

        }


    }

}
