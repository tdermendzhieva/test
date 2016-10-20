package com.allie.data.dto;

import com.allie.data.jpa.model.Address;

import java.util.List;
import java.util.Map;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
public class UserDTO {
    public UserDTO(){}

    public String allieId;
    public String firstName;
    public String lastName;
    public String pushToken;
    public Map<String, Address> addresses;
    public Map<String, MeetingDTO> meetings;
    public Map<String, String> norms;
    public List<String> enrolledSkills;

    public String getAllieId() {return allieId;}
    public void setAllieId(String allieId) {this.allieId = allieId;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getPushToken() {return pushToken;}
    public void setPushToken(String pushToken) {this.pushToken = pushToken;}

    public Map<String, Address> getAddresses() {return addresses;}
    public void setAddresses(Map<String, Address> addresses) {this.addresses = addresses;}

    public Map<String, MeetingDTO> getMeetings() {return meetings;}
    public void setMeetings(Map<String, MeetingDTO> meetings) {this.meetings = meetings;}

    public Map<String, String> getNorms() {return norms;}
    public void setNorms(Map<String, String> norms) {this.norms = norms;}

    public List<String> getEnrolledSkills() {return enrolledSkills;}
    public void setEnrolledSkills(List<String> enrolledSkills) {this.enrolledSkills = enrolledSkills;}

}
