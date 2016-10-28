package com.allie.data.repository;

import com.allie.data.jpa.model.Address;
import com.allie.data.jpa.model.Meeting;
import com.allie.data.jpa.model.User;
import com.mongodb.DBObject;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
@ActiveProfiles("DEVTEST")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    MongoTemplate template;

    @Autowired
    UserRepository repository;

    @After
    public void dropDB(){
        //Verify that we have the right db
        assertThat(template.getDb().getName(), equalTo("TEST"));
        //Drop the test db
        template.getDb().dropDatabase();
    }

    @Before
    public void setUp() {
        //Verify that we have the right db
        assertThat(template.getDb().getName(), equalTo("TEST"));
        //make sure we have indexing initialized
        Index index = new Index().on("allieId", Sort.Direction.ASC).unique();
        template.indexOps(User.class).ensureIndex(index);
        assertThat(template.getCollection("Users").getIndexInfo().size(), equalTo(2));

    }

    @Test
    public void testInsertDuplicateUsers() {
        User user = new User();

        user.setAllieId("allieId");

        try {
            repository.insert(user);
            repository.insert(user);
        }
        catch (Exception e) {
            assertThat(e.getClass(), equalTo(DuplicateKeyException.class));
            return;
        }
        assertThat("shouldn't get this far", false, equalTo(true));
    }

    @Test
    public void testGetAllAllieIds() {
        User user;
        for(int i = 0; i< 10; i++) {
            user = new User();
            user.setAllieId("id"+i);
            user.setLastName("ln"+i);
            repository.insert(user);
        }
        List<User> users = repository.findAllAllieIds();
        for(int i = 0; i<10; i++) {
            user =  users.get(i);
            assertThat(user.getAllieId(), equalTo("id" + i));
            assertThat(user.getLastName(), equalTo(null));
            assertThat(user.getDbId(), equalTo(null));
        }
    }

}
