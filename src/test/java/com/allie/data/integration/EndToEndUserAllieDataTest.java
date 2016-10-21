package com.allie.data.integration;

import com.allie.data.dto.MeetingDTO;
import com.allie.data.dto.UserDTO;
import com.allie.data.jpa.model.Address;
import com.allie.data.jpa.model.User;
import com.allie.data.repository.UserRepository;
import com.mongodb.DBObject;
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
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEndUserAllieDataTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    MongoTemplate template;

    @Autowired
    UserRepository repository;

    UserDTO userDTO;

    @Before
    public void buildUserDto() {
        Map<String, String> norms = new HashMap<>();
        Map<String, Address> addresses = new HashMap<>();
        Map<String, MeetingDTO> meetings = new HashMap<>();
        List<String> skills = new ArrayList<>();

        for(int i =0; i<10; i++) {
            Address address = new Address();
            MeetingDTO meeting = new MeetingDTO();

            address.setAddress1("address"+i);
            address.setCity("city"+i);
            address.setPostalCode("zip"+i);
            address.setState("state"+i);

            meeting.setDateTime("2010-10-1"+i+"T10:10:10.101Z");

            norms.put("norm"+i, "val"+i);
            addresses.put("address"+i, address);
            meetings.put("meeting"+i, meeting);
            skills.add("skill"+i);
        }

        userDTO = new UserDTO();
        userDTO.setAllieId("allieId");
        userDTO.setPushToken("pushToken");
        userDTO.setNorms(norms);
        userDTO.setAddresses(addresses);
        userDTO.setFirstName("first");
        userDTO.setMeetings(meetings);
        userDTO.setEnrolledSkills(skills);
        userDTO.setLastName("last");
        userDTO.setNickname("nick");
    }

    @After
    public void dropDB() {
        //Verify that we have the right db
        assertThat(template.getDb().getName(), equalTo("TEST"));
        //Drop the test db
        template.getDb().dropDatabase();
    }

//    @Test
//    public void testHasIndex() throws Exception {
//        User user = new User();
//        user.setAllieId("indexTest");
//        repository.insert(user);
//        assertThat("need the collection", template.collectionExists("Users"));
//        List<DBObject> list = template.getCollection("Users").getIndexInfo();
//        assertThat(list.size(), equalTo(2));
//    }

    @Test
    public void testPostUser() {

        //Create a valid request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<UserDTO> entity = new HttpEntity<>(userDTO, headers);
        this.testRestTemplate.postForLocation("/allie-data/v1/users", entity);

        List<User> users =  repository.findAll();
        assertThat(users.size(), equalTo(1));
        assertThat(users.get(0).getAllieId(), equalTo(userDTO.getAllieId()));
        assertThat(users.get(0).getAddresses().get("address3").getCity(), equalTo(userDTO.getAddresses().get("address3").getCity()));
    }

//    @Test
//    public void testPostDuplicateUser() {
//
//        //Create a valid request
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.add("x-allie-request-id", "req-id");
//        headers.add("x-allie-correlation-id", "corr-id");
//        HttpEntity<UserDTO> entity = new HttpEntity<>(userDTO, headers);
//        this.testRestTemplate.postForLocation("/allie-data/v1/users", entity);
//        this.testRestTemplate.postForLocation("/allie-data/v1/users", entity);
//
//        List<User> users =  repository.findAll();
//        assertThat(users.size(), equalTo(1));
//        assertThat(users.get(0).getAllieId(), equalTo(userDTO.getAllieId()));
//        assertThat(users.get(0).getAddresses().get("address3").getCity(), equalTo(userDTO.getAddresses().get("address3").getCity()));
//
//    }

    @Test
    public void testPostUserNoAllieId() {
        //Create a valid request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        userDTO.setAllieId(null);
        HttpEntity<UserDTO> entity = new HttpEntity<>(userDTO, headers);
        this.testRestTemplate.postForLocation("/allie-data/v1/users", entity);

        List<User> users =  repository.findAll();
        assertThat(users.size(), equalTo(0));

    }

    @Test
    public void testPostUserOnlyAllieId() {
        //Create a valid request
        UserDTO userDTO = new UserDTO();
        userDTO.setAllieId("allieId");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<UserDTO> entity = new HttpEntity<>(userDTO, headers);
        this.testRestTemplate.postForLocation("/allie-data/v1/users", entity);

        List<User> users =  repository.findAll();
        assertThat(users.size(), equalTo(1));
        assertThat(users.get(0).getAllieId(), equalTo(userDTO.getAllieId()));

    }

    @Test
    public void testPostUserDifferentAllieIds() {

        //Create a valid request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<UserDTO> entity = new HttpEntity<>(userDTO, headers);
        this.testRestTemplate.postForLocation("/allie-data/v1/users", entity);
        userDTO.setAllieId("allieId2");
        entity = new HttpEntity<>(userDTO, headers);
        this.testRestTemplate.postForLocation("/allie-data/v1/users", entity);

        List<User> users =  repository.findAll();
        assertThat(users.size(), equalTo(2));
        assertThat(users.get(0).getAllieId(), equalTo("allieId"));
        assertThat(users.get(0).getAddresses().get("address3").getCity(), equalTo(userDTO.getAddresses().get("address3").getCity()));
        assertThat(users.get(1).getAllieId(), equalTo(userDTO.getAllieId()));
        assertThat(users.get(1).getAddresses().get("address3").getCity(), equalTo(userDTO.getAddresses().get("address3").getCity()));


    }

}
