package com.allie.data.controller;

import com.allie.data.dto.BrowserHistoryDTO;
import com.allie.data.service.BrowserHistoryService;
import com.allie.data.validation.ValidationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by jacob.headlee on 11/4/2016.
 */
@RestController
@RequestMapping("/allie-data/v1")
@Api(value = "browserHistories", description = "endpoint to store a user's browser history")
public class BrowserHistoryController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BrowserHistoryService service;
    @Autowired
    ValidationService validationService;
    /**
     * Endpoint to capture and persist browser history data asynchronously
     * @param browserHistory the information about the user and their browsing to store
     * @param correlationId an id created by the generating system for cross-referencing the request
     * @param requestId an id created by the requester to reference the request
     */
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
    @RequestMapping(value="/browserHistories", method= RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void postBrowserHistory(@RequestHeader("x-allie-correlation-id") String correlationId,
                                   @RequestHeader("x-allie-request-id") String requestId,
                                   @Valid @RequestBody BrowserHistoryDTO browserHistory,
                                   BindingResult bindingResult) {

        //Check for validation errors
        if(bindingResult.hasErrors()) {
            logger.debug("Validation errors encountered in request");
            String message = validationService.getValidationErrors(bindingResult);
            throw new IllegalArgumentException(message);
        }
        //Async call, spin up a new thread then return
        Thread thread = new Thread(() -> service.insertBrowserHistory(browserHistory));
        //naming the thread for testability
        thread.setName("insert-browser-history");
        thread.start();
    }
}
