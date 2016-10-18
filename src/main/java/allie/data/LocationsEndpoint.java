package allie.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="allie-data/v1")
public class LocationsEndpoint {
	
	@Autowired
	public LocationTelemetryRepository repository;
	
	@RequestMapping(value="/locations", method=RequestMethod.POST)
	public List<LocationTelemetry> postLocationTelemetryList(@RequestBody List<LocationTelemetry> requestList) {
		List<LocationTelemetry> responseList = repository.insert(requestList);
		return responseList;
	}

}
