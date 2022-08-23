package com.example.batch_scheduler.repository;

import com.example.batch_scheduler.model.Campaigns;
import com.example.batch_scheduler.model.Segments;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;
import java.util.Optional;

@EnableMongoRepositories
public interface SegmentRepository extends MongoRepository<Segments, Integer> {

  @Query("{ '_id' : ?0 }")
  Optional<Segments> findBySegmentId(ObjectId id);

  @Query("{ 'name' : ?0 }")
  Optional<Segments> findByName(String name);

  @Query("{ 'customers' : ?0 }")
  List<Segments> findCustomers(String name);

  @Query("{ 'accounts' : ?0 }")
  List<Segments> findAccounts(String name);

  @Query("{ 'date_created' : ?0 }")
  Optional<Segments> findByDate(String name);

  @Query("{ 'totalCustomers' : ?0 }")
  Optional<Segments> findByTotalCustomers(Integer number);

  @Query("{ 'segment_collection' : ?0 }")
  Optional<Segments> findSegmentCollection(String name);
}
