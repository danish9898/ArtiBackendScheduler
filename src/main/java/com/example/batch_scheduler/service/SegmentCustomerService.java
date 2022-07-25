package com.example.batch_scheduler.service;

import com.example.batch_scheduler.model.SegmentCustomers;
import com.example.batch_scheduler.repository.SegmentCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class SegmentCustomerService {
  @Autowired
  private SegmentCustomerRepository segmentCustomerRepository;


  @Async(value = "taskExecutor")
  public List<SegmentCustomers> getAllMaleCustomers() throws InterruptedException {
    List<SegmentCustomers> result;
    result = segmentCustomerRepository.findAll();
//    try {
      System.out.println("Start Thread in Male Customer Sleep "+ LocalTime.now());
      Thread.sleep(10000);
      System.out.println("End Thread in Male Customer Sleep "+LocalTime.now());
      System.out.println("Result : " + result.get(0).getFirst_name()+" and number : "+result.get(0).getMobile_phone_number());
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
//    triggerMail();
    return result;
  }

  public List<SegmentCustomers> getAllSegmentCustomer(){
    List<SegmentCustomers> result;
    result = segmentCustomerRepository.findAll();
    return result;
  }
}

