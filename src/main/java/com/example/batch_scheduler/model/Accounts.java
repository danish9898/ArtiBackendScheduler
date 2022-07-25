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

@Document(collection = "accounts")
public class Accounts {
  @Id
  private ObjectId _id;
  private String account_id;
  private String iparty_id;
  private String currency;
  private String balance;
  private String balance_date;
  private String last_transaction_date;
  private String individual_acct_type;
  private String account_class;
  private String created_on;
  private String account_freezed_on;
  private String account_status;
  private String posting_reason;
  private String channel_id;
  private String dormancy_date;
  private String source_system;

}
