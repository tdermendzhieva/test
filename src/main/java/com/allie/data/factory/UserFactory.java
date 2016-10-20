package com.allie.data.factory;

import com.allie.data.dto.MeetingDTO;
import com.allie.data.dto.UserDTO;
import com.allie.data.jpa.model.Meeting;
import com.allie.data.jpa.model.User;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
public class UserFactory {
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setAllieId(userDTO.getAllieId());
        user.setAddresses(userDTO.getAddresses());
        user.setEnrolledSkills(userDTO.getEnrolledSkills());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        Map<String, Meeting> meetings = new HashMap<String, Meeting>();
        for (Map.Entry<String, MeetingDTO> entry : userDTO.getMeetings().entrySet()) {
            meetings.put(entry.getKey(), this.createMeeting(entry.getValue()));
        }
        user.setMeetings(meetings);
        user.setNorms(userDTO.getNorms());
        user.setPushToken(userDTO.getPushToken());
        //set timestamps
        user.setCreatedTimeStamp(new DateTime());
        user.setUpdatedTimeStamp(new DateTime());

        return user;
    }

    private Meeting createMeeting(MeetingDTO meetingDTO) {
        Meeting meeting = new Meeting();
        meeting.setDateTime(new DateTime(meetingDTO.getDateTime()));
        return meeting;
    }
}
