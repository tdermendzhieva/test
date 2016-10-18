package allie.data;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.BSONTimestamp;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class LocationsEndpointTest {
	@Test
	public void testPostLocationTelemetryList() {
		LocationsEndpoint le = new LocationsEndpoint();
		List<LocationTelemetry> inputList = new ArrayList<LocationTelemetry>();
		double[] location1 = new double[2];
		location1[0] = 0;
		location1[1] = 0;
		inputList.add(new LocationTelemetry("test1", location1, new BSONTimestamp(123456789,0)));
		double[] location2 = new double[2];
		location2[0] = 45;
		location2[1] = -45;
		inputList.add(new LocationTelemetry("test2", location2, new BSONTimestamp(987654321, 0)));
		double[] location3 = new double[2];
		location3[0] = -90;
		location3[1] = 90;
		inputList.add(new LocationTelemetry("test3", location3, new BSONTimestamp(987654321, 10)));
		
		List<LocationTelemetry> outputList = le.postLocationTelemetryList(inputList);
		Assert.notEmpty(outputList);
	}
}
