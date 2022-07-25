package com.example.batch_scheduler.notifications;

import com.example.batch_scheduler.model.Customers;
import com.example.batch_scheduler.model.SmsCustomers;
import org.springframework.stereotype.Component;

import org.springframework.util.MultiValueMap;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
@Component
public class SmsService {

  private final String ACCOUNT_SID ="AC2ad9f63160c3802b4e1370e059d66369";

  private final String AUTH_TOKEN = "0b4ab45c3f5d951bcf070149096a31f3";

  private final String FROM_NUMBER = "+18647540759";

  public void send(SmsCustomers sms) {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

    Message message = Message.creator(new PhoneNumber(sms.getMobile_phone_number()), new PhoneNumber(FROM_NUMBER), sms.getMailing_address())
        .create();
//    System.out.println("Here is my Twilio id: "+message.getSid());// Unique resource ID created to manage this transaction
  }

}