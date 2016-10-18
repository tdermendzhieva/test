package com.allie.data.jpa.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.joda.time.DateTime;

import java.util.Date;

@Document(collection="LocationTelemetryRecords")
public class LocationTelemetry {
	@Id
	public String id;
	
	public String allieId;
	public double[] location;
	public DateTime timestamp;
	
	public LocationTelemetry() {}
	
	public LocationTelemetry(String allieId, double[] location, DateTime timestamp) {
		this.allieId = allieId;
		this.location = location;
		this.timestamp = timestamp;
	}
	
	public String getId() {return id;}
	public void setId(String id) {this.id = id;}
	
	public String getAllieId() {return allieId;}
	public void setAllieId(String allieId) {this.allieId = allieId;}
	
	public double[] getLocation() {return location;}
	public void setLocation(double[] location) {this.location = location;}
	
	public DateTime getTimestamp() {return timestamp;}
	public void setTimestamp(DateTime timestamp) {this.timestamp = timestamp;}
	
	
	
	@Override
	public String toString() {
		return String.format(
				"LocationTelemetry[id='%s', allieId='%s', location='%s', timestamp='%s']",
				id, allieId, location, timestamp);
	}
}
