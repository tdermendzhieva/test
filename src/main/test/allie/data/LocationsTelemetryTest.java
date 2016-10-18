package allie.data;

import org.bson.types.BSONTimestamp;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

public class LocationsTelemetryTest {
	LocationTelemetry lt;
	
	@Before
	public void init() {
		String allieId = "test";
		double[] location = {1.23456789, -9.87654321};
		BSONTimestamp timestamp = new BSONTimestamp(998772211, 10);
		lt = new LocationTelemetry(allieId, location, timestamp);
	}
	
	@Test
	public void testLocationsTelemetryConstructor() {		
		double[] test = {1.23456789, -9.87654321};
		
		Assert.state(lt.allieId.equals("test") && lt.location == test && lt.timestamp.equals(new BSONTimestamp(998772211, 10)),  "Constructor must set values for allieId, location, and timestamp");
	}
	
	public void testGetAllieId() {
		Assert.state(lt.getAllieId().equals("test"));
	}
	
	public void testGetLocation() {
		double[] test = {1.23456789, -9.87654321};
		
		Assert.state(lt.getLocation() == test);
	}
	
	public void testGetTimeStamp() {
		Assert.state(lt.getTimestamp().equals(new BSONTimestamp(998772211, 10)));
	}
	
}
