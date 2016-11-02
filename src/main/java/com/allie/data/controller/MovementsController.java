package com.allie.data.controller;

import com.allie.data.dto.UserMovementDTO;
import com.allie.data.service.MovementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by andrew.larsen on 10/18/2016.
 */
@RestController
@RequestMapping(value="allie-data/v1")
@Api(value = "locations", description = "Endpoint to store user's phone movement")
public class MovementsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    MovementService service;

    /**
     * Endpoint to accept POST requests and persist the user movements
     * @param userMovements the list of user movements to persist
     * @param requestId id generated by calling service
     * @param correlationId id to correlate calls throughout service layers
     */
    @ApiOperation(value = "Persistence service call to store a list of an allie user's phone movement.",
            notes = "The service will asynchronously store all data in backend persistence structure.  If the request successfully reaches the service, a 202 (accepted) HttpStatus will " +
                    "be returned")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "The service successfully received the request"),
            @ApiResponse(code = 400, message = "The request was malformed to the point that no information can be stored"),
            @ApiResponse(code = 422, message = "The request was well-formed, however, could not be processed due to semantic errors"),
            @ApiResponse(code = 500, message = "There was an unspecified server error."),
            @ApiResponse(code = 503, message = "There was an issue connecting to a downstream system")
    })
    @RequestMapping(value = "/movements", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void postMovementTelemetry(@RequestBody List<UserMovementDTO> userMovements,
                                      @RequestHeader(name = "x-allie-request-id") String requestId,
                                      @RequestHeader(name = "x-allie-correlation-id") String correlationId) {
        Thread thread = new Thread(() -> service.insertMovements(userMovements));
        thread.setName("insert-movement");
        thread.start();
    }
}

