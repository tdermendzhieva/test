package com.allie.data.controller;

import com.allie.data.dto.UserMovementDTO;
import com.allie.data.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by andrew.larsen on 10/18/2016.
 */
@RestController
@RequestMapping(value="allie-data/v1")
public class MovementController {

    @Autowired
    MovementService service;

    @RequestMapping(value="/movements", method= RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void postMovementTelemetry(@RequestBody List<UserMovementDTO> requestList) {
        Thread thread = new Thread(() -> service.insertMovements(requestList));
        thread.setName("insert-movement");
        thread.run();
    }
}
