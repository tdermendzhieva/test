package com.allie.data.controller;

import com.allie.data.dto.UserEventDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by andrew.larsen on 10/24/2016.
 */
@RestController
@RequestMapping(value="allie-data/v1")
@Api(value = "Events", description = "Endpoint to manage user events")
public class UserEventController {


   // UserEventService service;
    @ApiOperation(value = "Persistence service call to store an Allie User Event",
            notes = "The service will asynchronously store all data in backend persistence structure.  If the request successfully reaches the service, a 202 (accepted) HttpStatus will " +
                    "be returned")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "The service successfully received the request"),
            @ApiResponse(code = 400, message = "The request was malformed to the point that no information can be stored"),
            @ApiResponse(code = 500, message = "There was an unspecified server error.")
    })
    @RequestMapping(value="/events", method= RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUserEvent(@RequestBody UserEventDTO request,
                                          @RequestHeader(name = "x-allie-request-id") String requestId,
                                          @RequestHeader(name = "x-allie-correlation-id") String correlationId) {
      //  service.insertEvent(request);
    }
}
