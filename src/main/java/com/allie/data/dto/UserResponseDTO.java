package com.allie.data.dto;

import java.util.List;
import java.util.Map;

import com.allie.data.jpa.model.Address;

/**
 * Created by jacob.headlee on 10/24/2016.
 */
public class UserResponseDTO {

    private String dbId;
    private String allieId;

    private String alliePhoneNumber;
    private String firstName;
    private String lastName;
    private String pushToken;
    private Map<String, Address> addresses;
    private Map<String, MeetingDTO> meetings;
    private Map<String, String> norms;
    private List<String> enrolledSkills;
    private String createdTimeStamp;
    private String updatedTimeStamp;
    private String nickname;
    private String neuraUserAccessToken;

    @Override
    public String toString() {
        return "UserResponseDTO{" +
                "dbId='" + dbId + '\'' +
                ", allieId='" + allieId + '\'' +
                ", alliePhoneNumber='" + alliePhoneNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pushToken='" + pushToken + '\'' +
                ", addresses=" + addresses +
                ", meetings=" + meetings +
                ", norms=" + norms +
                ", enrolledSkills=" + enrolledSkills +
                ", createdTimeStamp='" + createdTimeStamp + '\'' +
                ", updatedTimeStamp='" + updatedTimeStamp + '\'' +
                ", nickname='" + nickname + '\'' +
                ", neuraUserAccessToken='" + neuraUserAccessToken + '\'' +
                '}';
    }

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

    public String getNeuraUserAccessToken() {return this.neuraUserAccessToken;}
    public void setNeuraUserAccessToken(String neuraUserAccessToken) {this.neuraUserAccessToken = neuraUserAccessToken;}
}
