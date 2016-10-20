package com.allie.data.service;

import com.allie.data.dto.UserMovementDTO;
import com.allie.data.factory.MovementFactory;
import com.allie.data.jpa.model.MovementTelemetry;
import com.allie.data.repository.MovementTelemetryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacob.headlee on 10/19/2016.
 */
@Component
public class MovementService {
    private static final Logger logger = LoggerFactory.getLogger(MovementService.class);

    private MovementTelemetryRepository repository;
    private MovementFactory movementFactory;

    public MovementService(MovementTelemetryRepository repository, MovementFactory movementFactory) {
        this.repository = repository;
        this.movementFactory = movementFactory;
    }




    public List<MovementTelemetry> insertMovements(List<UserMovementDTO> userMovementDTOs) {
        List<MovementTelemetry> movementTelemetries = new ArrayList<MovementTelemetry>();
        //Convert to jpa objects
        MovementTelemetry mt = new MovementTelemetry();
        for (UserMovementDTO dto : userMovementDTOs) {
            mt = movementFactory.createMovementTelemetry(dto);
            if(
                    mt.getAllieId() != null
                    && mt.hasMoved != null
                    && mt.getTimestamp() != null
                    ) {
                movementTelemetries.add(mt);
            } else {
                logger.debug("User Movement missing required field, received:" + dto);
            }
        }
        //batch insert
        List<MovementTelemetry> toReturn = repository.insert(movementTelemetries);
        //log then return
        int initialSize = userMovementDTOs.size();
        int finalSize = toReturn.size();
        if(initialSize > finalSize) {
            logger.info("Failed to insert " + (initialSize - finalSize) + " messages of " + initialSize);
        } else {
            logger.info("Successfully inserted " + finalSize + " documents of " + finalSize);
        }
        return toReturn;
    }
}
