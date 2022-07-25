package com.example.batch_scheduler.repository;

import com.example.batch_scheduler.model.Customers;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;
@EnableMongoRepositories
public interface CustomerRepository extends MongoRepository<Customers, Integer> {

//  List<Customers> findByName(String name);

  @Query("{ 'first_name' : ?0 }")
  Optional<Customers> findCustomerByName(String name);

//  @Query("{id :?0}")                                                  //SQL Equivalent : SELECT * FROM BOOK WHERE ID=?
//  Optional<Customers> getCustomersById(String _id);
}
