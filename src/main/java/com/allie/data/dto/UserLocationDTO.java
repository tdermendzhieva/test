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

    @Override
    public String toString() {
        return "UserLocationDTO{" +
                "timestamp='" + timestamp + '\'' +
                ", location=" + location +
                ", allieId='" + allieId + '\'' +
                '}';
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserLocationDTO that = (UserLocationDTO) o;

        if (getTimestamp() != null ? !getTimestamp().equals(that.getTimestamp()) : that.getTimestamp() != null)
            return false;
        if (getLocation() != null ? !getLocation().equals(that.getLocation()) : that.getLocation() != null)
            return false;
        return getAllieId() != null ? getAllieId().equals(that.getAllieId()) : that.getAllieId() == null;

    }

    @Override
    public int hashCode() {
        int result = getTimestamp() != null ? getTimestamp().hashCode() : 0;
        result = 31 * result + (getLocation() != null ? getLocation().hashCode() : 0);
        result = 31 * result + (getAllieId() != null ? getAllieId().hashCode() : 0);
        return result;
    }
}
