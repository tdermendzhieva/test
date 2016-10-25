package com.allie.data.integration;

import com.allie.data.dto.UserEventDTO;
import com.allie.data.jpa.model.UserEvent;
import com.allie.data.repository.UserEventRepository;
import com.mongodb.BasicDBObject;
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

import java.io.IOException;
import java.util.List;

import static com.allie.data.util.UserEventTestUtils.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by andrew.larsen on 10/25/2016.
 */

@ActiveProfiles("DEVTEST")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEndUserEventDataTest {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    MongoTemplate template;

    @Autowired
    UserEventRepository repository;

    private UserEventDTO goodRequest;
    private UserEventDTO badRequest;

    @Before
    public void setup() throws IOException {
        goodRequest = createEventDTO();
    }
    @After
    public void dropDB() {
        //Verify that we have the right db
        assertThat(template.getDb().getName(), equalTo("TEST"));
        //Drop the test db
        template.getDb().dropDatabase();
    }

    @Test
    public void testPostIsSuccessful(){
        ResponseEntity responseEntity = sendRequest(goodRequest);
        assertThat(responseEntity.getStatusCode().value(), equalTo(HttpStatus.CREATED.value()));
        //now make sure entry is in db
        List<UserEvent> userEvent = repository.findAll();
        //make sure there's only one inserted
        assertThat(userEvent.size(), equalTo(1));
        assertThat(userEvent.get(0).getAllieId(), equalTo(goodRequest.getAllieId()));
        assertThat(userEvent.get(0).getEventReceivedTimestamp(), equalTo(new DateTime(goodRequest.getEventReceivedTimestamp())));
        assertThat(userEvent.get(0).getNeuraJson(), equalTo(new BasicDBObject(goodRequest.getNeuraJson())));

    }
    @Test
    public void testPostBadRequestNoJson() throws IOException {
        badRequest = createEventDTONoJson();
        ResponseEntity responseEntity = sendRequest(badRequest);
        assertThat(responseEntity.getStatusCode().value(), equalTo(HttpStatus.BAD_REQUEST.value()));
        //now make sure entry is in db
        List<UserEvent> userEvent = repository.findAll();
        //make sure there's only one inserted
        assertThat(userEvent.size(), equalTo(0));

    }
    @Test
    public void testPostBadRequestNoAllieId() throws IOException {
        badRequest = createEventDTONoAllieId();
        ResponseEntity responseEntity = sendRequest(badRequest);
        assertThat(responseEntity.getStatusCode().value(), equalTo(HttpStatus.BAD_REQUEST.value()));
        //now make sure entry is in db
        List<UserEvent> userEvent = repository.findAll();
        //make sure there's only one inserted
        assertThat(userEvent.size(), equalTo(0));

    }
    @Test
    public void testPostBadRequestNoTimestamp() throws IOException {
        badRequest = createEventDTONoTimestamp();
        ResponseEntity responseEntity = sendRequest(badRequest);
        assertThat(responseEntity.getStatusCode().value(), equalTo(HttpStatus.BAD_REQUEST.value()));
        //now make sure entry is in db
        List<UserEvent> userEvent = repository.findAll();
        //make sure there's only one inserted
        assertThat(userEvent.size(), equalTo(0));

    }
    @Test
    public void testPostBadRequestInvalidFormatTimestamp() throws IOException {
        badRequest = createEventDTONoTimestamp();
        badRequest.setEventReceivedTimestamp("2015/10/10");
        ResponseEntity responseEntity = sendRequest(badRequest);
        assertThat(responseEntity.getStatusCode().value(), equalTo(HttpStatus.BAD_REQUEST.value()));
        //now make sure entry is in db
        List<UserEvent> userEvent = repository.findAll();
        //make sure there's only one inserted
        assertThat(userEvent.size(), equalTo(0));

    }
    @Test
    public void testPostBadRequestInvalidTimestamp() throws IOException {
        badRequest = createEventDTONoTimestamp();
        badRequest.setEventReceivedTimestamp("20-54-23T18:25:43.511Z");
        ResponseEntity responseEntity = sendRequest(badRequest);
        assertThat(responseEntity.getStatusCode().value(), equalTo(HttpStatus.BAD_REQUEST.value()));
        //now make sure entry is in db
        List<UserEvent> userEvent = repository.findAll();
        //make sure there's only one inserted
        assertThat(userEvent.size(), equalTo(0));

    }
    /**
     * Helper method to send request
     * @param request
     * @return
     */
    public ResponseEntity sendRequest(UserEventDTO request){
        //Create a valid request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<UserEventDTO> entity = new HttpEntity<>(request, headers);
        return this.testRestTemplate.postForEntity("/allie-data/v1/events", entity, String.class);

    }
}
