package com.example.batch_scheduler.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
  @Override
  public void handleUncaughtException(Throwable ex, Method method, Object... params) {
    System.out.println("Mehthod Name "+method.getName()+"....."+ Arrays.toString(params)+"....."+"error Message"+ex.getMessage());
  }
}

