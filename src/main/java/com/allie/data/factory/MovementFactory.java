package com.allie.data.factory;

import com.allie.data.dto.UserMovementDTO;
import com.allie.data.jpa.model.MovementTelemetry;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

/**
 * Created by jacob.headlee on 10/19/2016.
 */
@Component
public class MovementFactory {
    public MovementTelemetry createMovementTelemetry(UserMovementDTO userMovementDTO) {
        MovementTelemetry movementTelemetry = new MovementTelemetry();
        movementTelemetry.setAllieId(userMovementDTO.getAllieId());
        movementTelemetry.setTimestamp(new DateTime(userMovementDTO.getTimestamp()));
        movementTelemetry.setHasMoved(userMovementDTO.getHasMoved());
        return movementTelemetry;
    }
}
