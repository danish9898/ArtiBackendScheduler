package com.example.batch_scheduler.controller;

import com.example.batch_scheduler.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("/customers")
public class CustomerController {
  @Autowired
  private CustomerRepository customerRepository;
  private String num ="62b447bb27d6b5befe357014";

  @GetMapping("/all")
  public String display() {
    System.out.println("Route Called :" + customerRepository.count());
    System.out.println("Route Called :" + customerRepository.findCustomerByName("PERVEEN AYUB"));
    return "customerRepository.findAll()";

  }

}
