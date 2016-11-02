package com.allie.data.controller;

import com.allie.data.dto.SkillDTO;
import com.allie.data.service.AllieSkillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by andrew.larsen on 10/28/2016.
 */
@RestController
@RequestMapping(value="allie-data/v1")
@Api(value = "Skills", description = "Endpoint to manage Allie Skills events")
public class AllieSkillController {

    @Autowired
    private AllieSkillService service;
    /**
     * Endpoint to retrieve Allie Skills
     * @return
     */
    @ApiOperation(value = "Persistence service call to retrieve Allie Skills",
            notes = "The service will retrieve all allie skills from a master list contained in backend persistence")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Skills were successfully retrieved"),
            @ApiResponse(code = 400, message = "The request was malformed to the point that no information can be stored"),
            @ApiResponse(code = 404, message = "No allie skills were found"),
            @ApiResponse(code = 422, message = "The request was well-formed, however, could not be processed due to semantic errors"),
            @ApiResponse(code = 500, message = "There was an unspecified server error."),
            @ApiResponse(code = 503, message = "There was an issue connecting to a downstream system")
    })
    @RequestMapping(value = "/skills", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<SkillDTO> getAllieSkills(@RequestHeader(name = "x-allie-request-id") String requestId,
                                         @RequestHeader(name = "x-allie-correlation-id") String correlationId){

        return service.getSkills();
    }
}
