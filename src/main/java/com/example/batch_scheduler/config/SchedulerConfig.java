//package com.example.batch_scheduler.config;//package com.example.batch_scheduler.config;
//
//import com.example.batch_scheduler.service.CustomThread;
//import com.example.batch_scheduler.service.SchedulerThreadService;
//import com.example.batch_scheduler.service.TriggerService;
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
//  //  	@Scheduled(fixedRate = 2000L )
//  //  	void someJob() throws InterruptedException {
//  //  		System.out.println("Now is"+ new Date());
//  //  		Thread.sleep(1000L);
//  //  	}
//
//  @Autowired
//  private SchedulerThreadService schedulerThreadService;
//
//  //  // Create Formatter class object
//  //  Formatter format = new Formatter();
//  //  // Creating a calendar
//  //  Calendar gfg_calender = Calendar.getInstance();
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
