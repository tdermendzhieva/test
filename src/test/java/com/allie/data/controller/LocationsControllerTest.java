package com.allie.data.controller;

import com.allie.data.dto.UserLocationDTO;
import com.allie.data.jpa.model.LocationTelemetry;
import com.allie.data.service.LocationService;
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
 * Created by andrew.larsen on 10/17/2016.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(LocationsController.class)
public class LocationsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LocationService locationService;

    private String allieId;
    List<UserLocationDTO> userLocationDTOs;
    List<LocationTelemetry> locationTelemetries;
    UserLocationDTO userLocationDTO;
    LocationTelemetry locationTelemetry;


    @Before
    public void setup(){
        allieId = "allieiedalksjdfa";
        userLocationDTOs = new ArrayList<>();
        locationTelemetries = new ArrayList<>();
        userLocationDTO = new UserLocationDTO();
        locationTelemetry =  new LocationTelemetry();


    }
    @Test
    public void testPostLocationReturnsAccepted() throws Exception {

        userLocationDTOs.add(userLocationDTO);
        locationTelemetry.setAllieId(allieId);
        locationTelemetries.add(locationTelemetry);

        given(this.locationService.insertLocations(userLocationDTOs))
                .willReturn(locationTelemetries);

        this.mvc.perform(post("/allie-data/v1/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userLocationDTOs)))
                .andExpect(status().isAccepted());
    }
    @Test
    public void testPostLocationReturn404() throws Exception {

        userLocationDTOs.add(userLocationDTO);
        locationTelemetry.setAllieId(allieId);
        locationTelemetries.add(locationTelemetry);

        given(this.locationService.insertLocations(userLocationDTOs))
                .willReturn(locationTelemetries);

        this.mvc.perform(post("/notfound")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userLocationDTOs)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPostLocationReturnsAcceptedAndAllieId() throws Exception {
        userLocationDTO.setAllieId(allieId);
        userLocationDTOs.add(userLocationDTO);
        locationTelemetry.setAllieId(allieId);
        locationTelemetries.add(locationTelemetry);

        given(this.locationService.insertLocations(userLocationDTOs))
                .willReturn(locationTelemetries);

        this.mvc.perform(post("/allie-data/v1/locations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userLocationDTOs)))
                .andExpect(status().isAccepted());
                //and except response contains allieId
    }

}