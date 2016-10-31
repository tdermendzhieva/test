package com.allie.data.controller;

import com.allie.data.dto.SkillDTO;
import com.allie.data.service.AllieSkillService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
            notes = "The service will get all Allie Kills")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The skills were successfully retrieved"),
            @ApiResponse(code = 404, message = "No events found for the given allieId and date"),
            @ApiResponse(code = 400, message = "Required allieId was null or empty")
    })
    @RequestMapping(value = "/skills", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<SkillDTO> getAllieSkills(){

        return service.getSkills();
    }
}
