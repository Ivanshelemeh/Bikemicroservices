package com.example.bikecustomservise.api.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Aspect
@Slf4j
@Order(1)
public class AsyncRunnerAspect {

    @Pointcut("execution(@com.example.bikecustomservise.api.annotation.AsyncRunnerAnnotation public void save*(..))")
    public void asyncRunnerPointcut() {
    }

    @Around("asyncRunnerPointcut()")
    public Object asyncRunnerInvoke(ProceedingJoinPoint joinPoint) {
        return CompletableFuture.runAsync(() -> {
            try {
                log.info("Invoke save method async approach");
                joinPoint.proceed();
            } catch (Throwable e) {
                log.error(e.getMessage());
            }
        });

    }
}
