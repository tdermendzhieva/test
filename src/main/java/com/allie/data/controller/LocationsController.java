package com.allie.data.controller;

import com.allie.data.dto.UserLocationDTO;
import com.allie.data.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="allie-data/v1")
public class LocationsController {
	
	@Autowired
	public LocationService service;

	@RequestMapping(value="/locations", method=RequestMethod.POST, consumes = "application/json")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void postLocationTelemetryList(@RequestBody List<UserLocationDTO> requestList) {
		//might need to thread and respond
		new Thread(() -> {
			service.insertLocations(requestList);
		}).start();
		return;
	}

}
