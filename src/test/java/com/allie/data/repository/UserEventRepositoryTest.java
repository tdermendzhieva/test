package com.allie.data.repository;

import com.allie.data.jpa.model.UserEvent;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by jacob.headlee on 10/25/2016.
 */
@ActiveProfiles("DEVTEST")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserEventRepositoryTest {
    @Autowired
    MongoTemplate template;

    @Autowired
    UserEventRepository repository;

    @After
    public void dropDB(){
        //Verify that we have the right db
        assertThat(template.getDb().getName(), equalTo("TEST"));
        //Drop the test db
        template.getDb().dropDatabase();
    }

    @Before
    public void setUpDB(){
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
    public void testFindUserEventsFindsTheRightEvents() {
        List<UserEvent> events = repository.findUserEvents("id0", new DateTime("2020-10-20"), new DateTime("2020-10-21"));
        assertThat(events.size(), equalTo(5));
        for(UserEvent event : events) {
            assertThat(event.getAllieId(), equalTo("id0"));
            assertThat(event.getEventReceivedTimestamp(), equalTo(new DateTime("2020-10-20T12:00:00.000Z")));
        }

    }

}
