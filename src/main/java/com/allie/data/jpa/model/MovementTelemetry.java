package com.allie.data.jpa.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by jacob.headlee on 10/19/2016.
 */
@Document(collection = "MovementTelemetryRecords")
public class MovementTelemetry {

    @Id
    public String dbId;

    private DateTime timestamp;
    private Boolean hasMoved;
    private String allieId;

    public MovementTelemetry() {}
    public MovementTelemetry(DateTime timestamp, Boolean hasMoved, String allieId) {
        this.timestamp = timestamp;
        this.hasMoved = hasMoved;
        this.allieId = allieId;
    }

    @Override
    public String toString() {
        return "MovementTelemetry{" +
                "dbId='" + dbId + '\'' +
                ", timestamp=" + timestamp +
                ", hasMoved=" + hasMoved +
                ", allieId='" + allieId + '\'' +
                '}';
    }

    public String getDbId() {return this.dbId;}
    public void setDbId(String dbId) {this.dbId = dbId;}

    public void setAllieId(String allieId) {this.allieId = allieId;}
    public String getAllieId() {return this.allieId;}

    public void setHasMoved(Boolean hasMoved) {this.hasMoved = hasMoved;}
    public Boolean getHasMoved() {return this.hasMoved;}

    public void setTimestamp(DateTime timestamp) {this.timestamp = timestamp;}
    public DateTime getTimestamp() {return timestamp;}

}
