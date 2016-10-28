package com.allie.data.controller;

import com.allie.data.dto.UserRequestDTO;
import com.allie.data.dto.UserResponseDTO;
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

import java.util.List;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
@RestController
@RequestMapping(value = "/allie-data/v1")
@Api(value = "users", description = "Endpoints for users")
public class UsersController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UserService service;

    /**
     * Endpoint to persist a user
     * @param user the user to persist
     * @param requestId id generated by calling service
     * @param correlationId id to correlate calls throughout service layers
     * @return
     */
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
    public UserResponseDTO postUser(@RequestBody UserRequestDTO user,
                         @RequestHeader(value = "x-allie-request-id") String requestId,
                         @RequestHeader(value = "x-allie-correlation-id") String correlationId) {
        return service.insertUser(user);

    }

    /**
     * Endpoint to retrieve a user
     * @param allieId the user to retrieve
     * @param requestId id generated by calling service
     * @param correlationId id to correlate calls throughout service layers
     * @return
     * @throws Exception
     */
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
        return service.selectUser(allieId);
    }


    /**
     * call to get all users based on a format input, currently only supported format is 'list' (case insensitive)
     * which returns all users allieIds with as a List&lt;String&gt;
     * @param format the format requested, currently must be 'list' (case insensitive)
     * @param correlationId id to correlate calls throughout service layers
     * @param requestId id generated by calling service
     * @return List&lt;?&gt; depending on format, currently only supported format 'list' returns List&lt;String&gt;
     */
    @ApiOperation(value="Persistence service call to retrieve all allieIds",
            notes="the format value 'list' (case insensitive) will retrieve a list of all Allie Ids, " +
                    "currently all other values for format will return a 400 (bad request)")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "The service found the users and is returning them in the requested format"),
            @ApiResponse(code = 400, message="The requested format is invalid"),
            @ApiResponse(code = 404, message = "No users found"),
            @ApiResponse(code = 500, message = "There was an unspecified error")
    })
    @RequestMapping(value = "/users/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<?> getAllUsers(@RequestParam(value = "format") String format,
                                                      @RequestHeader(value="x-allie-correlation-id") String correlationId,
                                                      @RequestHeader(value="x-allie-request-id") String requestId) {
        return service.getAllUserIds(format);
    }
}
