package com.allie.data.factory;

import com.allie.data.dto.UserMovementDTO;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jacob.headlee on 10/19/2016.
 */
public class MovementFactoryTest {
    MovementFactory movementFactory = new MovementFactory();
    @Test
    public void testCreateMovementTelemetryTimestamp() throws Exception {
        UserMovementDTO userMovementDTO = new UserMovementDTO();
        userMovementDTO.setTimestamp("2012-04-23T18:25:43.511Z");
        assertThat(movementFactory.createMovementTelemetry(userMovementDTO).getTimestamp(), equalTo(new DateTime("2012-04-23T18:25:43.511Z")));

    }

    @Test
    public void testCreateMovementTelemetryMovement() throws Exception {
        UserMovementDTO userMovementDTO = new UserMovementDTO();
        assertThat(movementFactory.createMovementTelemetry(userMovementDTO), equalTo(null));

    }

    @Test
    public void testCreateMovementTelemetryAllieId() throws Exception {
        UserMovementDTO userMovementDTO = new UserMovementDTO();
        String allid = "asdfasdf";
        userMovementDTO.setAllieId(allid);
        assertThat(movementFactory.createMovementTelemetry(userMovementDTO).getAllieId(), equalTo(allid));

    }
}
