package com.allie.data.controller;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andrew.larsen on 10/26/2016.
 */
@RestController
@Api(value = "healthCheck", description = "Endpoint to monitor health of application")
public class HealthCheckController {

    @RequestMapping(value = "/healthCheck", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public void healthCheck() {

    }
}
