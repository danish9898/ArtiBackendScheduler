package com.example.batch_scheduler.service;

import com.example.batch_scheduler.model.InvalidCustomers;
import com.example.batch_scheduler.model.Triggerlog;
import com.example.batch_scheduler.repository.InvalidCustomerRepository;
import com.example.batch_scheduler.repository.TriggerLogRepository;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TriggerLogService {
  @Autowired
  private TriggerLogRepository triggerLogRepository;

//
//  public void triggerMail() {
//    senderService.sendSimpleEmail("danishnaseer98@yahoo.com",
//        "This is test email body",
//        "This is email subject");
//
//  }


  @Async(value = "taskExecutor")
  public List<Triggerlog> getAllCustomers() throws InterruptedException {
    List<Triggerlog> result;
    result = triggerLogRepository.findAll();
    try {
      System.out.println("Start Thread in Customer Sleep "+ LocalTime.now());
      Thread.sleep(10000);
      System.out.println("End Thread in Customer Sleep "+LocalTime.now());

//      smsService.send(result.get(0));
      System.out.println("Result : " + result.get(0).getInvalid_counts()+" and number : "+result.get(0));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
//    triggerMail();
    return result;
  }

//  public void addTriggerLog(Triggerlog triggerlog , ArrayList<InvalidCustomers> invalidCustomers) {
//    triggerlog.setInvalid_customers(invalidCustomers);
//    triggerLogRepository.save(triggerlog);
//  }
//public void addTriggerLog(Triggerlog triggerlog , ArrayList<ObjectId> invalidCustomers) {
//  triggerlog.setInvalid_customers(invalidCustomers);
//  triggerLogRepository.save(triggerlog);
//}

  public void saveTriggerLog(Triggerlog triggerlog) {
    this.triggerLogRepository.save(triggerlog);
  }
}

