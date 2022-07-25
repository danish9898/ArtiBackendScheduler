package com.example.batch_scheduler.repository;

import com.example.batch_scheduler.model.Accounts;
import com.example.batch_scheduler.model.Campaigns;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;

@EnableMongoRepositories
public interface AccountRepository extends MongoRepository<Accounts, Integer> {
  @Query("{ 'balance' : ?0 }")
  Optional<Accounts> findByBalance(String name);

  @Query("{ 'account_status' : ?0 }")
  Optional<Accounts> findAccountStatusByName(String name);

  @Query("{ 'account_class' : ?0 }")
  Optional<Accounts> findByAccountClass(String name);
}
