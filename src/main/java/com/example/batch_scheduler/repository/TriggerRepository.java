package com.example.batch_scheduler.repository;
import com.example.batch_scheduler.model.Triggers;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;
import java.util.Optional;

@EnableMongoRepositories
public interface TriggerRepository extends MongoRepository<Triggers, Integer> {

//  List<Customers> findByName(String name);

  @Query("{ 'type' : ?0 }")
  Optional<Triggers> findTypeByName(String name);

  @Query("{ 'recursive' : ?0 }")
  Optional<Triggers> findRecursiveByName(Boolean name);

  @Query("{ 'time' : ?0 }")
  Optional<Triggers> findByTime(String name);

  @Query("{ 'is_executed' : ?0 }")
  List<Triggers> findByExecuted(Boolean name);

//  @Query("{id :?0}")                                                  //SQL Equivalent : SELECT * FROM BOOK WHERE ID=?
//  Optional<Customers> getCustomersById(String _id);
}
