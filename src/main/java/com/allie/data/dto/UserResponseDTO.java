package com.allie.data.dto;

import java.util.List;
import java.util.Map;

import com.allie.data.jpa.model.Address;

/**
 * Created by jacob.headlee on 10/24/2016.
 */
public class UserResponseDTO {
    public UserResponseDTO() {}

    public String dbId;

    public String allieId;

    public String alliePhoneNumber;
    public String firstName;
    public String lastName;
    public String pushToken;
    public Map<String, Address> addresses;
    public Map<String, MeetingDTO> meetings;
    public Map<String, String> norms;
    public List<String> enrolledSkills;
    public String createdTimeStamp;
    public String updatedTimeStamp;
    public String nickname;

    public String getDbId() {return this.dbId;}
    public void setDbId(String dbId) {this.dbId = dbId;}

    public String getAllieId() {return this.allieId;}
    public void setAllieId(String allieId) {this.allieId = allieId;}

    public String getAlliePhoneNumber() {return alliePhoneNumber;}
	public void setAlliePhoneNumber(String alliePhoneNumber) {this.alliePhoneNumber = alliePhoneNumber;}
	
	public String getFirstName() {return this.firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return this.lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getPushToken() {return this.pushToken;}
    public void setPushToken(String pushToken) {this.pushToken = pushToken;}

    public Map<String, Address> getAddresses() {return this.addresses;}
    public void setAddresses(Map<String, Address> addresses) {this.addresses = addresses;}

    public Map<String, MeetingDTO> getMeetings() {return this.meetings;}
    public void setMeetings(Map<String, MeetingDTO> meetings) {this.meetings = meetings;}

    public Map<String, String> getNorms() {return this.norms;}
    public void setNorms(Map<String, String> norms) {this.norms = norms;}

    public List<String> getEnrolledSkills() {return this.enrolledSkills;}
    public void setEnrolledSkills(List<String> enrolledSkills) {this.enrolledSkills = enrolledSkills;}

    public String getCreatedTimeStamp() {return this.createdTimeStamp;}
    public void setCreatedTimeStamp(String createdTimeStamp) {this.createdTimeStamp = createdTimeStamp;}

    public String getUpdatedTimeStamp() {return this.updatedTimeStamp;}
    public void setUpdatedTimeStamp(String updatedTimeStamp) {this.updatedTimeStamp = updatedTimeStamp;}

    public String getNickname() {return this.nickname;}
    public void setNickname(String nickname) {this.nickname = nickname;}
}
