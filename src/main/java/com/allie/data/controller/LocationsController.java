package com.allie.data.controller;

import com.allie.data.dto.UserLocationDTO;
import com.allie.data.service.LocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="allie-data/v1")
@Api(value = "locations", description = "Endpoint to store user location")
public class LocationsController {
	
	@Autowired
	public LocationService service;
	@ApiOperation(value = "Persistence service call to store a list of an allie user's location.",
			notes = "The service will asynchronously store all data in backend persistence structure.  If the request successfully reaches the service, a 202 (accepted) HttpStatus will " +
					"be returned")
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "The service successfully received the request"),
			@ApiResponse(code = 400, message = "The request was malformed to the point that no information can be stored"),
			@ApiResponse(code = 500, message = "There was an unspecified server error.")
	})
	@RequestMapping(value="/locations", method=RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void postLocationTelemetryList(@RequestBody List<UserLocationDTO> requestList,
										  @RequestHeader(name = "x-allie-request-id") String requestId,
										  @RequestHeader(name = "x-allie-correlation-id") String correlationId) {
		//might need to thread and respond
		Thread thread = new Thread(() -> service.insertLocations(requestList));
		thread.setName("insert-location");
		thread.start();
	}

}
