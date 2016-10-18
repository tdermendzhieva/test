package allie.data;

import org.bson.types.BSONTimestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="LocationTelemetryRecords")
public class LocationTelemetry {
	@Id
	public String id;
	
	public String allieId;
	public double[] location;
	public BSONTimestamp timestamp;
	
	public LocationTelemetry() {}
	
	public LocationTelemetry(String allieId, double[] location, BSONTimestamp timestamp) {
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
	
	public BSONTimestamp getTimestamp() {return timestamp;}
	public void setTimestamp(BSONTimestamp timestamp) {this.timestamp = timestamp;}
	
	
	
	@Override
	public String toString() {
		return String.format(
				"LocationTelemetry[id='%s', allieId='%s', location='%s', timestamp='%s']",
				id, allieId, location, timestamp);
	}
}
