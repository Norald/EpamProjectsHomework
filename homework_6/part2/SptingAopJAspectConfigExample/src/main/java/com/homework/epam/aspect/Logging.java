package com.homework.epam.aspect;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Logging {
    private static final Logger LOG = LogManager.getLogger(Logging.class.getName());

    @Pointcut("@annotation(com.homework.epam.annotation.Timed)")
    public void timedPointcut() {
    }

    @Around("timedPointcut()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint){
        try {
            String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
            String name = joinPoint.getSignature().getName();
            Object[] args = joinPoint.getArgs();
            LOG.info("Before execution...");
            LOG.info("On object of type: " + declaringTypeName);
            LOG.info("Executed method: " + name);
            LOG.info("With arguments: ");
            if (args.length != 0) {
                for (Object arg : args) {
                    LOG.info("    -> " + arg);
                }
            } else {
                LOG.info("    -> Method has no arguments!");
            }
            long beforeMethodInvocation = System.nanoTime();
            Object returnValue = joinPoint.proceed();
            long afterMethodInvocation = System.nanoTime();
            LOG.info("After returning...");
            LOG.info("Execution of the method took: " + (afterMethodInvocation - beforeMethodInvocation) / 1000000 + " ms");
            return returnValue;
        } catch (Throwable e) {
            LOG.info("After throwing...");
            throw new IllegalArgumentException("Method "
                    + joinPoint.getSignature().getName()
                    + " caused an Exception!", e);
        }
    }



}
