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
    public String nickname;

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

    public String getNickname() {return nickname;}
    public void setNickname(String nickname) {this.nickname = nickname;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (getAllieId() != null ? !getAllieId().equals(userDTO.getAllieId()) : userDTO.getAllieId() != null) return false;
        if (getFirstName() != null ? !getFirstName().equals(userDTO.getFirstName()) : userDTO.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(userDTO.getLastName()) : userDTO.getLastName() != null)
            return false;
        if (getPushToken() != null ? !getPushToken().equals(userDTO.getPushToken()) : userDTO.getPushToken() != null)
            return false;
        if (getAddresses() != null ? !getAddresses().equals(userDTO.getAddresses()) : userDTO.getAddresses() != null)
            return false;
        if (getMeetings() != null ? !getMeetings().equals(userDTO.getMeetings()) : userDTO.getMeetings() != null)
            return false;
        if (getNorms() != null ? !getNorms().equals(userDTO.getNorms()) : userDTO.getNorms() != null) return false;
        if (getEnrolledSkills() != null ? !getEnrolledSkills().equals(userDTO.getEnrolledSkills()) : userDTO.getEnrolledSkills() != null)
            return false;
        return getNickname() != null ? getNickname().equals(userDTO.getNickname()) : userDTO.getNickname() == null;

    }

    @Override
    public int hashCode() {
        int result = getAllieId().hashCode();
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getPushToken() != null ? getPushToken().hashCode() : 0);
        result = 31 * result + (getAddresses() != null ? getAddresses().hashCode() : 0);
        result = 31 * result + (getMeetings() != null ? getMeetings().hashCode() : 0);
        result = 31 * result + (getNorms() != null ? getNorms().hashCode() : 0);
        result = 31 * result + (getEnrolledSkills() != null ? getEnrolledSkills().hashCode() : 0);
        result = 31 * result + (getNickname() != null ? getNickname().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "allieId='" + allieId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pushToken='" + pushToken + '\'' +
                ", addresses=" + addresses +
                ", meetings=" + meetings +
                ", norms=" + norms +
                ", enrolledSkills=" + enrolledSkills +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
