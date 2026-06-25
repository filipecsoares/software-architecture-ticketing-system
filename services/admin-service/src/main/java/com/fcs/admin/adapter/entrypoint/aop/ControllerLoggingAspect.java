package com.fcs.admin.adapter.entrypoint.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ControllerLoggingAspect {

    // Intercepta qualquer método público em classes anotadas com @RestController
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restControllerMethods() {}

    @Before("restControllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        log.info("➡️ Entrando em método: {}.{}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "restControllerMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("✅ Retorno de {}.{} => {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result);
    }

    @AfterThrowing(pointcut = "restControllerMethods()", throwing = "ex")
    public void logException(JoinPoint joinPoint, Throwable ex) {
        log.error("❌ Exceção em {}.{}: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                ex.getMessage(), ex);
    }
}
