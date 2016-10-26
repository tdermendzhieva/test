package com.allie.data.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by andrew.larsen on 10/26/2016.
 */
@ActiveProfiles("DEVTEST")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthCheckTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    @Test
    public void testGetUserEventsReturns404() {

        ResponseEntity<Object> resp = this.testRestTemplate.getForEntity("/healthCheck",Object.class);

        assertThat(resp.getStatusCode().value(), equalTo(200));

    }
}
