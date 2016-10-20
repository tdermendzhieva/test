package com.allie.data.dto;

/**
 * Created by andrew.larsen on 10/17/2016.
 */
public class UserLocationDTO {
    private String timestamp;
    private Location location;
    private String allieId;

    public UserLocationDTO() {
    }

    public UserLocationDTO(String timestamp, Location location, String allieId) {
        this.timestamp = timestamp;
        this.location = location;
        this.allieId = allieId;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAllieId() {
        return allieId;
    }
    public void setAllieId(String allieId) {
        this.allieId = allieId;
    }
}
