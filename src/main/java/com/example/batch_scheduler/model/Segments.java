package com.example.batch_scheduler.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.persistence.Id;
import java.util.List;

@Getter
@Setter
@ToString

@Document(collection = "customer_segments")
public class Segments {

  @Id
  private ObjectId _id;
  private List<String> customers;
  private List<String> accounts;
  private String name;
  private String date_created;
  private String date_updated;
  private Integer totalCustomers;
  private String segment_collection;
}