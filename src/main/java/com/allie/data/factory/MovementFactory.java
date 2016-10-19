package com.allie.data.factory;

import com.allie.data.dto.UserMovementDTO;
import com.allie.data.jpa.model.MovementTelemetry;

/**
 * Created by jacob.headlee on 10/19/2016.
 */
public class MovementFactory {
    public MovementTelemetry createMovementTelemetry(UserMovementDTO userMovementDTO) {
        return new MovementTelemetry();
    }
}
