package com.example.batch_scheduler.service;

import com.example.batch_scheduler.model.SmsCustomers;
import com.example.batch_scheduler.notifications.EmailSenderService;
import com.example.batch_scheduler.model.Customers;
import com.example.batch_scheduler.notifications.SmsService;
import com.example.batch_scheduler.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
@Service
public class CustomerService {
  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private SmsService smsService;

  @Autowired
  private EmailSenderService senderService;

  @Autowired
  private SmsCustomers smsCustomers;

  public void triggerMail() {
    senderService.sendSimpleEmail("danishnaseer98@yahoo.com",
        "This is test email body",
        "This is email subject");

  }


  @Async(value = "taskExecutor")
  public List<Customers> getAllCustomers() throws InterruptedException {
    List<Customers> result;
    result = customerRepository.findAll();
    try {
      System.out.println("Start Thread in Customer Sleep "+ LocalTime.now());
      Thread.sleep(10000);
      System.out.println("End Thread in Customer Sleep "+LocalTime.now());

//      smsService.send(result.get(0));
      System.out.println("Result : " + result.get(0).getFirst_name()+" and number : "+result.get(0).getGender());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
//    triggerMail();
    return result;
  }
}

