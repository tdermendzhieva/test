package com.allie.data.service;

import com.allie.data.dto.UserLocationDTO;
import com.allie.data.factory.LocationFactory;
import com.allie.data.jpa.model.LocationTelemetry;
import com.allie.data.repository.LocationTelemetryRepository;
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
 * Created by andrew.larsen on 10/17/2016.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationServiceTest {

    @MockBean
    private LocationFactory locationFactory;
    @MockBean
    LocationTelemetryRepository locationTelemetryRepository;

    @Autowired
    LocationService service;

    @Test
    public void testInsertLocations() throws Exception {
        UserLocationDTO userLocationDTO = new UserLocationDTO();
        List<UserLocationDTO> locationDTOList = new ArrayList<>();
        locationDTOList.add(userLocationDTO);
        LocationTelemetry locationTelemetry = new LocationTelemetry();
        List<LocationTelemetry> locationTelemetries = new ArrayList<>();
        locationTelemetries.add(locationTelemetry);
        given(locationFactory.createLocationTelemetry(userLocationDTO)).willReturn(locationTelemetry);
        given(locationTelemetryRepository.insert(locationTelemetries)).willReturn(locationTelemetries);

        assertThat(service.insertLocations(locationDTOList).get(0), equalTo(locationTelemetry));

    }
}