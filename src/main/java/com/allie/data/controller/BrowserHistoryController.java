package com.allie.data.controller;

import com.allie.data.dto.BrowserHistoryDTO;
import com.allie.data.jpa.model.BrowserHistory;
import com.allie.data.service.BrowserHistoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jacob.headlee on 11/4/2016.
 */
@RestController
@RequestMapping("/allie-data/v1")
@Api(value = "browserHistory", description = "endpoint to store a user's browser history")
public class BrowserHistoryController {
    @Autowired
    BrowserHistoryService service;

    @ApiOperation(value = "Persistence service call to store an allie user's browser history.",
            notes = "The service will asynchronously store all data in backend persistence structure.  If the request successfully reaches the service, a 202 (accepted) HttpStatus will " +
                    "be returned")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "The service successfully received the request"),
            @ApiResponse(code = 400, message = "The request was malformed to the point that no information can be stored"),
            @ApiResponse(code = 422, message = "The request was well-formed, however, could not be processed due to semantic errors"),
            @ApiResponse(code = 500, message = "There was an unspecified server error."),
            @ApiResponse(code = 503, message = "There was an issue connecting to a downstream system")
    })
    @RequestMapping(value="/browserHistory", method= RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void postBrowserHistory(@RequestBody BrowserHistoryDTO browserHistory,
                                   @RequestHeader("x-allie-correlation-id") String correlationId,
                                   @RequestHeader("x-allie-request-id") String requestId) {
        Thread thread = new Thread(() -> service.insertBrowserHistory(browserHistory));
        thread.setName("insert-browser-history");
        thread.start();
    }
}
