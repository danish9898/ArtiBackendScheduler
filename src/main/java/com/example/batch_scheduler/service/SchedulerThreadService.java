package com.example.batch_scheduler.service;

import com.example.batch_scheduler.model.Campaigns;
import com.example.batch_scheduler.model.Segments;
import com.example.batch_scheduler.model.SmsCustomers;
import com.example.batch_scheduler.model.Triggers;
import com.example.batch_scheduler.notifications.EmailSenderService;
import com.example.batch_scheduler.notifications.SmsService;
import com.example.batch_scheduler.repository.TriggerRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Formatter;
import java.util.List;
import java.util.Optional;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SchedulerThreadService {

  @Autowired
  private TriggerRepository triggerRepository;

  @Autowired
  private MongoOperations mongoOperations;

  @Autowired
  private CampaignService campaignService;

  @Autowired
  private SegmentService segmentService;

  @Autowired
  private EmailSenderService senderService;

  @Autowired
  private SmsCustomers smsCustomers;

  @Autowired
  private SmsService smsService;

  static volatile boolean keepRunning = true;

  Runtime r = Runtime.getRuntime();
  // creating two objects t1 & t2 of MyThread
  //      CustomThread t1 = new CustomThread("First  thread");
  //      CustomThread t2 = new CustomThread("Second thread");
  //      CustomThread t3 = new CustomThread("Third thread");
  //      CustomThread t4 = new CustomThread("Forth thread");

  // Create Formatter class object
  Formatter format = new Formatter();
  // Creating a calendar
  Calendar gfg_calender = Calendar.getInstance();

  // Emails pattern verification

  String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
  Pattern pattern = Pattern.compile(regex);

  public void getCampaigns() {
    String uri = "mongodb://localhost";
    MongoClient mongoClient = MongoClients.create(uri);
    MongoDatabase database = mongoClient.getDatabase("ArtiDb_Collections_Data_Export");
    List<Triggers> allTrigger = triggerRepository.findAll();

    // creating two objects t1 & t2 of MyThread
    //    CustomThread t1 = new CustomThread("First  thread");
    //    CustomThread t2 = new CustomThread("Second thread");
    //    CustomThread t3 = new CustomThread("Third thread");
    //    CustomThread t4 = new CustomThread("Forth thread");

    for (Triggers item : allTrigger) {
      // Once
      if ((item.getType().equals("once")) && (item.getIs_executed().equals(false))) {
        onceScheduler(item, database);
        //        System.out.println("Once: " + item);
      }
      // Daily
      else if (item.getType().equals("daily") && (item.getIs_executed().equals(false))) {
        //        System.out.println("daily: "+item);
        dailyScheduler(item, database);

      }
      // Weekly
      else if (item.getType().equals("weekly") && (item.getIs_executed().equals(false))) {
        //        System.out.println("weekly: "+item);
        weeklyScheduler(item, database);

      }
      // Monthly
      else if (item.getType().equals("monthly") && (item.getIs_executed().equals(false))) {
        //        System.out.println("monthly: "+item);
        monthlyScheduler(item, database);

      } else {
        System.out.println("No Thread Created : ");
        System.out.println("Number of active threads from the given thread: " + "Thread.activeCount()" + java.lang.Thread.activeCount());
        System.out.println("Current Thread is alive : " + Thread.currentThread().getName());
      }
    }
  }

  public Optional<Triggers> getTrigger(String name) {
    return triggerRepository.findTypeByName(name);
  }

  public void setTrigger(String name) {
    //    Optional<Triggers> triggers = triggerRepository.findTypeByName(name);
    //    triggers.get().setType("true");
    Query query = new Query();
    query.addCriteria(Criteria.where("type").is(name));

    Update update = new Update();
    update.set("is_executed", true);

    Triggers userTest = mongoOperations.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Triggers.class);
    //    System.out.println("userTest - " + userTest);
  }

  //  @Async
  public void onceScheduler(Triggers item, MongoDatabase database) {

    System.out.println("Current Item : " + item + " and Thread name : " + Thread.currentThread().getName());
    // Displaying hour using Format class using  format
    // specifiers
    // '%tl' for hours and '%tM' for minutes
    format = new Formatter();
    format.format("%tl:%tM", gfg_calender, gfg_calender);

    // only check time for Once case
    CustomThread t1 = new CustomThread("First  thread");
    Thread thread = new Thread(t1);

    // "11:57" equal to format
    if (item.getTime().equals("11:57")) {
      Optional<Campaigns> campaign = campaignService.getById(item.getCampaign());
      Optional<Segments> segment = segmentService.getById(campaign.get().getSegment());
      String index[] = segment.get().getSegment_collection().split("_");

      MongoCollection collection = database.getCollection(index[0]);

      MongoCursor<Document> cursor = collection.find().iterator();
      int invalidCounter = 0;
      int validCounter = 0;
      int invalidEmails = 0;
      int nullEmails = 0;
      // for testing  atleast one notification sent for email and  sms ...****
      int smslimit = 0;
      // for testing ...****
      Document document;
      try {
        while (cursor.hasNext()) {
          //            cursor.next();
          document = cursor.next();
          String number = (String) document.get("mobile_phone_number");

          String email = (String) document.get("mailing_address");

          boolean isValid = false;
          if (email != null) {
            //            System.out.println("Orignal Mailing Address  " + email);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches() == true) {
              System.out.println(" Emails Sent " + email);
              //                  senderService.sendSimpleEmail(email, "This is test email body", "This is email subject");
            } else {
              invalidEmails++;
              //              System.out.println("Invalid Emails :.... " + email);
            }
          }
          if (email == null) {
            nullEmails++;
          }
          if (number == null) {
            System.out.println("Orignal Contact Number is  " + number);
            //            System.out.println("True");
            invalidCounter++;
            continue;
          } else {
            if (number.charAt(0) == '0') {
              number = number.replace("-", "");
              if (number.length() != 11) {
                System.out.println("Number have 0 but Invalid Number" + number);
                invalidCounter++;
              } else {
                number = number.substring(1, number.length());
                number = "+92" + number;
                isValid = true;
                smslimit++;
                validCounter++;
                System.out.println("Number start with 0 : " + number);
              }
            } else if (number.charAt(0) == '+' && number.charAt(1) == '9' && number.charAt(2) == '2') {
              if (number.length() != 13) {
                System.out.println("Number have +92 but Invalid Number" + number);
                invalidCounter++;
              } else {
                isValid = true;
                smslimit++;
                validCounter++;
                System.out.println("Number have +92: " + number);
              }
            } else {
              System.out.println("Invalid Number Format " + number);
              invalidCounter++;
            }
          }
          if (isValid) {
            if (smslimit == 1) {
//              System.out.println("Sms Limit is One>>>>>>>>>>>>>>>>>>>");
//              //              Enter your number for test in smsCustomers.setMobile_phone_number()
//              smsCustomers.setMobile_phone_number("+923105405425");
//              smsCustomers.setMailing_address(email);
//              smsService.send(smsCustomers);
              senderService.sendSimpleEmail("danishnaseer98@yahoo.com", "ABC", "This is test email body");
              System.out.println("Test email sended successfully ;");
            }
            //            if(emaillimit == 1){
            //              System.out.println(" test email sended successfully ;");
            //              senderService.sendSimpleEmail(email, "This is test email body", "This is email subject");
            //              emaillimit++;
            //            }
            //            smsCustomers.setMobile_phone_number(number);
            //            smsCustomers.setMailing_address(email);// Optional
            //            System.out.println("Sms Customer Number : "+smsCustomers.getMobile_phone_number()+" Mailing Customer Email : "+smsCustomers.getMailing_address());
            //            smsService.send(smsCustomers);
          }
        }
        System.out.println("Invalid Record is : " + invalidCounter);
        System.out.println("Valid Record is : " + validCounter);
        System.out.println("Invalid Emails = " + invalidEmails + " and Null Email values = " + nullEmails);
        item.setIs_executed(true);
        Optional<Triggers> trigger = getTrigger(item.getType());
        setTrigger(item.getType());
      } finally {
        cursor.close();
      }
      t1.stop();
      //      System.out.println("Thread.getState() : "+thread.getState());
    }
    t1.stop();
    //    System.out.println("Thread.getState() : "+thread.getState());
  }

  //  @Async
  public void dailyScheduler(Triggers item, MongoDatabase database) {
    System.out.println("Current Item : " + item + " and Thread name : " + Thread.currentThread().getName());
    // Displaying hour using Format class using  format
    // specifiers
    // '%tl' for hours and '%tM' for minutes
    format = new Formatter();
    format.format("%tl:%tM", gfg_calender, gfg_calender);

    CustomThread t2 = new CustomThread("Second  thread");
    Thread thread = new Thread(t2);

    if (item.getTime().equals(format)) {
      Optional<Campaigns> campaign = campaignService.getById(item.getCampaign());
      Optional<Segments> segment = segmentService.getById(campaign.get().getSegment());
      String index[] = segment.get().getSegment_collection().split("_");

      MongoCollection collection = database.getCollection(index[0]);

      MongoCursor<Document> cursor = collection.find().iterator();
      int invalidCounter = 0;
      int validCounter = 0;
      int invalidEmails = 0;
      int nullEmails = 0;
      Document document;
      try {
        while (cursor.hasNext()) {
          //            cursor.next();
          document = cursor.next();
          String number = (String) document.get("mobile_phone_number");
          String email = (String) document.get("mailing_address");
          boolean isValid = false;
          if (email != null) {
            //            System.out.println("Orignal Mailing Address  " + email);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches() == true) {
              System.out.println(" Emails Sent " + email);
              //                  senderService.sendSimpleEmail(email, "This is test email body", "This is email subject");
            } else {
              invalidEmails++;
              //              System.out.println("Invalid Emails :.... " + email);
            }
          }
          if (email == null) {
            nullEmails++;
          }
          if (number == null) {
            System.out.println("Orignal Contact Number is  " + number);
            //            System.out.println("True");
            invalidCounter++;
            continue;
          } else {
            if (number.charAt(0) == '0') {
              number = number.replace("-", "");
              if (number.length() != 11) {
                System.out.println("Number have 0 but Invalid Number" + number);
                invalidCounter++;
              } else {
                number = number.substring(1, number.length());
                number = "+92" + number;
                isValid = true;
                validCounter++;
                System.out.println("Number start with 0 : " + number);
              }
            } else if (number.charAt(0) == '+' && number.charAt(1) == '9' && number.charAt(2) == '2') {
              if (number.length() != 13) {
                System.out.println("Number have +92 but Invalid Number" + number);
                invalidCounter++;
              } else {
                isValid = true;
                validCounter++;
                System.out.println("Number have +92: " + number);
              }
            } else {
              System.out.println("Invalid Number Format " + number);
              invalidCounter++;
            }
          }
          if (isValid) {
            //            smsCustomers.setMobile_phone_number(number);
            //            smsCustomers.setMailing_address(email);// Optional
            //            System.out.println("Sms Customer Number : "+smsCustomers.getMobile_phone_number()+" Mailing Customer Email : "+smsCustomers.getMailing_address());
            //            smsService.send(smsCustomers);
          }
        }
        System.out.println("Invalid Record is : " + invalidCounter);
        System.out.println("Valid Record is : " + validCounter);
        System.out.println("Invalid Emails = " + invalidEmails + " and Null Email values = " + nullEmails);
        item.setIs_executed(true);
        Optional<Triggers> trigger = getTrigger(item.getType());
        setTrigger(item.getType());
      } finally {
        cursor.close();
      }
      t2.stop();
    }
    t2.stop();
  }

  //  @Async
  public void weeklyScheduler(Triggers item, MongoDatabase database) {
    System.out.println("Current Item : " + item + " and Thread name : " + Thread.currentThread().getName());
    // Displaying hour using Format class using  format
    // specifiers
    // '%tl' for hours and '%tM' for minutes
    format = new Formatter();
    format.format("%tl:%tM", gfg_calender, gfg_calender);

    System.out.println("weekly: " + item + "Thread name : " + Thread.currentThread().getName());
    CustomThread t3 = new CustomThread("Third  thread");
    Thread thread = new Thread(t3);

    if (item.getTime().equals(format)) {
      Optional<Campaigns> campaign = campaignService.getById(item.getCampaign());
      Optional<Segments> segment = segmentService.getById(campaign.get().getSegment());
      //        String index[] = result.get(0).getSegment_collection().split("_");
      String index[] = segment.get().getSegment_collection().split("_");

      MongoCollection collection = database.getCollection(index[0]);

      MongoCursor<Document> cursor = collection.find().iterator();
      int invalidCounter = 0;
      int validCounter = 0;
      int invalidEmails = 0;
      int nullEmails = 0;
      Document document;
      try {
        while (cursor.hasNext()) {
          //            cursor.next();
          document = cursor.next();
          String number = (String) document.get("mobile_phone_number");
          String email = (String) document.get("mailing_address");
          boolean isValid = false;
          if (email != null) {
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches() == true) {
              System.out.println(" Emails Sent " + email);
              //                  senderService.sendSimpleEmail(email, "This is test email body", "This is email subject");
            } else {
              invalidEmails++;
              //              System.out.println("Invalid Emails :.... " + email);
            }
          }
          if (email == null) {
            nullEmails++;
          }
          if (number == null) {
            System.out.println("Orignal Contact Number is  " + number);
            //            System.out.println("True");
            invalidCounter++;
            continue;
          } else {
            if (number.charAt(0) == '0') {
              number = number.replace("-", "");
              if (number.length() != 11) {
                System.out.println("Number have 0 but Invalid Number" + number);
                invalidCounter++;
              } else {
                number = number.substring(1, number.length());
                number = "+92" + number;
                isValid = true;
                validCounter++;
                System.out.println("Number start with 0 : " + number);
              }
            } else if (number.charAt(0) == '+' && number.charAt(1) == '9' && number.charAt(2) == '2') {
              if (number.length() != 13) {
                System.out.println("Number have +92 but Invalid Number" + number);
                invalidCounter++;
              } else {
                isValid = true;
                validCounter++;
                System.out.println("Number have +92: " + number);
              }
            } else {
              System.out.println("Invalid Number Format " + number);
              invalidCounter++;
            }
          }
          if (isValid) {
            //            smsCustomers.setMobile_phone_number(number);
            //            smsCustomers.setMailing_address(email);// Optional
            //            System.out.println("Sms Customer Number : "+smsCustomers.getMobile_phone_number()+" Mailing Customer Email : "+smsCustomers.getMailing_address());
            //            smsService.send(smsCustomers);
          }
        }
        System.out.println("Invalid Record is : " + invalidCounter);
        System.out.println("Valid Record is : " + validCounter);
        System.out.println("Invalid Emails = " + invalidEmails + " and Null Email values = " + nullEmails);
        item.setIs_executed(true);
        Optional<Triggers> trigger = getTrigger(item.getType());
        setTrigger(item.getType());
      } finally {
        cursor.close();
      }
      t3.stop();
      //      System.out.println("Thread.getState() : "+thread.getState());
    }
    t3.stop();
    //    System.out.println("Thread.getState() : "+thread.getState());

  }

  //  @Async
  public void monthlyScheduler(Triggers item, MongoDatabase database) {
    System.out.println("Current Item : " + item + " and Thread name : " + Thread.currentThread().getName());
    // Displaying hour using Format class using  format
    // specifiers
    // '%tl' for hours and '%tM' for minutes
    format = new Formatter();
    format.format("%tl:%tM", gfg_calender, gfg_calender);

    CustomThread t4 = new CustomThread("Forth  thread");
    Thread thread = new Thread(t4);

    if (item.getTime().equals(format)) {
      Optional<Campaigns> campaign = campaignService.getById(item.getCampaign());
      Optional<Segments> segment = segmentService.getById(campaign.get().getSegment());
      String index[] = segment.get().getSegment_collection().split("_");

      MongoCollection collection = database.getCollection(index[0]);

      MongoCursor<Document> cursor = collection.find().iterator();
      int invalidCounter = 0;
      int validCounter = 0;
      int invalidEmails = 0;
      int nullEmails = 0;
      Document document;
      try {
        while (cursor.hasNext()) {
          //            cursor.next();
          document = cursor.next();
          String number = (String) document.get("mobile_phone_number");
          String email = (String) document.get("mailing_address");
          boolean isValid = false;
          if (email != null) {
            //            System.out.println("Orignal Mailing Address  " + email);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches() == true) {
              System.out.println(" Emails Sent " + email);
              //                  senderService.sendSimpleEmail(email, "This is test email body", "This is email subject");
            } else {
              invalidEmails++;
              //              System.out.println("Invalid Emails :.... " + email);
            }
          }
          if (email == null) {
            nullEmails++;
          }
          if (number == null) {
            System.out.println("Orignal Contact Number is  " + number);
            invalidCounter++;
            continue;
          } else {
            if (number.charAt(0) == '0') {
              number = number.replace("-", "");
              if (number.length() != 11) {
                System.out.println("Number have 0 but Invalid Number" + number);
                invalidCounter++;
              } else {
                number = number.substring(1, number.length());
                number = "+92" + number;
                isValid = true;
                validCounter++;
                System.out.println("Number start with 0 : " + number);
              }
            } else if (number.charAt(0) == '+' && number.charAt(1) == '9' && number.charAt(2) == '2') {
              if (number.length() != 13) {
                System.out.println("Number have +92 but Invalid Number" + number);
                invalidCounter++;
              } else {
                isValid = true;
                validCounter++;
                System.out.println("Number have +92: " + number);
              }
            } else {
              System.out.println("Invalid Number Format " + number);
              invalidCounter++;
            }
          }
          if (isValid) {
            //            smsCustomers.setMobile_phone_number(number);
            //            smsCustomers.setMailing_address(email);// Optional
            //            System.out.println("Sms Customer Number : "+smsCustomers.getMobile_phone_number()+" Mailing Customer Email : "+smsCustomers.getMailing_address());
            //            smsService.send(smsCustomers);
          }
        }
        System.out.println("Invalid Record is : " + invalidCounter);
        System.out.println("Valid Record is : " + validCounter);
        System.out.println("Invalid Emails = " + invalidEmails + " and Null Email values = " + nullEmails);
        item.setIs_executed(true);
        Optional<Triggers> trigger = getTrigger(item.getType());
        setTrigger(item.getType());
      } finally {
        cursor.close();
      }
      t4.stop();
    }
    t4.stop();
    //    System.out.println("Number of active threads from the given thread: " + "Thread.activeCount()" + java.lang.Thread.activeCount());
  }

  // for testing>>>>>*************>>>>>>>
  //  public void verifyEmailAndNumber(Document document, boolean isValid) {
  //    //    document = cursor.next();
  //    //          System.out.println("Orignal once number is " + (String) cursor.next().get("mobile_phone_number"))
  //    String number = (String) document.get("mobile_phone_number");
  //    //          System.out.println("Actual number : "+ number1.get("mobile_phone_number")+" Emails : "+number1.get("mailing_address"));
  //    String email = (String) document.get("mailing_address");
  //    if (email != null) {
  //      //            System.out.println("Orignal Mailing Address  " + email);
  //      Matcher matcher = pattern.matcher(email);
  //      if (matcher.matches() == true) {
  //        System.out.println(" Emails Sent " + email);
  //        //                  senderService.sendSimpleEmail(email, "This is test email body", "This is email subject");
  //      } else {
  //        //        invalidEmails++;
  //        //              System.out.println("Invalid Emails :.... " + email);
  //      }
  //    }
  //    if (email == null) {
  //      //      nullEmails++;
  //    }
  //    if (number == null) {
  //      System.out.println("Orignal Contact Number is  " + number);
  //      //            System.out.println("True");
  //      //      invalidCounter++;
  //      //      continue;
  //    } else {
  //      if (number.charAt(0) == '0') {
  //        number = number.replace("-", "");
  //        if (number.length() != 11) {
  //          System.out.println("Number have 0 but Invalid Number" + number);
  //          //          invalidCounter++;
  //        } else {
  //          number = number.substring(1, number.length());
  //          number = "+92" + number;
  //          isValid = true;
  //          //          validCounter++;
  //          System.out.println("Number start with 0 : " + number);
  //        }
  //      } else if (number.charAt(0) == '+' && number.charAt(1) == '9' && number.charAt(2) == '2') {
  //        if (number.length() != 13) {
  //          System.out.println("Number have +92 but Invalid Number" + number);
  //          //          invalidCounter++;
  //        } else {
  //          isValid = true;
  //          //          validCounter++;
  //          System.out.println("Number have +92: " + number);
  //        }
  //      } else {
  //        System.out.println("Invalid Number Format " + number);
  //        //        invalidCounter++;
  //      }
  //    }
  //    if (isValid) {
  //      //      smsCustomers.setMobile_phone_number(number);
  //      //      smsCustomers.setMailing_address(email);// Optional
  //      //      System.out.println("Sms Customer Number : " + smsCustomers.getMobile_phone_number() + " Mailing Customer Email : " + smsCustomers.getMailing_address());
  //      //            smsService.send(smsCustomers);
  //    }
  //  }

}
