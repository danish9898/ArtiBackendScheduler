package com.example.batch_scheduler.repository;

import com.example.batch_scheduler.model.Customers;
import com.example.batch_scheduler.model.InvalidCustomers;
import com.example.batch_scheduler.model.Triggerlog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;

@EnableMongoRepositories
public interface TriggerLogRepository extends MongoRepository<Triggerlog, Integer> {

//  List<Customers> findByName(String name);

  @Query("{ 'date_executed' : ?0 }")
  Optional<Customers> findCustomerByName(String name);

//  @Query("{id :?0}")                                                  //SQL Equivalent : SELECT * FROM BOOK WHERE ID=?
//  Optional<Customers> getCustomersById(String _id);
}
