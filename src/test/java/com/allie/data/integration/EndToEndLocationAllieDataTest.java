package com.allie.data.integration;

import com.allie.data.dto.Location;
import com.allie.data.dto.UserLocationDTO;
import com.allie.data.factory.LocationFactory;
import com.allie.data.jpa.model.LocationTelemetry;
import com.allie.data.repository.LocationTelemetryRepository;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jacob.headlee on 10/18/2016.
 * Tests that request persists correct data to Mongo
 */
@ActiveProfiles("DEVTEST")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEndLocationAllieDataTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private MongoTemplate template;

    @Autowired
    private LocationTelemetryRepository repository;

    private static List<UserLocationDTO> userLocationDTOs;

    private LocationFactory locationFactory = new LocationFactory();

    @Before
    public void setup() {
        //Building a list of dtos to pass to the controller
        //and non-essential to what we're testing
        userLocationDTOs = new ArrayList<UserLocationDTO>();
        UserLocationDTO userLocationDTO;
        for (int i=0; i<10; i++) {
            //make sure we pass a valid date
            //would use "0" + i but 00 isn't date
            String string = "1" + i;
            userLocationDTO = new UserLocationDTO("2010-10-" + string + "T00:00:00.000Z",
                    new Location(i * 10, i - 50), "test" + i);
            userLocationDTOs.add(userLocationDTO);
        }
    }

    @After
    public void dropDB() {

        //Make sure we have the right db
        assertThat(template.getDb().getName(), equalTo("TEST"));
        //Then drop it
        template.getDb().dropDatabase();
    }

    public void sendMixedRequestAndJoin() throws Exception{
        //This sends the request, which persists 10 of 20 items
        //We then wait for the request to complete so we don't get ahead of ourselves
        List<UserLocationDTO> dtos = new ArrayList<UserLocationDTO>(userLocationDTOs);
        for (int i=0; i<10; i++) {
            dtos.add(new UserLocationDTO(null, null, null));
        }

        //I don't care about the response
        //that's tested in LocationsControllerTest

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<List<UserLocationDTO>> entity = new HttpEntity<>(dtos, headers);
        this.testRestTemplate.postForLocation("/allie-data/v1/locations", entity);

        //wait on the thread that the post spins up
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread: threadSet) {
            if(thread.isAlive() && thread.getName().equals("insert-location")) {
                thread.join(5 * 1000);
            }
        }
    }

    public void sendGoodRequestAndJoin() throws Exception{
        //This sends the request, which hopefuly persists
        //We then wait for the request to complete so we don't get ahead of ourselves

        //Response tested in LocationsControllerTest
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<List<UserLocationDTO>> entity = new HttpEntity<>(userLocationDTOs, headers);
        this.testRestTemplate.postForLocation("/allie-data/v1/locations", entity);

        //wait on the thread that the post spins up
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread: threadSet) {
            if(thread.isAlive() && thread.getName().equals("insert-location")) {
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
        this.testRestTemplate.postForLocation("/allie-data/v1/locations", entity);

        //wait on the thread that the post spins up
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread: threadSet) {
            if(thread.isAlive() && thread.getName().equals("insert-location")) {
                thread.join(5 * 1000);
            }
        }
    }

    public void testCollectionLength(int i) {
        //make sure that all ten records per i requests made their way to the db
        List<LocationTelemetry> fromDB = repository.findAll();
        assertThat("Must have 10 records for each of " + i +" requests"
                , fromDB.size(), equalTo(i * 10));
    }

    public void testCollectionContent(int i) {
        List<LocationTelemetry> lts = new ArrayList<LocationTelemetry>();
        for (UserLocationDTO dto : userLocationDTOs) {
            lts.add(locationFactory.createLocationTelemetry(dto));
        }
        boolean failed = false;
        List<LocationTelemetry> fromDB = repository.findAll();

        for (LocationTelemetry ltGen : lts) {
            if(!failed) {
                int matches = 0;
                for (LocationTelemetry ltDB : fromDB) {
                    if (ltDB.getAllieId().equals(ltGen.getAllieId())) {
                        assertThat("Where allieId matches so must ts",
                                ltDB.getTimestamp(),
                                equalTo(ltGen.getTimestamp()));
                        assertThat("Where allieId matches so must log",
                                ltDB.getLocation(),
                                equalTo(ltGen.getLocation()));
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
