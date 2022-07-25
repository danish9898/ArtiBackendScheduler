package com.example.batch_scheduler.service;

import com.example.batch_scheduler.model.Accounts;
import com.example.batch_scheduler.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class AccountService {
  @Autowired
  private AccountRepository accountRepository;

  @Async(value = "taskExecutor")
  public List<Accounts> getAllAccounts() throws InterruptedException {
    List<Accounts> result;
    result = accountRepository.findAll();
    try {
      System.out.println("Start Thread in Accounts Sleep "+ LocalTime.now());
      Thread.sleep(10000);
      System.out.println("End Thread in Accounts Sleep "+LocalTime.now());
      //    Query query = new Query();
      //    query.addCriteria(Criteria.where("type").is("once"));
      //    mongoTemplate.find(query,Triggers.class);
      //    System.out.println("Query : "+query+" : "+mongoTemplate.find(query,Triggers.class));
      System.out.println("Result : " + result.get(0).getAccount_id());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return result;
  }
}