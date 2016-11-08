package com.allie.data.jpa.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.joda.time.DateTime;

import java.util.Arrays;

@Document(collection="LocationTelemetryRecords")
public class LocationTelemetry {

	@Id
	private String dbId;
	
	private String allieId;
	private double[] location;
	private DateTime timestamp;
	
	public LocationTelemetry() {}
	
	public LocationTelemetry(String allieId, double[] location, DateTime timestamp) {
		this.allieId = allieId;
		this.location = location;
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "LocationTelemetry{" +
				"dbId='" + dbId + '\'' +
				", allieId='" + allieId + '\'' +
				", location=" + Arrays.toString(location) +
				", timestamp=" + timestamp +
				'}';
	}

	public String getDbId() {return dbId;}
	public void setDbId(String DBId) {this.dbId = DBId;}
	
	public String getAllieId() {return allieId;}
	public void setAllieId(String allieId) {this.allieId = allieId;}
	
	public double[] getLocation() {return location;}
	public void setLocation(double[] location) {this.location = location;}
	
	public DateTime getTimestamp() {return timestamp;}
	public void setTimestamp(DateTime timestamp) {this.timestamp = timestamp;}

}
