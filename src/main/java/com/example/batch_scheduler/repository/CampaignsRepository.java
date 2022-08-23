package com.example.batch_scheduler.repository;

import com.example.batch_scheduler.model.Campaigns;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.persistence.criteria.CriteriaBuilder.In;
import java.util.Optional;

@EnableMongoRepositories
public interface CampaignsRepository extends MongoRepository<Campaigns, Integer> {

    @Query("{ '_id' : ?0 }")
    Optional<Campaigns> findByCampaignId(ObjectId id);

    @Query("{ 'name' : ?0 }")
    Optional<Campaigns> findByName(String name);

    @Query("{ 'subject' : ?0 }")
    Optional<Campaigns> findSubjectByName(String name);

    @Query("{ 'segment' : ?0 }")
    Optional<Campaigns> findBySegment(String name);
}
