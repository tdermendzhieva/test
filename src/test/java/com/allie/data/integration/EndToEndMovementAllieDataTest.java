package com.allie.data.integration;

import com.allie.data.dto.UserMovementDTO;
import com.allie.data.factory.LocationFactory;
import com.allie.data.factory.MovementFactory;
import com.allie.data.jpa.model.MovementTelemetry;
import com.allie.data.repository.MovementTelemetryRepository;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
@ActiveProfiles("DEVTEST")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEndMovementAllieDataTest {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    MongoTemplate template;

    @Autowired
    MovementTelemetryRepository repository;

    private List<UserMovementDTO> userMovementDTOs;

    private MovementFactory movementFactory = new MovementFactory();

    @Before
    public void setUp(){
        //Building a list of dtos
        userMovementDTOs = new ArrayList<UserMovementDTO>();
        UserMovementDTO userMovementDTO;
        for (int i=0; i<10; i++) {
            //make sure we pass a valid date
            //would use "0" + i but 00 isn't date
            String string = "1" + i;
            userMovementDTO = new UserMovementDTO("2010-10-" + string + "T00:00:00.000Z",
                     i%2 != 0, "test" + i);
            userMovementDTOs.add(userMovementDTO);
        }
    }

    @After
    public void dropDB(){
        //Verify that we have the right db
        assertThat(template.getDb().getName(), equalTo("TEST"));
        //Drop the test db
        template.getDb().dropDatabase();
    }

    public void sendGoodRequestAndJoin() throws Exception{
        //Create a valid request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<List<UserMovementDTO>> entity = new HttpEntity<>(userMovementDTOs, headers);
        this.testRestTemplate.postForLocation("/allie-data/v1/movements", entity);

        //wait on the thread that the post spins up
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread: threadSet) {
            if(thread.isAlive() && thread.getName().equals("insert-movement")) {
                thread.join(5 * 1000);
            }
        }
    }
    public void sendBadRequestAndJoin() throws Exception{
        //This sends an invalid request,
        //We then wait for the request to complete so we don't get ahead of ourselves

        List<Object> badData = new ArrayList<Object>();
        badData.add(new LocationFactory());
        badData.add(new Integer(8));
        badData.add("bad data");
        //I don't care about the response
        //that's tested in LocationsControllerTest
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<List<Object>> entity = new HttpEntity<>(badData, headers);
        this.testRestTemplate.postForLocation("/allie-data/v1/movements", entity);

        //wait on the thread that the post spins up
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread: threadSet) {
            if(thread.isAlive() && thread.getName().equals("insert-movement")) {
                thread.join(5 * 1000);
            }
        }
    }

    public void sendMixedRequestAndJoin() throws Exception{
        //Start with the good list
        List<UserMovementDTO> dtos = new ArrayList<>(userMovementDTOs);
        //add some junk
        for (int i=0; i<10; i++) {
            dtos.add(new UserMovementDTO(null, null, null));
        }
        //send it off
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<List<UserMovementDTO>> entity = new HttpEntity<>(dtos, headers);
        this.testRestTemplate.postForLocation("/allie-data/v1/movements", entity);

        //wait on the thread that the post spins up
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread: threadSet) {
            if(thread.isAlive() && thread.getName().equals("insert-movement")) {
                thread.join(5 * 1000);
            }
        }
    }
    public void testCollectionLength(int i) {
        //make sure that all ten records per i requests made their way to the db
        List<MovementTelemetry> fromDB = repository.findAll();
        assertThat("Must have 10 records for each of " + i +" requests"
                , fromDB.size(), CoreMatchers.equalTo(i * 10));
    }
    public void testCollectionContent(int i) {
        List<MovementTelemetry> mts = new ArrayList<MovementTelemetry>();
        for (UserMovementDTO dto : userMovementDTOs) {
            mts.add(movementFactory.createMovementTelemetry(dto));
        }
        boolean failed = false;
        List<MovementTelemetry> fromDB = repository.findAll();

        for (MovementTelemetry mtGen : mts) {
            if(!failed) {
                int matches = 0;
                for (MovementTelemetry mtDB : fromDB) {
                    if (mtDB.getAllieId().equals(mtGen.getAllieId())) {
                        assertThat("Where allieId matches so must ts",
                                mtDB.getTimestamp(),
                                CoreMatchers.equalTo(mtGen.getTimestamp()));
                        assertThat("Where allieId matches so must log",
                                mtDB.getTimestamp(),
                                CoreMatchers.equalTo(mtGen.getTimestamp()));
                        matches++;
                    }
                }
                assertThat("A record should be recorded for each allieId no match found for", matches == i);
            }
        }
    }

    @Test
    public void testTwoGoodServiceCalls () throws Exception {
        sendGoodRequestAndJoin();
        testCollectionLength(1);
        testCollectionContent(1);
        sendGoodRequestAndJoin();
        testCollectionLength(2);
        testCollectionContent(2);

    }

    @Test
    public void testTwoBadServiceCalls () throws Exception {
        sendBadRequestAndJoin();
        testCollectionLength(0);
        sendBadRequestAndJoin();
        testCollectionLength(0);

    }

    @Test
    public void testBadGoodServiceCalls () throws Exception {
        sendBadRequestAndJoin();
        testCollectionLength(0);
        sendGoodRequestAndJoin();
        testCollectionLength(1);

    }

    @Test
    public void testGoodBadServiceCalls () throws Exception {
        sendGoodRequestAndJoin();
        testCollectionLength(1);
        testCollectionContent(1);
        sendBadRequestAndJoin();
        testCollectionLength(1);
        testCollectionContent(1);

    }

    @Test
    public void testTwoMixedServiceCalls () throws Exception {
        sendMixedRequestAndJoin();
        testCollectionLength(1);
        testCollectionContent(1);
        sendMixedRequestAndJoin();
        testCollectionLength(2);
        testCollectionContent(2);
    }

    @Test
    public void testMixedGoodServiceCalls () throws Exception {
        sendMixedRequestAndJoin();
        testCollectionLength(1);
        testCollectionContent(1);
        sendGoodRequestAndJoin();
        testCollectionLength(2);
        testCollectionContent(2);
    }

    @Test
    public void testMixedBadServiceCalls () throws Exception {
        sendMixedRequestAndJoin();
        testCollectionLength(1);
        testCollectionContent(1);
        sendBadRequestAndJoin();
        testCollectionLength(1);
        testCollectionContent(1);

    }


}
