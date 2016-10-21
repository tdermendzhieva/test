package com.allie.data.dto;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMovementDTO that = (UserMovementDTO) o;

        if (getTimestamp() != null ? !getTimestamp().equals(that.getTimestamp()) : that.getTimestamp() != null)
            return false;
        if (getHasMoved() != null ? !getHasMoved().equals(that.getHasMoved()) : that.getHasMoved() != null)
            return false;
        return getAllieId() != null ? getAllieId().equals(that.getAllieId()) : that.getAllieId() == null;

    }

    @Override
    public int hashCode() {
        int result = getTimestamp() != null ? getTimestamp().hashCode() : 0;
        result = 31 * result + (getHasMoved() != null ? getHasMoved().hashCode() : 0);
        result = 31 * result + (getAllieId() != null ? getAllieId().hashCode() : 0);
        return result;
    }
}
