package com.rest_api.employee.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    private Logger myLogger = Logger.getLogger(getClass().getName());

    @Pointcut("execution(* com.rest_api.employee.controller.*.*(..))")
    private void forControllerPackage() {}

    @Pointcut("execution(* com.rest_api.employee.service.*.*(..))")
    private void forServicePackage() {}

    @Pointcut("execution(* com.rest_api.employee.dao.*.*(..))")
    private void forDaoPackage() {}

    @Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
    private void forAppFlow() {}

    @Before("forAppFlow()")
    public void before(JoinPoint theJoinPoint)  {
        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("=======>> in @Before: calling method: " + theMethod);

        Object[] args = theJoinPoint.getArgs();

        for(Object tempArg: args) {
            myLogger.info("=======>> argument: " + tempArg);
        }
    }

    @AfterReturning(
            pointcut = "forAppFlow()",
            returning = "theResult"
    )
    public void afterReturning(JoinPoint theJoinPoint, Object theResult)  {
        String theMethod = theJoinPoint.getSignature().toShortString();
        myLogger.info("=======>> in @AfterReturning: from method: " + theMethod);

        myLogger.info("=======>> result: " + theResult);
    }
}
