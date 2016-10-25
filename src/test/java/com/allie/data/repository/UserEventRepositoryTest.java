package com.allie.data.repository;

import com.allie.data.jpa.model.UserEvent;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

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
        UserEvent event;
        for(int i = 0; i<10; i++) {
            event = new UserEvent();
            DateTime dt = new DateTime("2020-10-2" + i%2);
//            event.s
            
        }

    }
}
