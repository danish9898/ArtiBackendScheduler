package com.example.batch_scheduler.service;

import com.example.batch_scheduler.model.Campaigns;
import com.example.batch_scheduler.model.Segments;
import com.example.batch_scheduler.repository.SegmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class SegmentService {

  @Autowired
  private SegmentRepository segmentRepository;

  //  @Autowired
  //  private SmsService smsService;

    @Async(value = "taskExecutor")
  public List<Segments> getAllSegments() throws InterruptedException {
    List<Segments> result;
    result = segmentRepository.findAll();
    try {
      System.out.println("Start Thread in Segments Sleep "+ LocalTime.now());
      Thread.sleep(10000);
      System.out.println("End Thread in Segments Sleep "+LocalTime.now());
      System.out.println("Result Segments ID : " + result.get(0).getSegment_collection());
//     int index = result.get(0).getSegment_collection().indexOf( '_' );string.split("-")
      String index[] = result.get(0).getSegment_collection().split("_");
//      System.out.println("index : "+ index[0]);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return result;
  }
  public Optional<Segments> getById(String id){
    //    System.out.println("pass id is : " +id);
    //    System.out.println("Campaign id : "+campaignsRepository.findById(id));
    return segmentRepository.findBySegmentId(id);
  }
}
