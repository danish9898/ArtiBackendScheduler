package com.example.batch_scheduler.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;

@Getter
@Setter
@ToString

@Document(collection = "triggerlogs")
public class Triggerlog {

  @Id
    private ObjectId trigger_id;
    private String date_executed;
    private int valid_counts;
    private int invalid_counts;
    private int null_count;
}
//  private ObjectId trigger_id;
//  private String date_executed;
//  private int valid_counts;
//  private int invalid_counts;
//  private int null_count;
//  private ArrayList<InvalidCustomers> invalid_customers;

//  private ObjectId trigger_id;
//  private ObjectId campaign_id;
//  private ObjectId segment_id;
//  private String date_executed;
//  private int valid_counts;
//  private int invalid_counts;
//  private int null_count;
//  private String reason;