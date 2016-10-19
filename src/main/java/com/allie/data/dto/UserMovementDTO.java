package com.allie.data.dto;

/**
 * Created by jacob.headlee on 10/19/2016.
 */
public class UserMovementDTO {
    public String timestamp;
    public String allieId;

    public void setAllieId(String allieId) {this.allieId = allieId;}
    public String getAllieId() {return this.allieId;}

    public void setTimestamp(String timestamp) {this.timestamp = timestamp;}
    public String getTimestamp() {return timestamp;}
}
