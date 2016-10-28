package com.allie.data.controller;

import com.allie.data.jpa.model.Skill;
import com.allie.data.jpa.model.Skills;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew.larsen on 10/28/2016.
 */
@RestController
@RequestMapping(value="allie-data/v1")
@Api(value = "Skills", description = "Endpoint to manage Allie Skills events")
public class AllieSkillController {

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
    public List<Skill> getAllieSkills(){
        List<Skill> skills  = new ArrayList<>();

        skills.add(new Skill(Skills.ATTEND_MEETING.getValue(), "Allow Allie to watch your calendar and schedule to warn you about early morning meetings requiring a change to your morning schedule"));
        skills.add(new Skill(Skills.PROTECT_BY_ALLIE.getValue(), "Allow Allie to watch your e-mail for purchase notifications, and compare them to your browsing history to warn you about unexpected activity."));
        skills.add(new Skill(Skills.CHILD_SAFETY.getValue(), "Allow Allie to monitor your child to ensure they arrived home safely"));

        return skills;
    }
}
