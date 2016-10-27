package com.allie.data.factory;

import com.allie.data.dto.MeetingDTO;
import com.allie.data.dto.UserRequestDTO;
import com.allie.data.dto.UserResponseDTO;
import com.allie.data.jpa.model.Meeting;
import com.allie.data.jpa.model.User;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
@Component
public class UserFactory {
    public User createUser(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setAllieId(userRequestDTO.getAllieId());
        user.setAddresses(userRequestDTO.getAddresses());
        user.setEnrolledSkills(userRequestDTO.getEnrolledSkills());
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setNickname(userRequestDTO.getNickname());
        Map<String, Meeting> meetings = new HashMap<String, Meeting>();
        if(userRequestDTO.getMeetings() != null) {
            for (Map.Entry<String, MeetingDTO> entry : userRequestDTO.getMeetings().entrySet()) {
                meetings.put(entry.getKey(), this.createMeeting(entry.getValue()));
            }
        }
        user.setMeetings(meetings);
        user.setNorms(userRequestDTO.getNorms());
        user.setPushToken(userRequestDTO.getPushToken());
        //set timestamps
        user.setCreatedTimeStamp(new DateTime());
        user.setUpdatedTimeStamp(new DateTime());

        return user;
    }

    private Meeting createMeeting(MeetingDTO meetingDTO) {
        Meeting meeting = new Meeting();
        meeting.setTitle(meetingDTO.getTitle());
        meeting.setDateTime(new DateTime(meetingDTO.getDateTime()));
        return meeting;
    }

    private MeetingDTO createMeetingDTO(Meeting meeting) {
        MeetingDTO meetingDTO = new MeetingDTO();
        meetingDTO.setTitle(meeting.getTitle());
        meetingDTO.setDateTime(meeting.getDateTime().toString());
        return meetingDTO;
    }

    public UserResponseDTO createUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setAllieId(user.getAllieId());
        userResponseDTO.setDbId(user.getDbId());
        userResponseDTO.setPushToken(user.getPushToken());

        userResponseDTO.setNickname(user.getNickname());
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());

        userResponseDTO.setCreatedTimeStamp(user.getCreatedTimeStamp().toString());
        userResponseDTO.setUpdatedTimeStamp(user.getUpdatedTimeStamp().toString());

        userResponseDTO.setAddresses(user.getAddresses());
        userResponseDTO.setEnrolledSkills(user.getEnrolledSkills());
        userResponseDTO.setNorms(user.getNorms());
        Map<String, MeetingDTO> meetings = new HashMap<>();

        if(user.getMeetings() != null) {
            for(Map.Entry<String, Meeting> entry : user.getMeetings().entrySet()) {
                meetings.put(entry.getKey(), createMeetingDTO(entry.getValue()));
            }
        }
        userResponseDTO.setMeetings(meetings);

        return userResponseDTO;
    }
}
