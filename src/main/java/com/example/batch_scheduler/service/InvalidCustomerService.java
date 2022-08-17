package com.example.batch_scheduler.service;

import com.example.batch_scheduler.model.InvalidCustomers;
import com.example.batch_scheduler.repository.InvalidCustomerRepository;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class InvalidCustomerService {
  @Autowired
  private InvalidCustomerRepository invalidCustomerRepository;

//
//  public void triggerMail() {
//    senderService.sendSimpleEmail("danishnaseer98@yahoo.com",
//        "This is test email body",
//        "This is email subject");
//
//  }


  @Async(value = "taskExecutor")
  public List<InvalidCustomers> getAllCustomers() throws InterruptedException {
    List<InvalidCustomers> result;
    result = invalidCustomerRepository.findAll();
    try {
      System.out.println("Start Thread in Customer Sleep "+ LocalTime.now());
      Thread.sleep(10000);
      System.out.println("End Thread in Customer Sleep "+LocalTime.now());

//      smsService.send(result.get(0));
      System.out.println("Result : " + result.get(0).getFirst_name()+" and number : "+result.get(0));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
//    triggerMail();
    return result;
  }

  public void addInvalidCustomer(Document document) {
    InvalidCustomers invalidCustomers = new InvalidCustomers();
    invalidCustomers.set_id((ObjectId) document.get("_id"));
    invalidCustomers.setIparty_id((String) document.get("iparty_id"));
    invalidCustomers.setBirth_place((String) document.get("birth_place"));
    invalidCustomers.setCity((String) document.get("city"));
    invalidCustomers.setC( document.get("c"));
    invalidCustomers.setCreated_on((String) document.get("created_on"));
    invalidCustomers.setAddress((String) document.get("address"));
    invalidCustomers.setDate_of_birth((String) document.get("date_of_birth"));
    invalidCustomers.setFirst_name((String) document.get("first_name"));
    invalidCustomers.setGender((String) document.get("gender"));
    invalidCustomers.setClr( document.get("clr"));
    invalidCustomers.setRisk_rating((String) document.get("risk_rating"));
    invalidCustomerRepository.save(invalidCustomers);
  }
}

