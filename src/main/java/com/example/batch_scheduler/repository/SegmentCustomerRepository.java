package com.example.batch_scheduler.repository;

import com.example.batch_scheduler.model.SegmentCustomers;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface SegmentCustomerRepository extends MongoRepository<SegmentCustomers, Integer> {

  //  List<Customers> findByName(String name);
//
//  @Query("{ 'first_name' : ?0 }")
//  Optional<Customers> findCustomerByName(String name);
//
//  //  @Query("{id :?0}")                                                  //SQL Equivalent : SELECT * FROM BOOK WHERE ID=?
//  //  Optional<Customers> getCustomersById(String _id);
}