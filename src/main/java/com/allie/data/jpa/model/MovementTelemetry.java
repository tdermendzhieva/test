package com.allie.data.jpa.model;

import org.joda.time.DateTime;

/**
 * Created by jacob.headlee on 10/19/2016.
 */
public class MovementTelemetry {
    DateTime timestamp;
    String allieId;

    public void setAllieId(String allieId) {this.allieId = allieId;}
    public String getAllieId() {return this.allieId;}

    public void setTimestamp(DateTime timestamp) {this.timestamp = timestamp;}
    public DateTime getTimestamp() {return timestamp;}
}
