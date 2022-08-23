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

@Document(collection = "triggers")
public class Triggers {

  @Id
  private ObjectId _id;
  private ObjectId campaign;
  private String time;
  private Boolean recursive;
  private String type;
  private String day;
  private String date;
  private Boolean is_executed;

}
