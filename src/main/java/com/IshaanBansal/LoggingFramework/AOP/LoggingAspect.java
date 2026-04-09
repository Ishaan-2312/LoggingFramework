package com.IshaanBansal.LoggingFramework.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.IshaanBansal..*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws  Throwable{

        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();
        log.info("Entering method: {}", methodName);

        try{
            Object result = joinPoint.proceed();

            long timeTaken = System.currentTimeMillis() - start;

            log.info("Exiting method: {} | Time Taken: {} ms", methodName, timeTaken);

            return result;
        }
        catch (Exception e){
            log.error("Exception in method: {} | Message: {}", methodName, e.getMessage());

            throw e;
        }
    }
}
