package com.example.batch_scheduler.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Service;

@Getter
@Setter
@ToString
@Service
public class SmsCustomers {

  private String mailing_address;
  private String mobile_phone_number;
}
