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
  private String _id;
  private String campaign;
  private String time;
  private Boolean recursive;
  private String type;
  private Boolean is_executed;

}
