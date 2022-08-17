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

@Document(collection = "invalidRecord")
public class InvalidCustomers {

  @Id
  private ObjectId _id;
  private String iparty_id;
  private String birth_place;
  private String city;
  private Object c;
  private String created_on;
  private String address;
  private String date_of_birth;
  private String first_name;
  private String gender;
  private Object clr;
  private String risk_rating;
}
