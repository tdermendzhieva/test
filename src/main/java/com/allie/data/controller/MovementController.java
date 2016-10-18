package com.allie.data.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andrew.larsen on 10/18/2016.
 */
@RestController
@RequestMapping(value="allie-data/v1")
public class MovementController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value="/movements", method= RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void postMovementTelemetry() {
        logger.info("test from controller");
        //might need to thread and respond
    }
}
