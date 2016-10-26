package com.allie.data.integration;

import com.allie.data.dto.UserEventDTO;
import com.allie.data.jpa.model.UserEvent;
import com.allie.data.repository.UserEventRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by jacob.headlee on 10/26/2016.
 */


@ActiveProfiles("DEVTEST")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEndUserEventSelect {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    MongoTemplate template;

    @Autowired
    UserEventRepository repository;

    @After
    public void dropDB() {
        //Verify that we have the right db
        assertThat(template.getDb().getName(), equalTo("TEST"));
        //Drop the test db
        template.getDb().dropDatabase();
    }

    @Before
    public void setUp() {
        assertThat(template.getDb().getName(), equalTo("TEST"));
        UserEvent event;
        for(int i = 0; i<20; i++) {
            event = new UserEvent();
            DateTime dt = new DateTime("2020-10-2" + i%4 + "T12:00:00.000Z");
            event.setEventReceivedTimestamp(dt);
            event.setAllieId("id"+ i%2);
            repository.insert(event);
        }
    }

    @Test
    public void testGetUserEvents() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<List<UserEventDTO>> entity = new HttpEntity<>(null, headers);

        ResponseEntity<UserEventDTO[]> resp = this.testRestTemplate.exchange("/allie-data/v1/user/id0/events?received_date=2020-10-20", HttpMethod.GET, entity, UserEventDTO[].class);

        UserEventDTO[] eventDTOs = resp.getBody();
        assertThat(eventDTOs.length, equalTo(5));
        assertThat(eventDTOs[3].getAllieId(), equalTo("id0"));

    }
}
