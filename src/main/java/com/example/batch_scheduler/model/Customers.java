package com.example.batch_scheduler.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@ToString

@Document(collection = "customers")
public class Customers {

  @Id
  private ObjectId _id;
  private String iparty_id;
  private String birth_place;
  private String city;
  private String country;
  private String created_on;
  private String current_address;
  private String date_of_birth;
  private String first_name;
  private String gender;
  private String mailing_address;
  private String nationality;
  private String permanent_address;
  private String updated_date;
  private String core_bank_account;
  private String mobile_phone_number;
  private String risk_rating;
}
