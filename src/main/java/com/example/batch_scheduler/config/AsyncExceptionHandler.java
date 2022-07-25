package com.example.batch_scheduler.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
  @Override
  public void handleUncaughtException(Throwable ex, Method method, Object... params) {
    System.out.println("Mehthod Name "+method.getName()+"....."+ Arrays.toString(params)+"....."+"error Message"+ex.getMessage());
  }
}


//@Configuration
//public class AsyncConfig extends AsyncConfigurerSupport {

  //  @Autowired
  //  private AsyncExceptionHandler asyncExceptionHandler;
  //
  //  @Override
  //  public Executor getAsyncExecutor() {
  //    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
  //    executor.setCorePoolSize(10);
  //    executor.setMaxPoolSize(10);
  //    executor.setQueueCapacity(500);
  //    executor.setThreadNamePrefix("Async thread executor-1");
  //    executor.initialize();
  //    return executor;
  //  }
  //
  //  @Override
  //  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
  //    return super.getAsyncUncaughtExceptionHandler();
  //  }
//}
