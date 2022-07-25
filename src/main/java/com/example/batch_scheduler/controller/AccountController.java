package com.example.batch_scheduler.controller;

import com.example.batch_scheduler.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/accounts")
public class AccountController {
  @Autowired
  private AccountRepository accountRepository;

  @GetMapping("/all")
  public String display() {
    System.out.println("Route Called :" + accountRepository.count());
    System.out.println("Route Called :" + accountRepository.findAccountStatusByName("Current"));
    //    System.out.println("Route Called :" + triggerRepository.findRecursiveByName(false));
    //    System.out.println("Route Called :" + triggerRepository.findByExecuted(false));
    //    System.out.println("Route Called :" + triggerRepository.findByTime("11:57"));
    return "Executed";

  }
}
