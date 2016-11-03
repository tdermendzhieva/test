package com.allie.data.factory;

import com.allie.data.dto.MeetingDTO;
import com.allie.data.dto.UserRequestDTO;
import com.allie.data.dto.UserResponseDTO;
import com.allie.data.jpa.model.Address;
import com.allie.data.jpa.model.Meeting;
import com.allie.data.jpa.model.User;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
public class UserFactoryTest {
    UserRequestDTO dto;
    User user;

    UserFactory uf = new UserFactory();


    @Before
    public void setDto() {
        dto = new UserRequestDTO();
        user = new User();
        Map<String, Address> addresses = new HashMap<>();
        Map<String, String> norms = new HashMap<>();
        List<String> enrolledSkills = new ArrayList<>();
        Map<String, MeetingDTO> meetingDTOs = new HashMap<>();
        Map<String, Meeting> meetings = new HashMap<>();

        for(int i = 0; i < 10; i++) {
            Address address = new Address();
            address.setAddress1("address" + i);
            address.setCity("city" + i);
            address.setPostalCode("zip" +i);
            address.setState("state" + i);

            Meeting meeting = new Meeting();
            meeting.setTitle("Standup");
            meeting.setDateTime(new DateTime("2016-10-2" + i + "T10:10:10.111Z"));
            MeetingDTO meetingDTO = new MeetingDTO();
            meetingDTO.setTitle("Standup");
            meetingDTO.setDateTime("2016-10-2" + i + "T10:10:10.111Z");

            addresses.put("address" + i, address);
            norms.put("normkey" + i, "normval" + i);
            enrolledSkills.add("skill" + i);
            meetingDTOs.put("meeting" +i, meetingDTO);
            meetings.put("meeting" + i, meeting);
        }


        dto.setAddresses(addresses);
        user.setAddresses(addresses);
        dto.setAllieId("allieId");
        user.setAllieId("allieId");
        dto.setAlliePhoneNumber("alliePhoneNumber");
        user.setAlliePhoneNumber("alliePhoneNumber");
        dto.setPushToken("pushToken");
        user.setPushToken("pushToken");
        dto.setNorms(norms);
        user.setNorms(norms);
        dto.setLastName("last");
        user.setLastName("last");
        dto.setFirstName("first");
        user.setFirstName("first");
        dto.setEnrolledSkills(enrolledSkills);
        user.setEnrolledSkills(enrolledSkills);
        dto.setMeetings(meetingDTOs);
        user.setMeetings(meetings);
        dto.setNickname("nickname");
        user.setNickname("nickname");
        dto.setNeuraUserAccessToken("accessToken");
        user.setNeuraUserAccessToken("accessToken");

        user.setCreatedTimeStamp(new DateTime("2010-10-10T10:10:10.101Z"));
        user.setUpdatedTimeStamp(new DateTime("2010-10-10T10:10:10.101Z"));
    }

    @Test
    public void testCreateUserAllieId(){
        User user = uf.createUser(dto);

        assertThat(user.getAllieId(), equalTo("allieId"));
    }
    @Test
    public void testCreateUserAlliePhoneNumber(){
        User user = uf.createUser(dto);

        assertThat(user.getAlliePhoneNumber(), equalTo("alliePhoneNumber"));
    }
    @Test
    public void testCreateUserFirstName(){
        User user = uf.createUser(dto);

        assertThat(user.getFirstName(), equalTo("first"));
    }
    @Test
    public void testCreateUserLastName(){
        User user = uf.createUser(dto);

        assertThat(user.getLastName(), equalTo("last"));
    }
    @Test
    public void testCreateUserPushToken(){
        User user = uf.createUser(dto);

        assertThat(user.getPushToken(), equalTo("pushToken"));
    }
    @Test
    public void testCreateUserAddresses(){
        User user = uf.createUser(dto);

        assertThat(user.getAddresses().size(), equalTo(10));
        assertThat(user.getAddresses().get("address5").getState(), equalTo("state5"));
    }
    @Test
    public void testCreateUserMeetings(){
        User user = uf.createUser(dto);

        assertThat(user.getMeetings().size(), equalTo(10));
        assertThat(user.getMeetings().get("meeting8").getTitle(), equalTo("Standup"));
        assertThat(user.getMeetings().get("meeting8").getDateTime().getDayOfMonth(), equalTo(28));
    }
    @Test
    public void testCreateUserNorms(){
        User user = uf.createUser(dto);

        assertThat(user.getNorms().size(), equalTo(10));
        assertThat(user.getNorms().get("normkey3"), equalTo("normval3"));
    }
    @Test
    public void testCreateUserEnrolledSkills(){
        User user = uf.createUser(dto);

        assertThat(user.getEnrolledSkills().size(), equalTo(10));
        assertThat(user.getEnrolledSkills().get(3), equalTo("skill3"));
    }
    @Test
    public void testCreateUserCreatedTimeStamp(){
        User user = uf.createUser(dto);

        //Generated at on createUser call, verify that was within 5 secs
        boolean after = user.getCreatedTimeStamp().isAfter((new DateTime().getMillis()) - 5*1000);
        boolean before = user.getCreatedTimeStamp().isEqualNow() || user.getCreatedTimeStamp().isBeforeNow();
        assertThat(after && before, equalTo(true));
    }
    @Test
    public void testCreateUserUpdatedTimeStamp(){
        User user = uf.createUser(dto);

        //Generated at on createUser call, verify that was within 5 secs
        boolean after = user.getUpdatedTimeStamp().isAfter((new DateTime().getMillis()) - 5*1000);
        boolean before = user.getUpdatedTimeStamp().isEqualNow() || user.getUpdatedTimeStamp().isBeforeNow();
        assertThat(after && before, equalTo(true));
    }

