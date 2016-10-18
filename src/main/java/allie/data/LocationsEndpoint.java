package allie.data;

import java.util.ArrayList;
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
	public DBLocationRepository repository;
	
	@RequestMapping(value="/locations", method=RequestMethod.POST)
	public List<DBLocation> postLocationTelemetryList(@RequestBody List<ReqLocation> requestLocations) {
		List<DBLocation> dbLocations = new ArrayList<DBLocation>();
		for (ReqLocation rl : requestLocations) {
			dbLocations.add(rl.toDBLocation());
		}
		
		List<DBLocation> responseList = repository.insert(dbLocations);
		return responseList;
	}
}
