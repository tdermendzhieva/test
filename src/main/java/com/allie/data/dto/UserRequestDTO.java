package com.allie.data.dto;

import com.allie.data.jpa.model.Address;

import java.util.List;
import java.util.Map;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
public class UserRequestDTO {

    private String allieId;
	private String alliePhoneNumber;
	private String firstName;
	private String lastName;
	private String pushToken;
	private Map<String, Address> addresses;
	private Map<String, MeetingDTO> meetings;
	private Map<String, String> norms;
	private List<String> enrolledSkills;
	private String nickname;
	private String neuraUserAccessToken;

	@Override
	public String toString() {
		return "UserRequestDTO{" +
				"allieId='" + allieId + '\'' +
				", alliePhoneNumber='" + alliePhoneNumber + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", pushToken='" + pushToken + '\'' +
				", addresses=" + addresses +
				", meetings=" + meetings +
				", norms=" + norms +
				", enrolledSkills=" + enrolledSkills +
				", nickname='" + nickname + '\'' +
				", neuraUserAccessToken='" + neuraUserAccessToken + '\'' +
				'}';
	}

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UserRequestDTO that = (UserRequestDTO) o;

		if (getAllieId() != null ? !getAllieId().equals(that.getAllieId()) : that.getAllieId() != null) return false;
		if (getAlliePhoneNumber() != null ? !getAlliePhoneNumber().equals(that.getAlliePhoneNumber()) : that.getAlliePhoneNumber() != null)
			return false;
		if (getFirstName() != null ? !getFirstName().equals(that.getFirstName()) : that.getFirstName() != null)
			return false;
		if (getLastName() != null ? !getLastName().equals(that.getLastName()) : that.getLastName() != null)
			return false;
		if (getPushToken() != null ? !getPushToken().equals(that.getPushToken()) : that.getPushToken() != null)
			return false;
		if (getAddresses() != null ? !getAddresses().equals(that.getAddresses()) : that.getAddresses() != null)
			return false;
		if (getMeetings() != null ? !getMeetings().equals(that.getMeetings()) : that.getMeetings() != null)
			return false;
		if (getNorms() != null ? !getNorms().equals(that.getNorms()) : that.getNorms() != null) return false;
		if (getEnrolledSkills() != null ? !getEnrolledSkills().equals(that.getEnrolledSkills()) : that.getEnrolledSkills() != null)
			return false;
		if (getNickname() != null ? !getNickname().equals(that.getNickname()) : that.getNickname() != null)
			return false;
		return getNeuraUserAccessToken() != null ? getNeuraUserAccessToken().equals(that.getNeuraUserAccessToken()) : that.getNeuraUserAccessToken() == null;

	}

	@Override
	public int hashCode() {
		int result = getAllieId() != null ? getAllieId().hashCode() : 0;
		result = 31 * result + (getAlliePhoneNumber() != null ? getAlliePhoneNumber().hashCode() : 0);
		result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
		result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
		result = 31 * result + (getPushToken() != null ? getPushToken().hashCode() : 0);
		result = 31 * result + (getAddresses() != null ? getAddresses().hashCode() : 0);
		result = 31 * result + (getMeetings() != null ? getMeetings().hashCode() : 0);
		result = 31 * result + (getNorms() != null ? getNorms().hashCode() : 0);
		result = 31 * result + (getEnrolledSkills() != null ? getEnrolledSkills().hashCode() : 0);
		result = 31 * result + (getNickname() != null ? getNickname().hashCode() : 0);
		result = 31 * result + (getNeuraUserAccessToken() != null ? getNeuraUserAccessToken().hashCode() : 0);
		return result;
	}
}