    @Test
    public void testCreateUserNickname(){
        User user = uf.createUser(dto);

        assertThat(user.getNickname(), equalTo("nickname"));
    }

    @Test
    public void testCreateUserNeuraUserAccessToken(){
        User user = uf.createUser(dto);

        assertThat(user.getNeuraUserAccessToken(), equalTo("accessToken"));
    }


    @Test
    public void testCreateUserResponseDTOAllieId(){
        UserResponseDTO userResponseDTO = uf.createUserResponseDTO(user);

        assertThat(userResponseDTO.getAllieId(), equalTo("allieId"));
    }
    @Test
    public void testCreateUserResponseDTOAlliePhoneNumber(){
        UserResponseDTO userResponseDTO = uf.createUserResponseDTO(user);

        assertThat(userResponseDTO.getAlliePhoneNumber(), equalTo("alliePhoneNumber"));
    }
    @Test
    public void testCreateUserResponseDTOFirstName(){
        UserResponseDTO userResponseDTO = uf.createUserResponseDTO(user);

        assertThat(userResponseDTO.getFirstName(), equalTo("first"));
    }
    @Test
    public void testCreateUserResponseDTOLastName(){
        UserResponseDTO userResponseDTO = uf.createUserResponseDTO(user);

        assertThat(userResponseDTO.getLastName(), equalTo("last"));
    }
    @Test
    public void testCreateUserResponseDTOPushToken(){
        UserResponseDTO userResponseDTO = uf.createUserResponseDTO(user);

        assertThat(userResponseDTO.getPushToken(), equalTo("pushToken"));
    }
    @Test
    public void testCreateUserResponseDTOAddresses(){
        UserResponseDTO userResponseDTO = uf.createUserResponseDTO(user);

        assertThat(userResponseDTO.getAddresses().size(), equalTo(10));
        assertThat(userResponseDTO.getAddresses().get("address5").getState(), equalTo("state5"));
    }
    @Test
    public void testCreateUserResponseDTOMeetings(){
        UserResponseDTO userResponseDTO = uf.createUserResponseDTO(user);

        assertThat(userResponseDTO.getMeetings().size(), equalTo(10));
        assertThat(userResponseDTO.getMeetings().get("meeting8").getTitle(), equalTo("Standup"));
        assertThat(new DateTime(userResponseDTO.getMeetings().get("meeting8").getDateTime()).getDayOfMonth(), equalTo(28));
    }
    @Test
    public void testCreateUserResponseDTONorms(){
        UserResponseDTO userResponseDTO = uf.createUserResponseDTO(user);

        assertThat(userResponseDTO.getNorms().size(), equalTo(10));
        assertThat(userResponseDTO.getNorms().get("normkey3"), equalTo("normval3"));
    }
    @Test
    public void testCreateUserResponseDTOEnrolledSkills(){
        UserResponseDTO userResponseDTO = uf.createUserResponseDTO(user);

        assertThat(userResponseDTO.getEnrolledSkills().size(), equalTo(10));
        assertThat(userResponseDTO.getEnrolledSkills().get(3), equalTo("skill3"));
    }
    @Test
    public void testCreateUserResponseDTOCreatedTimeStamp(){
        UserResponseDTO userResponseDTO = uf.createUserResponseDTO(user);
        
        assertThat(new DateTime(userResponseDTO.getCreatedTimeStamp()).isEqual(new DateTime("2010-10-10T10:10:10.101Z").getMillis()),
                equalTo(true));
    }
    @Test
    public void testCreateUserResponseDTOUpdatedTimeStamp(){
        UserResponseDTO userResponseDTO = uf.createUserResponseDTO(user);

        assertThat(new DateTime(userResponseDTO.getCreatedTimeStamp()).isEqual(new DateTime("2010-10-10T10:10:10.101Z").getMillis()),
                equalTo(true));
    }

    @Test
    public void testCreateUserResponseDTONickname(){
        UserResponseDTO userResponseDTO = uf.createUserResponseDTO(user);

        assertThat(userResponseDTO.getNickname(), equalTo("nickname"));
    }

    @Test
    public void testCreateUserResponseDTONeuraUserAccessToken(){
        UserResponseDTO userResponseDTO = uf.createUserResponseDTO(user);

        assertThat(userResponseDTO.getNeuraUserAccessToken(), equalTo("accessToken"));
    }
}
