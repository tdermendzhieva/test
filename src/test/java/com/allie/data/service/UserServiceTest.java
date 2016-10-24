package com.allie.data.service;

import com.allie.data.dto.UserDTO;
import com.allie.data.factory.UserFactory;
import com.allie.data.jpa.model.Address;
import com.allie.data.jpa.model.Meeting;
import com.allie.data.jpa.model.User;
import com.allie.data.repository.UserRepository;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.given;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
@ActiveProfiles("DEVTEST")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @MockBean
    UserFactory factory;
    @MockBean
    UserRepository repository;
    @Autowired
    UserService service;


    @Test
    public void testInsertUser(){
        UserDTO userDTO = new UserDTO();
        User user = new User();

        Map<String, Address> addresses = new HashMap<>();
        Map<String, String> norms = new HashMap<>();
        Map<String, Meeting> meetings = new HashMap<>();
        List<String> skills = new ArrayList<>();

        Address address;
        Meeting meeting;

        for (int i =0; i<10; i++) {
            address = new Address();
            address.setState("state" + i);
            address.setPostalCode("zip" + i);
            address.setCity("city" + i);
            address.setAddress1("address" + i);

            meeting = new Meeting();
            meeting.setDateTime(new DateTime("2000-10-1"  + i + "T10:10:10.111Z"));

            meetings.put("meeting" + i, meeting);
            addresses.put("key" + i, address);
            norms.put("key" + i, "val" + i);
            skills.add("skill" + i);
        }

        user.setAddresses(addresses);
        user.setAllieId("allieId");
        user.setUpdatedTimeStamp(new DateTime());
        user.setCreatedTimeStamp(new DateTime());
        user.setNorms(norms);
        user.setPushToken("token");
        user.setMeetings(meetings);
        user.setLastName("first");
        user.setFirstName("last");
        user.setEnrolledSkills(skills);

        given(factory.createUser(userDTO)).willReturn(user);
        given(repository.insert(user)).willReturn(user);

        assertThat(service.insertUser(userDTO), equalTo(user));
    }

    @Test
    public void testInsertInvalidUser(){

        UserDTO userDTO = new UserDTO();
        User user = new User();


        given(factory.createUser(userDTO)).willReturn(user);

        try {
            service.insertUser(userDTO);
        } catch (Exception e) {
            assertThat(e.getClass(), equalTo(IllegalArgumentException.class));
            return;
        }
        assertThat("should not get this far", true, equalTo(false));
    }
}
