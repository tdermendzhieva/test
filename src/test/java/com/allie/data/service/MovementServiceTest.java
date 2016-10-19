package com.allie.data.service;

import com.allie.data.dto.UserMovementDTO;
import com.allie.data.factory.MovementFactory;
import com.allie.data.jpa.model.MovementTelemetry;
import com.allie.data.repository.MovementTelemetryRepository;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Created by jacob.headlee on 10/19/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MovementServiceTest {

    @MockBean
    private MovementFactory movementFactory;
    @MockBean
    MovementTelemetryRepository movementTelemetryRepository;

    @Autowired
    MovementService service;

    @Test
    public void testEmptyInsertMovements() throws Exception {
        //we don't insert empty movement records
        UserMovementDTO userMovementDTO = new UserMovementDTO();
        List<UserMovementDTO> movementDTOList = new ArrayList<>();
        movementDTOList.add(userMovementDTO);
        MovementTelemetry movementTelemetry = new MovementTelemetry();
        List<MovementTelemetry> movementTelemetries = new ArrayList<>();
        movementTelemetries.add(movementTelemetry);
        given(movementFactory.createMovementTelemetry(userMovementDTO)).willReturn(movementTelemetry);
        given(movementTelemetryRepository.insert(new ArrayList<MovementTelemetry>())).willReturn(new ArrayList<MovementTelemetry>());

        assertThat("should not insert empty movementTelemetry", service.insertMovements(movementDTOList).size(), equalTo(0));

    }

    @Test
    public void testInsertSingleMovement() throws Exception {

        UserMovementDTO userMovementDTO = new UserMovementDTO();
        List<UserMovementDTO> movementDTOList = new ArrayList<>();
        movementDTOList.add(userMovementDTO);
        MovementTelemetry movementTelemetry = new MovementTelemetry();
        movementTelemetry.setAllieId("test");
        movementTelemetry.setTimestamp(new DateTime(2016, 10, 19, 12, 31, 0, 0));
        List<MovementTelemetry> movementTelemetries = new ArrayList<>();
        movementTelemetries.add(movementTelemetry);
        given(movementFactory.createMovementTelemetry(userMovementDTO)).willReturn(movementTelemetry);
        given(movementTelemetryRepository.insert(movementTelemetries)).willReturn(movementTelemetries);

        assertThat("inserted MovementTelemetry is returned", service.insertMovements(movementDTOList).get(0), equalTo(movementTelemetry));
    }

    @Test
    public void testInsertMixedValidityMovements() {
        //Create some inputs
        UserMovementDTO userMovementDTO1 = new UserMovementDTO();
        UserMovementDTO userMovementDTO2 = new UserMovementDTO();
        UserMovementDTO userMovementDTO3 = new UserMovementDTO();
        UserMovementDTO userMovementDTO4 = new UserMovementDTO();
        UserMovementDTO userMovementDTO5 = new UserMovementDTO();
        //put them in an array
        List<UserMovementDTO> movementDTOList = new ArrayList<>();
        movementDTOList.add(userMovementDTO1);
        movementDTOList.add(userMovementDTO2);
        movementDTOList.add(userMovementDTO3);
        movementDTOList.add(userMovementDTO4);
        movementDTOList.add(userMovementDTO5);

        //Create the transformed inputs
        //Good
        MovementTelemetry lt1 = new MovementTelemetry();
        lt1.setAllieId("lt1");
        lt1.setTimestamp(new DateTime(2016, 10, 19, 12, 31, 1, 0));

        //Bad
        MovementTelemetry lt2 = new MovementTelemetry();

        //Bad
        MovementTelemetry lt3 = new MovementTelemetry();
        lt3.setAllieId("lt3");
        double[] loc3 = new double[2];
        loc3[0] = 0;
        loc3[1] = 0;

        //Bad
        MovementTelemetry lt4 = new MovementTelemetry();
        lt4.setAllieId("lt4");
        lt4.setTimestamp(new DateTime(2016, 10, 19, 12, 31, 4, 0));

        //Bad
        MovementTelemetry lt5 = new MovementTelemetry();
        lt5.setTimestamp(new DateTime(2016, 10, 19, 12, 31, 5, 0));
        //put them into an array
        List<MovementTelemetry> movementTelemetries = new ArrayList<>();
        movementTelemetries.add(lt1);
        movementTelemetries.add(lt2);
        movementTelemetries.add(lt3);
        movementTelemetries.add(lt4);
        movementTelemetries.add(lt5);

        //assume that the inputs transform into the telemetries
        given(movementFactory.createMovementTelemetry(userMovementDTO1)).willReturn(lt1);
        given(movementFactory.createMovementTelemetry(userMovementDTO2)).willReturn(lt2);
        given(movementFactory.createMovementTelemetry(userMovementDTO3)).willReturn(lt3);
        given(movementFactory.createMovementTelemetry(userMovementDTO4)).willReturn(lt4);
        given(movementFactory.createMovementTelemetry(userMovementDTO5)).willReturn(lt5);

        //another assumption
        List<MovementTelemetry> diminishedList = new ArrayList<MovementTelemetry>();
        diminishedList.add(lt1);
        given(movementTelemetryRepository.insert(diminishedList)).willReturn(diminishedList);

        assertThat("inserted MovementTelemetry is returned", service.insertMovements(movementDTOList).get(0), equalTo(lt1));
    }
}
