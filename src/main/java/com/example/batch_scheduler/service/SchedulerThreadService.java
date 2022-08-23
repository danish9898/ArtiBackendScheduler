package com.example.batch_scheduler.service;

import com.example.batch_scheduler.model.Campaigns;
import com.example.batch_scheduler.model.Segments;
import com.example.batch_scheduler.model.SmsCustomers;
import com.example.batch_scheduler.model.TriggerCollection;
import com.example.batch_scheduler.model.Triggerlog;
import com.example.batch_scheduler.model.Triggers;
import com.example.batch_scheduler.notifications.EmailSenderService;
import com.example.batch_scheduler.notifications.SmsService;

import com.example.batch_scheduler.repository.TriggerRepository;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import lombok.SneakyThrows;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;
import java.util.Calendar;
import java.util.TimeZone;
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

  @Autowired
  private InvalidCustomerService invalidCustomerService;


  @Autowired
  private TriggerLogService triggerLogService;

  @Autowired
  private TriggerCollectionService triggerCollectionService;

  // Create Formatter class object
  Formatter format = new Formatter();
  // Creating a calendar
  Calendar gfg_calender = Calendar.getInstance();

  // Emails pattern verification

  String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
  Pattern pattern = Pattern.compile(regex);

  public String getDayToString() {
    Date date = new Date();
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    //    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
    String dayWeekText = new SimpleDateFormat("EEEE").format(date);
    return dayWeekText;
  }

  // Method to get number of days in month
  public static int getNumberOfDaysInMonth(int year, int month) {
    YearMonth yearMonthObject = YearMonth.of(year, month);
    int daysInMonth = yearMonthObject.lengthOfMonth();
    return daysInMonth;
  }

  public boolean getDateToString(String date) {
    //2020-06-30
    LocalDate localDate = LocalDate.now();
    Calendar cal = Calendar.getInstance();
    int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

    //    LocalDate date1 = LocalDate.parse(date);
    if (!date.matches(String.valueOf(localDate))) {
      date = date.replace("-", "");
      String year = new String();
      String month = new String();
      String day = new String();
      for (int i = 0; i < date.length(); i++) {
        if (i <= 3) {
          year += date.charAt(i);
        } else if (i > 3 && i <= 5) {
          month += date.charAt(i);
        } else if (i > 5 && i <= 7) {
          day += date.charAt(i);
        } else {
          continue;
        }
      }
      int integeryear = Integer.parseInt(year);
      int integerday = Integer.parseInt(day);
      int integermonth = Integer.parseInt(month);
      int numberOfDaysInMonthays = getNumberOfDaysInMonth(integeryear, integermonth);

      if (localDate.getYear() < integeryear) {
        return false;
      } else if (integerday == localDate.getDayOfMonth() && integermonth != localDate.getMonthValue()) {
        return true;
      } else if ((integerday == localDate.getDayOfMonth()) && (integermonth == localDate.getMonthValue()) && (integeryear != localDate.getYear())) {
        return true;
      } else if ((integerday > 28) && (numberOfDaysInMonthays < 29) && (dayOfMonth == 28)) {
        return true;
      } else if ((integerday > 28) && (numberOfDaysInMonthays == 29) && (dayOfMonth == 29)) {
        return true;
      } else if ((integerday > 30) && (numberOfDaysInMonthays == 30) && (dayOfMonth == 30)) {
        return true;
      } else {
        return false;
      }
    }
    return false;
  }

  public void getCampaigns() {

    String uri = "mongodb://localhost";
    MongoClient mongoClient = MongoClients.create(uri);
    MongoDatabase database = mongoClient.getDatabase("ArtiDb_Collections_Data_Export");

    List<Triggers> allTrigger = triggerRepository.findAll();

    format = new Formatter();
    format.format("%tl:%tM", gfg_calender, gfg_calender);

    for (Triggers item : allTrigger) {
      // Once
      if (item.getType().equals("once")) {
        if ((item.getIs_executed().equals(false)) && (item.getTime().equals(format))) {
          new Thread(() -> Scheduler(item, database)).start();
        } else if (item.getIs_executed().equals(true) && (item.getRecursive().equals(true)) && (item.getTime().equals(format))) {
          new Thread(() -> Scheduler(item, database)).start();
        } else {
          continue;
        }
      }
      // Daily
      else if (item.getType().equals("daily")) {
        if ((item.getIs_executed().equals(false)) && (item.getTime().equals(format))) {
          new Thread(() -> Scheduler(item, database)).start();
        } else if ((item.getIs_executed().equals(true)) && (item.getRecursive().equals(true)) && (item.getTime().equals(format))) {
          new Thread(() -> Scheduler(item, database)).start();
        } else {
          continue;
        }
      }
      // Weekly
      else if (item.getType().equals("weekly")) {
        if ((item.getIs_executed().equals(false)) && (item.getTime().equals(format)) && (item.getDay().equalsIgnoreCase(getDayToString()))) {
          new Thread(() -> Scheduler(item, database)).start();
        } else if ((item.getIs_executed().equals(true)) && (item.getDay().equalsIgnoreCase(getDayToString())) && (item.getTime().equals(format))) {
          new Thread(() -> Scheduler(item, database)).start();
        } else {
          continue;
        }
      }
      // Monthly
      else if (item.getType().equals("monthly")) {
        if ((item.getIs_executed().equals(false)) && (item.getTime().equals(format))) {
          new Thread(() -> Scheduler(item, database)).start();
        } else if ((item.getIs_executed().equals(true)) && (item.getTime().equals(format)) && (getDateToString(item.getDate()))) {
          new Thread(() -> Scheduler(item, database)).start();
        } else {
          continue;
        }
      } else {
        System.out.println("No Thread Created : ");
        //        System.out.println("Number of active threads from the given thread: " + "Thread.activeCount()" + java.lang.Thread.activeCount());
        System.out.println("Current Thread is alive : " + Thread.currentThread().getName());
      }
    }
  }

  public void setTrigger(ObjectId id) {

    Query query = new Query();
    query.addCriteria(Criteria.where("_id").is(id));

    Update update = new Update();
    update.set("is_executed", true);

    Triggers userTest = mongoOperations.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Triggers.class);
    //    System.out.println("userTest - " + userTest);
  }

  //  @Async
  @SneakyThrows
  public void Scheduler(Triggers item, MongoDatabase database) {

    DateTimeFormatter currentdate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // Use Pak's time zone to format the date in
    sdf.setTimeZone(TimeZone.getTimeZone("Pakistan Standard Time (PKT)"));
    LocalDateTime now = LocalDateTime.now();
    String date = currentdate.format(now);
    Date dateformate = sdf.parse(date);

    System.out.println("Current Item : " + item + " and Thread name : " + Thread.currentThread().getName());
    Optional<Campaigns> campaign = campaignService.getById(item.getCampaign());
    Optional<Segments> segment = segmentService.getById(campaign.get().getSegment());
    String index[] = segment.get().getSegment_collection().split("_");

    MongoCollection collection = database.getCollection(index[0]);
    try {
      database.createCollection("triggerinvalidcollection");
      System.out.println("Collection created successfully");
    } catch (MongoCommandException e) {
      MongoCollection mongoCollection = database.getCollection("triggerinvalidcollection");
    }
    TriggerCollection triggerCollection = new TriggerCollection();
    triggerCollection.setTrigger_id(item.get_id());
    triggerCollection.setCampaign_id(item.getCampaign());
    triggerCollection.setSegment_id(campaign.get().getSegment());
    triggerCollection.setType(item.getType());
    triggerCollection.setDate_executed(dateformate);
    MongoCursor<Document> cursor = collection.find().iterator();
    int invalidCounter = 0;
    int validCounter = 0;
    int nullEmails = 0;
    // for testing  atleast one notification sent for email and  sms ...****
    int smslimit = 0;
    Document document;
    try {
      while (cursor.hasNext()) {
        //            cursor.next();
        document = cursor.next();
        Document details = new Document();
        details = (Document) document.get("clr");
        String number = (String) details.get("cust_mob_phone");
        String email = (String) details.get("cust_email_id");

        boolean Valid = false;
        boolean inValid = false;
        if (campaign.get().getType().equalsIgnoreCase("email")) {
          if (email != null || !email.matches("")) {
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches() == true) {
              //              senderService.sendSimpleEmail(email, "This is test email body", "This is email subject");
            } else {
              invalidCounter++;
              String reason = "001";// for email failed
              saveNewTriggerCollection(triggerCollection, dateformate, document, reason);
            }
          } else if (email == null || email.matches("")) {
            nullEmails++;
          } else {
            continue;
          }
        }
        if (campaign.get().getType().equalsIgnoreCase("SMS")) {
          if (number == null || number.matches("")) {
            invalidCounter++;
            inValid = true;
          } else {
            if (number.charAt(0) == '0') {
              number = number.replace("-", "");
              if (number.length() != 11) {
                invalidCounter++;
                inValid = true;
              } else {
                number = number.substring(1, number.length());
                number = "+92" + number;
                Valid = true;
                smslimit++;
                validCounter++;
              }
            } else if (number.charAt(0) == '+' && number.charAt(1) == '9' && number.charAt(2) == '2') {
              if (number.length() != 13) {
                invalidCounter++;
                inValid = true;
              } else {
                Valid = true;
                smslimit++;
                validCounter++;
              }
            } else {
              invalidCounter++;
              inValid = true;
            }
          }
          if (Valid) {
            if (smslimit == 1) {
              //              System.out.println("Sms Limit is One>>>>>>>>>>>>>>>>>>>");
              //              //              Enter your number for test in smsCustomers.setMobile_phone_number()
              //              smsCustomers.setMobile_phone_number("+923105405425");
              //              smsCustomers.setMailing_address(email);
              //              smsService.send(smsCustomers);
            }
          }
          if (inValid) {
            String reason = "002";// for SMS failed
            saveNewTriggerCollection(triggerCollection, dateformate, document, reason);
          }
        }
      }

      System.out.println("Invalid Record is : " + invalidCounter);
      System.out.println("Valid Record is : " + validCounter);
      System.out.println("Invalid Emails = " + invalidCounter + " and Null Email values = " + nullEmails);
      Triggerlog triggerlog = new Triggerlog();
      triggerlog.setTrigger_id(item.get_id());
      String formatDateTime = now.format(currentdate);
      triggerlog.setDate_executed(formatDateTime);
      triggerlog.setInvalid_counts(invalidCounter);
      triggerlog.setValid_counts(validCounter);
      triggerlog.setNull_count(0);
      triggerLogService.saveTriggerLog(triggerlog);
      item.setIs_executed(true);
      setTrigger(item.get_id());
    } finally {
      cursor.close();
    }
  }

  public void saveNewTriggerCollection(TriggerCollection triggerCollection, Date date, Document document, String reason) {
    TriggerCollection triggerCollection1 = new TriggerCollection();
    triggerCollection1.setTrigger_id(triggerCollection.getTrigger_id());
    triggerCollection1.setCampaign_id(triggerCollection.getCampaign_id());
    triggerCollection1.setSegment_id(triggerCollection.getSegment_id());
    triggerCollection1.setCustomer_id((ObjectId) document.get("_id"));
    triggerCollection1.setDate_executed(date);
    triggerCollection1.setReason(reason);
    triggerCollection1.setType(triggerCollection.getType());
    triggerCollectionService.saveTriggerCollection(triggerCollection1);
  }
}
