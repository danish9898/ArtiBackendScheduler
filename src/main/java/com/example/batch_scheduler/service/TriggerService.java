package com.example.batch_scheduler.service;
import com.example.batch_scheduler.model.SmsCustomers;
import com.example.batch_scheduler.notifications.EmailSenderService;
import com.example.batch_scheduler.model.Triggers;
import com.example.batch_scheduler.notifications.SmsService;
import com.example.batch_scheduler.repository.TriggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;

@Service
public class TriggerService {
  @Autowired
  private TriggerRepository triggerRepository;

  @Autowired
  private EmailSenderService senderService;

  @Autowired
  private CampaignService campaignService;

  @Autowired
  private SegmentService segmentService;

  @Autowired
  private SmsCustomers smsCustomers;

  @Autowired
  private SmsService smsService;

  @Autowired
  private MongoOperations mongoOperations;

  //
  //  @Autowired
  //  private SegmentCustomerService segmentCustomerService;

  //  @Autowired
  //  private MongoTemplate mongoTemplate;
  //

  // Create Formatter class object
  Formatter format = new Formatter();
  // Creating a calendar
  Calendar gfg_calender = Calendar.getInstance();

//  @Async(value = "taskExecutor")
  public List<Triggers> getAllTriggers() throws InterruptedException {
    List<Triggers> result;
    result = triggerRepository.findAll();
    try {

      System.out.println("Start Thread in Trigger Sleep " + LocalTime.now());
      Thread.sleep(10000);
      System.out.println("End Thread in Trigger Sleep " + LocalTime.now());
      System.out.println("Result : " + result.get(0).getType());
      //      triggerMail();
      //      System.out.println("Email Sended Successfully : "+collection.toString());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
//    getCampaigns();
    return result;
  }

  public void triggerMail() throws MessagingException {
//    senderService.sendSimpleEmail("danishnaseer98@yahoo.com", "This is test email body", "This is email subject");

  }
  
}