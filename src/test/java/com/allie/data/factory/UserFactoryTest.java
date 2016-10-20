package com.allie.data.factory;

import com.allie.data.dto.MeetingDTO;
import com.allie.data.dto.UserDTO;
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
    UserDTO dto;

    UserFactory uf = new UserFactory();


    @Before
    public void setDto() {
        dto = new UserDTO();
        Map<String, Address> addresses = new HashMap<>();
        Map<String, String> norms = new HashMap<>();
        List<String> enrolledSkills = new ArrayList<>();
        Map<String, MeetingDTO> meetings = new HashMap<>();

        for(int i = 0; i < 10; i++) {
            Address address = new Address();
            address.setAddress1("address" + i);
            address.setCity("city" + i);
            address.setPostalCode("zip" +i);
            address.setState("state" + i);

            MeetingDTO meetingDTO = new MeetingDTO();
            meetingDTO.setDateTime("2016-10-2" + i + "T10:10:10.111Z");

            addresses.put("address" + i, address);
            norms.put("normkey" + i, "normval" + i);
            enrolledSkills.add("skill" + i);
            meetings.put("meeting" +i, meetingDTO);
        }


        dto.setAddresses(addresses);
        dto.setAllieId("allieId");
        dto.setPushToken("pushToken");
        dto.setNorms(norms);
        dto.setLastName("last");
        dto.setFirstName("first");
        dto.setEnrolledSkills(enrolledSkills);
        dto.setMeetings(meetings);
    }

    @Test
    public void testCreateAllieId(){
        User user = uf.createUser(dto);

        assertThat(user.getAllieId(), equalTo("allieId"));
    }
    @Test
    public void testCreateFirstName(){
        User user = uf.createUser(dto);

        assertThat(user.getFirstName(), equalTo("first"));
    }
    @Test
    public void testCreateLastName(){
        User user = uf.createUser(dto);

        assertThat(user.getLastName(), equalTo("last"));
    }
    @Test
    public void testCreatePushToken(){
        User user = uf.createUser(dto);

        assertThat(user.getPushToken(), equalTo("pushToken"));
    }
    @Test
    public void testCreateAddresses(){
        User user = uf.createUser(dto);

        assertThat(user.getAddresses().size(), equalTo(10));
        assertThat(user.getAddresses().get("address5").getState(), equalTo("state5"));
    }
    @Test
    public void testCreateMeetings(){
        User user = uf.createUser(dto);

        assertThat(user.getMeetings().size(), equalTo(10));
        assertThat(user.getMeetings().get("meeting8").getDateTime().getDayOfMonth(), equalTo(28));
    }
    @Test
    public void testCreateNorms(){
        User user = uf.createUser(dto);

        assertThat(user.getNorms().size(), equalTo(10));
        assertThat(user.getNorms().get("normkey3"), equalTo("normval3"));
    }
    @Test
    public void testCreateEnrolledSkills(){
        User user = uf.createUser(dto);

        assertThat(user.getEnrolledSkills().size(), equalTo(10));
        assertThat(user.getEnrolledSkills().get(3), equalTo("skill3"));
    }
    @Test
    public void testCreateCreatedTimeStamp(){
        User user = uf.createUser(dto);

        //Generated at on createUser call, verify that was within 5 secs
        boolean after = user.getCreatedTimeStamp().isAfter((new DateTime().getMillis()) - 5*1000);
        boolean before = user.getCreatedTimeStamp().isEqualNow() || user.getCreatedTimeStamp().isBeforeNow();
        assertThat(after && before, equalTo(true));
    }
    @Test
    public void testCreateUpdatedTimeStamp(){
        User user = uf.createUser(dto);

        //Generated at on createUser call, verify that was within 5 secs
        boolean after = user.getUpdatedTimeStamp().isAfter((new DateTime().getMillis()) - 5*1000);
        boolean before = user.getUpdatedTimeStamp().isEqualNow() || user.getUpdatedTimeStamp().isBeforeNow();
        assertThat(after && before, equalTo(true));
    }
}
