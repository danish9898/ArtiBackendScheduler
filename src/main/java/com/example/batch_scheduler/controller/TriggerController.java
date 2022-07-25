package com.example.batch_scheduler.controller;


import com.example.batch_scheduler.repository.TriggerRepository;
import com.example.batch_scheduler.service.AccountService;
import com.example.batch_scheduler.service.CampaignService;
import com.example.batch_scheduler.service.CustomerService;
import com.example.batch_scheduler.service.SchedulerThreadService;
import com.example.batch_scheduler.service.SegmentService;
import com.example.batch_scheduler.service.TriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/triggers")
public class TriggerController {
  @Autowired
  private TriggerRepository triggerRepository;

  @Autowired
  private TriggerService triggerService;

  @Autowired
  private CampaignService campaignService;

  @Autowired
  private CustomerService customerService;

  @Autowired
  private AccountService accountService;

  @Autowired
  private SegmentService segmentService;

  @Autowired
  private SchedulerThreadService schedulerThreadService;

  @GetMapping("/all")
  public String display() throws InterruptedException {
    System.out.println("Route Called :" + triggerRepository.count());
    schedulerThreadService.getCampaigns();

//    System.out.println("Route Called :" + triggerRepository.findTypeByName("once"));
//    System.out.println("Route Called TriggerService :" + triggerService.getAllTriggers());
//    System.out.println("Route Called CampaignService :" + campaignService.getAllCampaigns());
//    System.out.println("Route Called CustomerService :" + customerService.getAllCustomers());
//    System.out.println("Route Called AccountService :" + accountService.getAllAccounts());
//    System.out.println("Route Called SegmentService :" +segmentService.getAllSegments());
//    System.out.println("Route Called MaleCustomerService :" +maleCustomerService.getAllMaleCustomers());
//    System.out.println("Route Called FemaleCustomerService :" +femaleCustomerService.getAllFemaleCustomers());

//    System.out.println("Route Called :" + triggerRepository.findRecursiveByName(false));
//    System.out.println("Route Called :" + triggerRepository.findByExecuted(false));
//    System.out.println("Route Called :" + triggerRepository.findByTime("11:57"));
    return "Executed";

  }

}
