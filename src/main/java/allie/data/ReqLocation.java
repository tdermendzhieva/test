package allie.data;

import org.bson.types.BSONTimestamp;

public class ReqLocation {
	String allieId;
	ReqCoordinates location;
	int timestamp;
	
	
	public String getAllieId() {return this.allieId;}
	public void setAllieId(String allieId) {this.allieId = allieId;}
	
	public ReqCoordinates getLocation() {return this.location;}
	public void setLocation(ReqCoordinates location) {this.location = location;}
	
	public int getTimestamp() {return this.timestamp;}
	public void setTimestamp(int timestamp) {this.timestamp = timestamp;}
	
	public DBLocation toDBLocation() {
		DBLocation dbLoc = new DBLocation();
		dbLoc.setAllieId(this.allieId);
		double[] loc = {location.getLatitude(), location.getLongitude()};
		dbLoc.setLocation(loc);
		BSONTimestamp ts = new BSONTimestamp(this.timestamp, 0);
		dbLoc.setTimestamp(ts);
		return dbLoc;
	}
}
