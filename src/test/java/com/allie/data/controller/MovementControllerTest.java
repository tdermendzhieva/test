package com.allie.data.controller;

import com.allie.data.dto.UserMovementDTO;
import com.allie.data.jpa.model.MovementTelemetry;
import com.allie.data.service.MovementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.allie.data.util.TestUtil.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jacob.headlee on 10/19/2016.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(MovementController.class)
public class MovementControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MovementService movementService;

    private String allieId;
    List<UserMovementDTO> userMovementDTOs;
    List<MovementTelemetry> movementTelemetries;
    UserMovementDTO userMovementDTO;
    MovementTelemetry movementTelemetry;


    @Before
    public void setup(){
        allieId = "allieiedalksjdfa";
        userMovementDTOs = new ArrayList<>();
        movementTelemetries = new ArrayList<>();
        userMovementDTO = new UserMovementDTO();
        movementTelemetry =  new MovementTelemetry();


    }

    @Test
    public void testPostMovementReturn404() throws Exception {

        userMovementDTOs.add(userMovementDTO);
        movementTelemetry.setAllieId(allieId);
        movementTelemetries.add(movementTelemetry);

        given(this.movementService.insertMovements(userMovementDTOs))
                .willReturn(movementTelemetries);

        this.mvc.perform(post("/notfound")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-allie-correlation-id", "corr-id")
                .header("x-allie-request-id", "req-id")
                .content(asJsonString(userMovementDTOs)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPostMovementReturnsAccepted() throws Exception {
        userMovementDTO.setAllieId(allieId);
        userMovementDTOs.add(userMovementDTO);
        movementTelemetry.setAllieId(allieId);
        movementTelemetries.add(movementTelemetry);

        given(this.movementService.insertMovements(userMovementDTOs))
                .willReturn(movementTelemetries);

        this.mvc.perform(post("/allie-data/v1/movements")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-allie-correlation-id", "corr-id")
                .header("x-allie-request-id", "req-id")
                .content(asJsonString(userMovementDTOs)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testPostMovementNoBody() throws Exception {
        userMovementDTO.setAllieId(allieId);
        userMovementDTOs.add(userMovementDTO);
        movementTelemetry.setAllieId(allieId);
        movementTelemetries.add(movementTelemetry);

        given(this.movementService.insertMovements(userMovementDTOs))
                .willReturn(movementTelemetries);

        this.mvc.perform(post("/allie-data/v1/movements")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-allie-correlation-id", "corr-id")
                .header("x-allie-request-id", "req-id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostMovementNoHeaders() throws Exception {
        userMovementDTO.setAllieId(allieId);
        userMovementDTOs.add(userMovementDTO);
        movementTelemetry.setAllieId(allieId);
        movementTelemetries.add(movementTelemetry);

        given(this.movementService.insertMovements(userMovementDTOs))
                .willReturn(movementTelemetries);

        this.mvc.perform(post("/allie-data/v1/movements")
                .content(asJsonString(userMovementDTOs)))
                .andExpect(status().isBadRequest());
    }
}
