package com.allie.data.dto;

import org.apache.catalina.User;

/**
 * Created by jacob.headlee on 10/19/2016.
 */
public class UserMovementDTO {
    private String timestamp;
    private Boolean hasMoved;
    private String allieId;

    public UserMovementDTO() {}
    public UserMovementDTO(String timestamp, Boolean hasMoved, String allieId) {
        this.timestamp = timestamp;
        this.hasMoved = hasMoved;
        this.allieId = allieId;
    }

    public void setAllieId(String allieId) {this.allieId = allieId;}
    public String getAllieId() {return this.allieId;}

    public void setHasMoved(Boolean hasMoved) {this.hasMoved = hasMoved;}
    public Boolean getHasMoved() {return this.hasMoved;}

    public void setTimestamp(String timestamp) {this.timestamp = timestamp;}
    public String getTimestamp() {return timestamp;}
}
