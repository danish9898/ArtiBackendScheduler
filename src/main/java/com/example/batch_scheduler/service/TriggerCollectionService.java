package com.example.batch_scheduler.service;

import com.example.batch_scheduler.model.TriggerCollection;
import com.example.batch_scheduler.model.Triggerlog;
import com.example.batch_scheduler.repository.TriggerCollectionRepository;
import com.example.batch_scheduler.repository.TriggerLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class TriggerCollectionService {
  @Autowired
  private TriggerCollectionRepository triggerCollectionRepository;

//
//  public void triggerMail() {
//    senderService.sendSimpleEmail("danishnaseer98@yahoo.com",
//        "This is test email body",
//        "This is email subject");
//
//  }


  @Async(value = "taskExecutor")
  public List<TriggerCollection> getAllCustomers() throws InterruptedException {
    List<TriggerCollection> result;
    result = triggerCollectionRepository.findAll();
    try {
      System.out.println("Start Thread in Customer Sleep "+ LocalTime.now());
      Thread.sleep(10000);
      System.out.println("End Thread in Customer Sleep "+LocalTime.now());

//      smsService.send(result.get(0));
      System.out.println("Result : " + result.get(0).getDate_executed()+" and number : "+result.get(0));
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

  public void saveTriggerCollection(TriggerCollection triggerCollection) {
    this.triggerCollectionRepository.save(triggerCollection);
  }
}

