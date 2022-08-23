package com.example.batch_scheduler.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Getter
@Setter
@ToString

@Document(collection = "campaigns")
public class Campaigns {
  @Id
  private ObjectId _id;
  private String name;
  private String objective;
  private ObjectId segment;
  private String details;
  private String subject;
  private String content;
  private String type;
}
//  private ObjectId _id;
//  private String name;
//  private String objective;
//  private String segment;
//  private String details;
//  private String subject;
//  private String content;
//  private String type;