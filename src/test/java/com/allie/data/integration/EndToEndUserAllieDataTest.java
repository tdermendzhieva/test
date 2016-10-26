package com.allie.data.integration;

import com.allie.data.dto.MeetingDTO;
import com.allie.data.dto.UserRequestDTO;
import com.allie.data.dto.UserResponseDTO;
import com.allie.data.factory.UserFactory;
import com.allie.data.jpa.model.Address;
import com.allie.data.jpa.model.User;
import com.allie.data.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

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

    UserRequestDTO userRequestDTO;

    @Before
    public void buildUserRequestDto() {
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

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setAllieId("allieId");
        userRequestDTO.setPushToken("pushToken");
        userRequestDTO.setNorms(norms);
        userRequestDTO.setAddresses(addresses);
        userRequestDTO.setFirstName("first");
        userRequestDTO.setMeetings(meetings);
        userRequestDTO.setEnrolledSkills(skills);
        userRequestDTO.setLastName("last");
        userRequestDTO.setNickname("nick");
    }

    @After
    public void dropDB() {
        //Verify that we have the right db
        assertThat(template.getDb().getName(), equalTo("TEST"));
        //Drop the test db
        template.getDb().dropDatabase();
        //make sure we have indexing
        Index index = new Index().on("allieId", Sort.Direction.ASC).unique();
        template.indexOps(User.class).ensureIndex(index);
        assertThat(template.getCollection("Users").getIndexInfo().size(), equalTo(2));
    }

    @Test
    public void testPostUser() {

        //Create a valid request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<UserRequestDTO> entity = new HttpEntity<>(userRequestDTO, headers);
        this.testRestTemplate.postForLocation("/allie-data/v1/users", entity);

        List<User> users =  repository.findAll();
        assertThat(users.size(), equalTo(1));
        assertThat(users.get(0).getAllieId(), equalTo(userRequestDTO.getAllieId()));
        assertThat(users.get(0).getAddresses().get("address3").getCity(), equalTo(userRequestDTO.getAddresses().get("address3").getCity()));
    }

    @Test
    public void testPostDuplicateUser() {

        //Create a valid request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<UserRequestDTO> entity = new HttpEntity<>(userRequestDTO, headers);
        this.testRestTemplate.postForLocation("/allie-data/v1/users", entity);
        this.testRestTemplate.postForLocation("/allie-data/v1/users", entity);

        List<User> users =  repository.findAll();
        assertThat(users.size(), equalTo(1));
        assertThat(users.get(0).getAllieId(), equalTo(userRequestDTO.getAllieId()));
        assertThat(users.get(0).getAddresses().get("address3").getCity(), equalTo(userRequestDTO.getAddresses().get("address3").getCity()));

    }

    @Test
    public void testPostUserNoAllieId() {
        //Create a valid request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        userRequestDTO.setAllieId(null);
        HttpEntity<UserRequestDTO> entity = new HttpEntity<>(userRequestDTO, headers);
        this.testRestTemplate.postForLocation("/allie-data/v1/users", entity);

        List<User> users =  repository.findAll();
        assertThat(users.size(), equalTo(0));

    }

    @Test
    public void testPostUserOnlyAllieId() {
        //Create a valid request
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setAllieId("allieId");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<UserRequestDTO> entity = new HttpEntity<>(userRequestDTO, headers);
        this.testRestTemplate.postForLocation("/allie-data/v1/users", entity);

        List<User> users =  repository.findAll();
        assertThat(users.size(), equalTo(1));
        assertThat(users.get(0).getAllieId(), equalTo(userRequestDTO.getAllieId()));

    }

    @Test
    public void testPostUserDifferentAllieIds() {

        //Create a valid request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<UserRequestDTO> entity = new HttpEntity<>(userRequestDTO, headers);
        this.testRestTemplate.postForLocation("/allie-data/v1/users", entity);
        userRequestDTO.setAllieId("allieId2");
        entity = new HttpEntity<>(userRequestDTO, headers);
        this.testRestTemplate.postForLocation("/allie-data/v1/users", entity);

        List<User> users =  repository.findAll();
        assertThat(users.size(), equalTo(2));
        assertThat(users.get(0).getAllieId(), equalTo("allieId"));
        assertThat(users.get(0).getAddresses().get("address3").getCity(), equalTo(userRequestDTO.getAddresses().get("address3").getCity()));
        assertThat(users.get(1).getAllieId(), equalTo(userRequestDTO.getAllieId()));
        assertThat(users.get(1).getAddresses().get("address3").getCity(), equalTo(userRequestDTO.getAddresses().get("address3").getCity()));

    }

    @Test
    public void testGet1User() {

        UserFactory factory = new UserFactory();
        User user = factory.createUser(userRequestDTO);
        repository.insert(user);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<Object> entity = new HttpEntity<>(null, headers);

        ResponseEntity<UserResponseDTO> resp = this.testRestTemplate.exchange("/allie-data/v1/users/" + user.getAllieId(), HttpMethod.GET, entity, UserResponseDTO.class);

        UserResponseDTO newUser = resp.getBody();
        assertThat(newUser.getAllieId(), equalTo(user.getAllieId()));
        assertThat(newUser.getNickname(), equalTo(user.getNickname()));
        assertThat(newUser.getAddresses().get("address5").getCity(), equalTo(user.getAddresses().get("address5").getCity()));

    }

    @Test
    public void testGetEachOfManyUsers() {
        UserFactory factory = new UserFactory();

        User user;
        for(int i = 0; i<10; i++) {
            userRequestDTO.setAllieId("id" + i);
            user = factory.createUser(userRequestDTO);
            repository.insert(user);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<UserRequestDTO> entity = new HttpEntity<>(userRequestDTO, headers);


        for(int i = 0; i<10; i++) {
            ResponseEntity<UserResponseDTO> resp = this.testRestTemplate.exchange("/allie-data/v1/users/" + "id" + i, HttpMethod.GET, entity, UserResponseDTO.class);
            assertThat(resp.getBody().getAllieId(), equalTo("id" + i));
        }
    }

    @Test
    public void testGetNonExtantAllieId() {
        UserFactory factory = new UserFactory();

        User user;
        for(int i = 0; i<10; i++) {
            userRequestDTO.setAllieId("id" + i);
            user = factory.createUser(userRequestDTO);
            repository.insert(user);
        }

        ResponseEntity<User> resp = this.testRestTemplate.getForEntity("/allie-id/v1/users/" + "badid", User.class);
        assertThat(resp.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));

    }
}
