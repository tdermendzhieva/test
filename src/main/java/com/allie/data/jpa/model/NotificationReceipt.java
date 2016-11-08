package com.allie.data.jpa.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by jacob.headlee on 11/1/2016.
 */
@Document(collection = "NotificationReceipts")
public class NotificationReceipt {
    @Id
    private String dbId;
    private String allieId;
    private String trackingNumber;
    private String messageClass;
    private String type;
    private String userSelectedOption;
    private String userEnteredOption;
    private DateTime timestamp;

    @Override
    public String toString() {
        return "NotificationReceipt{" +
                "dbId='" + dbId + '\'' +
                ", allieId='" + allieId + '\'' +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", messageClass='" + messageClass + '\'' +
                ", type='" + type + '\'' +
                ", userSelectedOption='" + userSelectedOption + '\'' +
                ", userEnteredOption='" + userEnteredOption + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public String getDbId() {return this.dbId;}
    public void setDbId(String dbId) {this.dbId = dbId;}

    public String getAllieId() {return this.allieId;}
    public void setAllieId(String allieId) {this.allieId = allieId;}

    public String getTrackingNumber() {return this.trackingNumber;}
    public void setTrackingNumber(String trackingNumber) {this.trackingNumber = trackingNumber;}

    public String getMessageClass() {return this.messageClass;}
    public void setMessageClass(String messageClass) {this.messageClass = messageClass;}

    public String getType() {return this.type;}
    public void setType(String type) {this.type = type;}

    public String getUserSelectedOption() {return this.userSelectedOption;}
    public void setUserSelectedOption(String userSelecteOptions) {this.userSelectedOption = userSelecteOptions;}

    public DateTime getTimestamp() {return this.timestamp;}
    public void setTimestamp(DateTime timestamp) {this.timestamp = timestamp;}

    public String getUserEnteredOption() {return this.userEnteredOption;}
    public void setUserEnteredOption(String userEnteredOption) {this.userEnteredOption = userEnteredOption;}

}
