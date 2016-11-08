package com.allie.data.jpa.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
@Document(collection = "Users")
public class User {
    public User() {}

    @Id
    public String dbId;

    //Indexed so we can't users with duplicate ids
   // @Indexed(unique = true)
    public String allieId;

    public String alliePhoneNumber;
    public String firstName;
    public String lastName;
    public String pushToken;
    public Map<String, Address> addresses;
    public Map<String, Meeting> meetings;
    public Map<String, String> norms;
    public List<String> enrolledSkills;
    public DateTime createdTimeStamp;
    public DateTime updatedTimeStamp;
    public String nickname;
    public String neuraUserAccessToken;

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

    public Map<String, Meeting> getMeetings() {return this.meetings;}
    public void setMeetings(Map<String, Meeting> meetings) {this.meetings = meetings;}

    public Map<String, String> getNorms() {return this.norms;}
    public void setNorms(Map<String, String> norms) {this.norms = norms;}

    public List<String> getEnrolledSkills() {return this.enrolledSkills;}
    public void setEnrolledSkills(List<String> enrolledSkills) {this.enrolledSkills = enrolledSkills;}

    public DateTime getCreatedTimeStamp() {return this.createdTimeStamp;}
    public void setCreatedTimeStamp(DateTime createdTimeStamp) {this.createdTimeStamp = createdTimeStamp;}

    public DateTime getUpdatedTimeStamp() {return this.updatedTimeStamp;}
    public void setUpdatedTimeStamp(DateTime updatedTimeStamp) {this.updatedTimeStamp = updatedTimeStamp;}

    public String getNickname() {return this.nickname;}
    public void setNickname(String nickname) {this.nickname = nickname;}

    public String getNeuraUserAccessToken() {return this.neuraUserAccessToken;}
    public void setNeuraUserAccessToken(String neuraUserAccessToken) {this.neuraUserAccessToken = neuraUserAccessToken;}
}
