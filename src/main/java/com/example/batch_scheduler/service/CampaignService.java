package com.example.batch_scheduler.service;

import com.example.batch_scheduler.model.Campaigns;
import com.example.batch_scheduler.repository.CampaignsRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class CampaignService {
  @Autowired
  private CampaignsRepository campaignsRepository;

  @Async(value = "taskExecutor")
  public List<Campaigns> getAllCampaigns() throws InterruptedException {
    List<Campaigns> result;
    result = campaignsRepository.findAll();
    try {
      System.out.println("Start Thread in Campaign Sleep "+ LocalTime.now());
      Thread.sleep(10000);
      System.out.println("End Thread in Campaign Sleep "+LocalTime.now());
      //    Query query = new Query();
      //    query.addCriteria(Criteria.where("type").is("once"));
      //    mongoTemplate.find(query,Triggers.class);
      //    System.out.println("Query : "+query+" : "+mongoTemplate.find(query,Triggers.class));
      System.out.println("Result campaigns ID : " + result.get(0).get_id());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return result;
  }
  public Optional<Campaigns> getById(String id){
//    System.out.println("pass id is : " +id);
//    System.out.println("Campaign id : "+campaignsRepository.findById(id));
    return campaignsRepository.findByCampaignId(id);
  }
}