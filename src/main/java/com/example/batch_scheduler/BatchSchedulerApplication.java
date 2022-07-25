package com.example.batch_scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@SpringBootApplication
@EnableAsync
public class BatchSchedulerApplication {

//	@Autowired
//	private EmailSenderService senderService;

	public static void main(String[] args) {
		SpringApplication.run(BatchSchedulerApplication.class, args);
	}
	// Scheduling jobs
//	@Scheduled(fixedRate = 2000L )
//	void someJob() throws InterruptedException {
//		System.out.println("Now is"+ new Date());
//		Thread.sleep(1000L);
//	}

	//send email
//	@EventListener(ApplicationReadyEvent.class)
//	public void triggerMail() throws MessagingException {
//		senderService.sendSimpleEmail("danishnaseer98@yahoo.com",
//				"This is test email body",
//				"This is email subject");
//
//	}
}

