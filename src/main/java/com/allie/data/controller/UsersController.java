package com.allie.data.controller;

import com.allie.data.dto.UserRequestDTO;
import com.allie.data.dto.UserResponseDTO;
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

import java.net.URLDecoder;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
@RestController
@RequestMapping(value = "allie-data/v1")
@Api(value = "users", description = "Endpoints for users")
public class UsersController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService service;

    @ApiOperation(value = "Persistence service call to store a new user.",
            notes = "The service will store the user in backend persistence structure.  If the request is successful, a 201 (created) HttpStatus will " +
                    "be returned, with the persisted user in the body, an allieId is required to persist a user, if an allieId is not provided "+
                    "a 400 (bad request) will be returned")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The service successfully persisted the user"),
            @ApiResponse(code = 400, message = "No allieId was provided or the request was malformed to the point that no information can be stored"),
            @ApiResponse(code = 409, message = "A user with the given allieId already exists"),
            @ApiResponse(code = 500, message = "There was an unspecified server error.")
    })
    @RequestMapping(value = "/users", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO postUser(@RequestBody UserRequestDTO userRequestDTO,
                         @RequestHeader(value = "x-allie-request-id") String requestId,
                         @RequestHeader(value = "x-allie-correlation-id") String correlationId) {
        return service.insertUser(userRequestDTO);

    }


    @ApiOperation(value = "Persistence service call to retrieve a user with a given allieId",
            notes = "The service will return the user.  If the request is successful, a 200 (ok) status will be returned, with the " +
                    "user in the body.  If no user is found for the given allieId a 404 (not found) will be returned.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The service found the user, the user is in the body"),
            @ApiResponse(code = 400, message = "Given allieId was null or empty"),
            @ApiResponse(code = 404, message = "No user found for given allieId"),
            @ApiResponse(code = 500, message = "There was an unspecified server error.")
    })
    @RequestMapping(value = "/users/{allieId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDTO getUser(@PathVariable String allieId,
                                   @RequestHeader(value = "x-allie-request-id") String requestId,
                                   @RequestHeader(value = "x-allie-correlation-id") String correlationId) throws Exception{
        allieId = URLDecoder.decode(allieId, "UTF-8");
        return service.selectUser(allieId);
    }

}
