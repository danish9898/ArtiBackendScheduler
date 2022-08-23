package com.example.batch_scheduler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@ToString

@Document(collection = "triggerinvalidcollection")
public class TriggerCollection {

  @Id
  private ObjectId _id;
  private ObjectId trigger_id;
  private ObjectId campaign_id;
  private ObjectId segment_id;
  private ObjectId customer_id;
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
  private Date date_executed;
  private String reason;
  private String type;
}
