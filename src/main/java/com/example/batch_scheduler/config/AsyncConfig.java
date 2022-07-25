package com.example.batch_scheduler.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
@Configuration
public class AsyncConfig extends AsyncConfigurerSupport {

  @Autowired
  private AsyncExceptionHandler asyncExceptionHandler;

  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(5);
    executor.setMaxPoolSize(5);
    executor.setQueueCapacity(100);
    executor.setThreadNamePrefix("Async thread executor-1");
    executor.initialize();
    return executor;
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return super.getAsyncUncaughtExceptionHandler();
  }

  //  @Bean(name ="taskExecutor")
  //  public Executor taskExecutor(){
  //    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
  //    executor.setCorePoolSize(10);
  //    executor.setMaxPoolSize(10);
  //    executor.setQueueCapacity(100);
  //    executor.setThreadNamePrefix("executor-1");
  //    executor.initialize();
  //    return executor;
  //  }
  //  @Bean(name ="taskExecutor-1")
  //  public Executor taskCompleteExecutor(){
  //    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
  //    executor.setCorePoolSize(10);
  //    executor.setMaxPoolSize(10);
  //    executor.setQueueCapacity(1000);
  ////    executor.setKeepAliveSeconds(60);
  //    executor.setThreadNamePrefix("executor-2");
  //    executor.initialize();
  //    return executor;
  //  }
}

