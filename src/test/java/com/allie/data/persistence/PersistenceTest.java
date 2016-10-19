package com.allie.data.persistence;

import com.allie.data.dto.Location;
import com.allie.data.dto.UserLocationDTO;
import com.allie.data.jpa.model.LocationTelemetry;
import com.allie.data.repository.LocationTelemetryRepository;
import com.allie.data.service.LocationService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by jacob.headlee on 10/18/2016.
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PersistenceTest {
    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    MongoTemplate template;

    @Autowired
    LocationService locationService;

    @Autowired
    LocationTelemetryRepository repository;

    @Before
    public void setup() {
        if(template.getDb().getName().equals("test")){
            template.getDb().dropDatabase();
        }
    }

    @Test
    public void testControllerToPersistence () throws Exception {
        List<UserLocationDTO> userLocationDTOs = new ArrayList<UserLocationDTO>();
        UserLocationDTO userLocationDTO;
        for (int i=0; i<10; i++) {
            String istring = "1" + i;
            userLocationDTO = new UserLocationDTO("2010-10-" + istring + "T00:00:00.000Z", new Location(i * 10, i - 50), "test" + i);
            userLocationDTOs.add(userLocationDTO);
        }
        Object throwaway = this.testRestTemplate.postForObject("/allie-data/v1/locations", userLocationDTOs, Object.class);
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

        for (Thread thread: threadSet) {
            if(thread.isAlive() && thread.getName().equals("insert-location")) {
                thread.join(5 * 1000);
            }
        }


        Thread.sleep(10 * 1000);
        List<LocationTelemetry> fromDB = repository.findAll();
        assertThat(fromDB.size(), equalTo(10));
    }

    @After
    public void unset() {
        if(template.getDb().getName().equals("test")){
            template.getDb().dropDatabase();
        }
    }
}
