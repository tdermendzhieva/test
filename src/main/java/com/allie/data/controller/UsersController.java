package com.allie.data.controller;

import com.allie.data.dto.UserDTO;
import com.allie.data.jpa.model.User;
import com.allie.data.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
@RestController
@RequestMapping(value = "allie-data/v1")
@Api(value = "users", description = "Endpoint to store a user")
public class UsersController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService service;

    @ApiOperation(value = "Persistence service call to store a new user.",
            notes = "The service will store the user in backend persistence structure.  If the request is successful, a 201 (created) HttpStatus will " +
                    "be returned, with the persisted user in the body")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The service successfully persisted the user"),
            @ApiResponse(code = 400, message = "The request was malformed to the point that no information can be stored"),
            //409
            @ApiResponse(code = 500, message = "There was an unspecified server error.")
    })
    @RequestMapping(value = "/users", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User postUser(@RequestBody UserDTO userDTO,
                         @RequestHeader(value = "x-allie-request-id") String requestId,
                         @RequestHeader(value = "x-allie-correlation-id") String correlationId) {
        User toReturn = service.insertUser(userDTO);
        return toReturn;
    }

}
