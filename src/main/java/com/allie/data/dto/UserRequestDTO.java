package com.allie.data.dto;

import com.allie.data.jpa.model.Address;

import java.util.List;
import java.util.Map;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
public class UserRequestDTO {
    public UserRequestDTO(){}

    public String allieId;
    public String alliePhoneNumber;
    public String firstName;
    public String lastName;
    public String pushToken;
    public Map<String, Address> addresses;
    public Map<String, MeetingDTO> meetings;
    public Map<String, String> norms;
    public List<String> enrolledSkills;
    public String nickname;
	public String neuraUserAccessToken;

    public String getAllieId() {return allieId;}
    public void setAllieId(String allieId) {this.allieId = allieId;}

    public String getAlliePhoneNumber() {return alliePhoneNumber;}
	public void setAlliePhoneNumber(String alliePhoneNumber) {this.alliePhoneNumber = alliePhoneNumber;}
	
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

	public String getNeuraUserAccessToken() {return neuraUserAccessToken;}
	public void setNeuraUserAccessToken(String neuraUserAccessToken) {this.neuraUserAccessToken = neuraUserAccessToken;}

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRequestDTO other = (UserRequestDTO) obj;
		if (addresses == null) {
			if (other.addresses != null)
				return false;
		} else if (!addresses.equals(other.addresses))
			return false;
		if (neuraUserAccessToken == null) {
			if (other.neuraUserAccessToken != null)
				return false;
		} else if (!neuraUserAccessToken.equals(other.neuraUserAccessToken))
			return false;
		if (allieId == null) {
			if (other.allieId != null)
				return false;
		} else if (!allieId.equals(other.allieId))
			return false;
		if (alliePhoneNumber == null) {
			if (other.alliePhoneNumber != null)
				return false;
		} else if (!alliePhoneNumber.equals(other.alliePhoneNumber))
			return false;
		if (enrolledSkills == null) {
			if (other.enrolledSkills != null)
				return false;
		} else if (!enrolledSkills.equals(other.enrolledSkills))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (meetings == null) {
			if (other.meetings != null)
				return false;
		} else if (!meetings.equals(other.meetings))
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (norms == null) {
			if (other.norms != null)
				return false;
		} else if (!norms.equals(other.norms))
			return false;
		if (pushToken == null) {
			if (other.pushToken != null)
				return false;
		} else if (!pushToken.equals(other.pushToken))
			return false;
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addresses == null) ? 0 : addresses.hashCode());
		result = prime * result + ((allieId == null) ? 0 : allieId.hashCode());
		result = prime * result + ((alliePhoneNumber == null) ? 0 : alliePhoneNumber.hashCode());
		result = prime * result + ((enrolledSkills == null) ? 0 : enrolledSkills.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((meetings == null) ? 0 : meetings.hashCode());
		result = prime * result + ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result + ((norms == null) ? 0 : norms.hashCode());
		result = prime * result + ((pushToken == null) ? 0 : pushToken.hashCode());
		result = prime * result + ((neuraUserAccessToken == null) ? 0 : neuraUserAccessToken.hashCode());
		return result;
	}

    @Override
	public String toString() {
		return "UserRequestDTO [allieId=" + allieId + ", alliePhoneNumber=" + alliePhoneNumber + ", firstName="
				+ firstName + ", lastName=" + lastName + ", pushToken=" + pushToken + ", addresses=" + addresses
				+ ", meetings=" + meetings + ", norms=" + norms + ", enrolledSkills=" + enrolledSkills + ", nickname="
				+ nickname + "]";
	}
}
