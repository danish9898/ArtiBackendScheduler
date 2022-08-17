//package com.example.batch_scheduler.config;
//import com.example.batch_scheduler.service.SchedulerThreadService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//
//import java.util.Date;
//
//@Configuration
//@EnableScheduling
//@ConditionalOnProperty(name = "Scheduling.enable", matchIfMissing = true)
//public class SchedulerConfig {
//
//  @Autowired
//  private SchedulerThreadService schedulerThreadService;
//
//  @Scheduled(fixedRate = 30000)
//  void someJob() throws InterruptedException {
//    System.out.println("Start Time is " + new Date());
//    schedulerThreadService.getCampaigns();
//    System.out.println("End Time is " + new Date());
//    Thread.sleep(2000);
//  }
//
//}
