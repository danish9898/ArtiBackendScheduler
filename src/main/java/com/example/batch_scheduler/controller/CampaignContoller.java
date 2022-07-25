package com.example.batch_scheduler.controller;

import com.example.batch_scheduler.repository.CampaignsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/campaigns")
public class CampaignContoller {
  @Autowired
  private CampaignsRepository campaignsRepository;


  @GetMapping("/all")
  public String display() {
    System.out.println("Route Called :" + campaignsRepository.count());
    System.out.println("Route Called :" + campaignsRepository.findSubjectByName("Test Mail"));
    //    System.out.println("Route Called :" + triggerRepository.findRecursiveByName(false));
    //    System.out.println("Route Called :" + triggerRepository.findByExecuted(false));
    //    System.out.println("Route Called :" + triggerRepository.findByTime("11:57"));
    return "Executed";

  }
}
