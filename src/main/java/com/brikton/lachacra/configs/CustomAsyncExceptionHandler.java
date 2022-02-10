package com.brikton.lachacra.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Slf4j
@Configuration
public class CustomAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj) {
        log.info("Exception message - " + throwable.getMessage());
        log.info("Method name - " + method.getName());
        throwable.printStackTrace();
        for (Object param : obj) {
            log.info("Parameter value - " + param);
        }
    }
}