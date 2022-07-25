package com.example.batch_scheduler.controller;

import com.example.batch_scheduler.repository.SegmentRepository;
import com.example.batch_scheduler.service.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customersegemnts")
public class SegmentController {

  @Autowired
  private SegmentRepository segmentRepository;

  @Autowired
  private SegmentService segmentService;

  @GetMapping("/all")
  public String display() throws InterruptedException {
    System.out.println("Route Called :" + segmentRepository.count());
//    System.out.println("Route Called :" + segmentRepository.findByName("Females with current accounts"));
//    System.out.println("Route Called :" + segmentRepository.findSegmentCollection("femaleswithcurrentaccounts_2022-07-13t09:39:00.628z"));
    return "Executed";

  }
}