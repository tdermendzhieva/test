package com.allie.data.integration;

import com.allie.data.dto.NotificationReceiptDTO;
import com.allie.data.jpa.model.NotificationReceipt;
import com.allie.data.repository.NotificationReceiptRepository;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jacob.headlee on 11/1/2016.
 */
@ActiveProfiles("DEVTEST")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEndNotificationReceiptTest {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    MongoTemplate template;

    @Autowired
    NotificationReceiptRepository repository;

    NotificationReceiptDTO good;
    NotificationReceiptDTO bad;

    NotificationReceipt notificationReceipt;

    String allieId;
    String timeStamp;
    String type;
    String trackingNumber;
    String messageClass;
    String userSelectedOption;
    String userEnteredOption;
    DateTime dt;

    @Before
    public void setUp() {
        notificationReceipt = new NotificationReceipt();
        good = new NotificationReceiptDTO();
        bad = new NotificationReceiptDTO();

        allieId = "allieId";
        timeStamp = "2016-11-01T12:01:38.435Z";
        type = "SOME_TYPE";
        trackingNumber = "tracking#######";
        userSelectedOption = "sure, why not?";
        userEnteredOption = "I like donuts";
        messageClass = "MESSAGE_CLASS";
        dt = new DateTime(timeStamp);

        bad.setType(trackingNumber);

        good.setAllieId(allieId);
        good.setTimestamp(timeStamp);
        good.setType(type);
        good.setTrackingNumber(trackingNumber);
        good.setUserSelectedOption(userSelectedOption);
        good.setMessageClass(messageClass);
        good.setUserEnteredOption(userEnteredOption);

        notificationReceipt.setAllieId(allieId);
        notificationReceipt.setTimestamp(dt);
        notificationReceipt.setType(type);
        notificationReceipt.setTrackingNumber(trackingNumber);
        notificationReceipt.setUserSelectedOption(userSelectedOption);
        notificationReceipt.setMessageClass(messageClass);

    }

    @After
    public void dropDB() {

        //Make sure we have the right db
        assertThat(template.getDb().getName(), equalTo("TEST"));
        //Then drop it
        template.getDb().dropDatabase();
    }

    @Test
    public void testGoodServiceCall() throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<NotificationReceiptDTO> entity = new HttpEntity<>(good, headers);
        ResponseEntity<Object> response = this.testRestTemplate.postForEntity("/allie-data/v1/notificationReceipts", entity,  Object.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.ACCEPTED));

        //wait on the thread that the post spins up
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread: threadSet) {
            if(thread.isAlive() && thread.getName().equals("insert-notification-receipt")) {
                thread.join(10 * 1000);
            }
        }
        
        List<NotificationReceipt> fromMongo = repository.findAll();
        assertThat(fromMongo.size(), equalTo(1));
        assertThat(fromMongo.get(0).getAllieId(), equalTo(allieId));
        assertThat(fromMongo.get(0).getMessageClass(), equalTo(messageClass));
        assertThat(fromMongo.get(0).getUserEnteredOption(), equalTo(userEnteredOption));
    }

    @Test
    public void testBadServiceCall() throws Exception{

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<NotificationReceiptDTO> entity = new HttpEntity<>(bad, headers);
        ResponseEntity<Object> response = this.testRestTemplate.postForEntity("/allie-data/v1/notificationReceipts", entity,  Object.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.ACCEPTED));

        //wait on the thread that the post spins up
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread: threadSet) {
            if(thread.isAlive() && thread.getName().equals("insert-notification-receipt")) {
                thread.join(5 * 1000);
            }
        }

        List<NotificationReceipt> fromMongo = repository.findAll();
        assertThat(fromMongo.size(), equalTo(0));

    }

    @Test
    public void testMixedServiceCalls() throws Exception{

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<NotificationReceiptDTO> entityBad = new HttpEntity<>(bad, headers);
        HttpEntity<NotificationReceiptDTO> entityGood = new HttpEntity<>(good, headers);

        ResponseEntity<Object> response;

        response = this.testRestTemplate.postForEntity("/allie-data/v1/notificationReceipts", entityBad,  Object.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.ACCEPTED));

        //wait on the thread that the post spins up
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread: threadSet) {
            if(thread.isAlive() && thread.getName().equals("insert-notification-receipt")) {
                thread.join(5 * 1000);
            }
        }

        response = this.testRestTemplate.postForEntity("/allie-data/v1/notificationReceipts", entityGood,  Object.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.ACCEPTED));
        //wait on the thread that the post spins up
        threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread: threadSet) {
            if(thread.isAlive() && thread.getName().equals("insert-notification-receipt")) {
                thread.join(5 * 1000);
            }
        }

        response = this.testRestTemplate.postForEntity("/allie-data/v1/notificationReceipts", entityBad,  Object.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.ACCEPTED));

        //wait on the thread that the post spins up
        threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread: threadSet) {
            if(thread.isAlive() && thread.getName().equals("insert-notification-receipt")) {
                thread.join(5 * 1000);
            }
        }

        entityGood.getBody().setAllieId("differentAllieId");
        response = this.testRestTemplate.postForEntity("/allie-data/v1/notificationReceipts", entityGood,  Object.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.ACCEPTED));

        //wait on the thread that the post spins up
        threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread: threadSet) {
            if(thread.isAlive() && thread.getName().equals("insert-notification-receipt")) {
                thread.join(5 * 1000);
            }
        }

        List<NotificationReceipt> fromMongo = repository.findAll();
        assertThat(fromMongo.size(), equalTo(2));
        assertThat(fromMongo.get(0).getType(), equalTo(type));
        assertThat(fromMongo.get(1).getTrackingNumber(), equalTo(trackingNumber));
    }

}
