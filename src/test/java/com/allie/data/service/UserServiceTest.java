package com.allie.data.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;

import com.mongodb.MongoException;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.allie.data.dto.MeetingDTO;
import com.allie.data.dto.UserRequestDTO;
import com.allie.data.dto.UserResponseDTO;
import com.allie.data.factory.UserFactory;
import com.allie.data.jpa.model.Address;
import com.allie.data.jpa.model.Meeting;
import com.allie.data.jpa.model.User;
import com.allie.data.repository.UserRepository;

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



    UserRequestDTO userRequestDTO;
    User user;
    UserResponseDTO userResponseDTO;

    @Before
    public void setUp() {

        userRequestDTO = new UserRequestDTO();
        user = new User();
        userResponseDTO = new UserResponseDTO();

        Map<String, Address> addresses = new HashMap<>();
        Map<String, String> norms = new HashMap<>();
        Map<String, Meeting> meetings = new HashMap<>();
        Map<String, MeetingDTO> meetingDTOs = new HashMap<>();
        List<String> skills = new ArrayList<>();

        Address address;
        Meeting meeting;
        MeetingDTO meetingDTO;

        for (int i =0; i<10; i++) {
            address = new Address();
            address.setState("state" + i);
            address.setPostalCode("zip" + i);
            address.setCity("city" + i);
            address.setAddress1("address" + i);

            meetingDTO = new MeetingDTO();
            meetingDTO.setTitle("Standup");
            meetingDTO.setDateTime("2000-10-1"  + i + "T10:10:10.111Z");

            meeting = new Meeting();
            meeting.setTitle("Standup");
            meeting.setDateTime(new DateTime("2000-10-1"  + i + "T10:10:10.111Z"));

            meetingDTOs.put("meeting" +i, meetingDTO);
            meetings.put("meeting" + i, meeting);
            addresses.put("key" + i, address);
            norms.put("key" + i, "val" + i);
            skills.add("skill" + i);
        }

        user.setAddresses(addresses);
        user.setAllieId("allieId");
        user.setAlliePhoneNumber("alliePhoneNumber");
        user.setUpdatedTimeStamp(new DateTime());
        user.setCreatedTimeStamp(new DateTime());
        user.setNorms(norms);
        user.setPushToken("token");
        user.setMeetings(meetings);
        user.setLastName("last");
        user.setFirstName("first");
        user.setEnrolledSkills(skills);

        userResponseDTO.setAddresses(addresses);
        userResponseDTO.setAllieId("allieId");
        userResponseDTO.setAlliePhoneNumber("alliePhoneNumber");
        userResponseDTO.setUpdatedTimeStamp(new DateTime().toString());
        userResponseDTO.setCreatedTimeStamp(new DateTime().toString());
        userResponseDTO.setNorms(norms);
        userResponseDTO.setPushToken("token");
        userResponseDTO.setMeetings(meetingDTOs);
        userResponseDTO.setLastName("last");
        userResponseDTO.setFirstName("first");
        userResponseDTO.setEnrolledSkills(skills);
    }

    @Test
    public void testInsertUser(){

        given(factory.createUser(userRequestDTO)).willReturn(user);
        given(repository.insert(user)).willReturn(user);
        given(factory.createUserResponseDTO(user)).willReturn(userResponseDTO);

        assertThat(service.insertUser(userRequestDTO), equalTo(userResponseDTO));
    }

    @Test
    public void testInsertInvalidUser(){

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        User user = new User();


        given(factory.createUser(userRequestDTO)).willReturn(user);

        try {
            service.insertUser(userRequestDTO);
        } catch (Exception e) {
            assertThat(e.getClass(), equalTo(IllegalArgumentException.class));
            return;
        }
        assertThat("should not get this far", true, equalTo(false));
    }


    @Test
    public void testUpdateUser(){

        given(factory.createUser(userRequestDTO)).willReturn(user);
        given(repository.findByAllieId(user.getAllieId())).willReturn(user);
        given(repository.save(user)).willReturn(user);
        given(factory.createUserResponseDTO(user)).willReturn(userResponseDTO);

        assertThat(service.updateUser(userRequestDTO), equalTo(userResponseDTO));

    }

    @Test
    public void testUpdateUserNoAllieId() {
        userRequestDTO = new UserRequestDTO();

        given(factory.createUser(userRequestDTO)).willReturn(new User());

        try{
            service.updateUser(userRequestDTO);
        } catch (Exception e) {
            assertThat(e.getClass(), equalTo(IllegalArgumentException.class));
            return;
        }
        fail();
    }

    @Test
    public void testUpdateUserNotFound() {
        given(factory.createUser(userRequestDTO)).willReturn(user);
        given(repository.findByAllieId(user.getAllieId())).willReturn(null);

        try{
            service.updateUser(userRequestDTO);
        } catch (Exception e) {
            assertThat(e.getClass(), equalTo(MissingResourceException.class));
            return;
        }
        fail();

    }



    @Test
    public void testGetUser(){
        User user = new User();
        user.setAllieId("test");
        user.setCreatedTimeStamp(new DateTime("2010-10-10T10:10:10.101Z"));
        given(repository.findByAllieId("test")).willReturn(user);
        UserResponseDTO dto = new UserResponseDTO();
        dto.setAllieId("test");
        given(factory.createUserResponseDTO(user)).willReturn(dto);
        UserResponseDTO userResponseDTO = service.selectUser("test");
        assertThat(userResponseDTO.getAllieId(), equalTo("test"));
    }

    @Test
    public void testGetNullAllieId() {
        try {
            service.selectUser(null);
        } catch (Exception e) {
            assertThat(e.getClass(), equalTo(IllegalArgumentException.class));
            return;
        }
        assertThat("we shouldn't get this far", true, equalTo(false));
    }

    @Test
    public void testGetEmptyAllieId() {
        try {
            service.selectUser(" ");
        } catch (Exception e) {
            assertThat(e.getClass(), equalTo(IllegalArgumentException.class));
            return;
        }
        assertThat("we shouldn't get this far", true, equalTo(false));
    }

    @Test
    public void testGetNoUserFound() {
        given(repository.findByAllieId("test")).willReturn(null);
        try{
            service.selectUser("test");
        } catch (Exception e) {
            assertThat(e.getClass(), equalTo(MissingResourceException.class));
            return;
        }
        assertThat("we shouldn't get this far", true, equalTo(false));
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        User user = new User();
        user.setAllieId("something");
        users.add(user);
        given(repository.findAllAllieIds()).willReturn(users);
        List<String> ids = service.getAllUserIds("list");
        assertThat(ids.size(), equalTo(1));
        assertThat(ids.get(0), equalTo("something"));
    }

    @Test
    public void testGetAllUsersBadFormat() {
        try {
            service.getAllUserIds("blah");
        } catch (Exception e) {
            assertThat(e.getClass(), equalTo(IllegalArgumentException.class));
            return;
        }
        assertThat("we shouldn't get this far", true, equalTo(false));
    }

    @Test
    public void testGetAllUsersEmpty() {
        given(repository.findAllAllieIds()).willReturn(new ArrayList<User>());

        try{
            service.getAllUserIds("list");
        } catch (Exception e) {
            assertThat(e.getClass(), equalTo(MissingResourceException.class));
            return;
        }
        assertThat("we shouldn't get this far", true, equalTo(false));
    }

    @Test
    public void testGetAllUsersTimeout() {
        given(repository.findAllAllieIds()).willThrow(new MongoException(""));

        try{
            service.getAllUserIds("list");
        } catch (Exception e) {
            assertThat(e.getClass(), equalTo(MongoException.class));
            return;
        }
        assertThat("we shouldn't get this far", true, equalTo(false));
    }
}
