package com.allie.data.dto;

/**
 * Created by jacob.headlee on 11/1/2016.
 */
public class NotificationReceiptDTO {
    private String trackingNumber;
    private String allieId;
    private String messageClass;
    private String timestamp;
    private String type;
    private String userSelectedOption;

    @Override
    public String toString() {
        return "NotificationReceiptDTO{" +
                "trackingNumber='" + trackingNumber + '\'' +
                ", allieId='" + allieId + '\'' +
                ", messageClass='" + messageClass + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", type='" + type + '\'' +
                ", userSelectedOption='" + userSelectedOption + '\'' +
                '}';
    }

    public void setTrackingNumber(String trackingNumber) {this.trackingNumber = trackingNumber;}
    public String getTrackingNumber() {return this.trackingNumber;}

    public void setAllieId(String allieId) {this.allieId = allieId;}
    public String getAllieId() {return this.allieId;}

    public void setMessageClass(String messageClass) {this.messageClass = messageClass;}
    public String getMessageClass() {return this.messageClass;}

    public void setTimestamp(String timestamp) {this.timestamp = timestamp;}
    public String getTimestamp() {return this.timestamp;}

    public void setType(String type) {this.type = type;}
    public String getType() {return this.type;}

    public void setUserSelectedOption(String userSelectedOption) {this.userSelectedOption = userSelectedOption;}
    public String getUserSelectedOption() {return this.userSelectedOption;}

}
