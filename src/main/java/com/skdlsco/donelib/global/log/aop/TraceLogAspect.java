package com.skdlsco.donelib.global.log.aop;

import com.skdlsco.donelib.global.log.tracelog.TraceLogManager;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class TraceLogAspect {

    final private TraceLogManager traceLogManager;

    @Pointcut("execution(* com.skdlsco.donelib..*(..))")
    private void basePackage() {
    }

    @Pointcut("execution(* com.skdlsco.donelib.domain..*(..))")
    private void domainComponent() {
    }

    @Pointcut("basePackage() && @within(com.skdlsco.donelib.global.log.aop.annotation.TraceInclude)")
    private void includeClass() {
    }

    @Pointcut("@annotation(com.skdlsco.donelib.global.log.aop.annotation.TraceInclude)")
    private void includeMethod() {
    }

    @Pointcut("!@annotation(com.skdlsco.donelib.global.log.aop.annotation.TraceExclude) && !@within(com.skdlsco.donelib.global.log.aop.annotation.TraceExclude)")
    private void exclude() {
    }

    @Around("(domainComponent() || includeClass()) && exclude() || includeMethod()")
    public Object traceLogAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long startTimeMs = System.currentTimeMillis();
        try {
            traceLogManager.begin(methodName);
            Object result = joinPoint.proceed();
            traceLogManager.end(methodName + " time=" + getResultTime(startTimeMs) + "ms");
            return result;
        } catch (Throwable e) {
            traceLogManager.setException(e);
            traceLogManager.exception(methodName + " time=" + getResultTime(startTimeMs) + "ms", e);
            throw e;
        }
    }

    private long getResultTime(long startTimeMs) {
        long endTimeMs = System.currentTimeMillis();
        return endTimeMs - startTimeMs;
    }
}
