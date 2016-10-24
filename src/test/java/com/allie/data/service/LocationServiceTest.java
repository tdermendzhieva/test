package com.allie.data.service;

import com.allie.data.dto.Location;
import com.allie.data.dto.UserLocationDTO;
import com.allie.data.factory.LocationFactory;
import com.allie.data.jpa.model.LocationTelemetry;
import com.allie.data.repository.LocationTelemetryRepository;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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
    public void testEmptyInsertLocations() throws Exception {
        //we don't insert empty location records
        UserLocationDTO userLocationDTO = new UserLocationDTO();
        List<UserLocationDTO> locationDTOList = new ArrayList<>();
        locationDTOList.add(userLocationDTO);
        LocationTelemetry locationTelemetry = new LocationTelemetry();
        List<LocationTelemetry> locationTelemetries = new ArrayList<>();
        locationTelemetries.add(locationTelemetry);
        given(locationFactory.createLocationTelemetry(userLocationDTO)).willReturn(locationTelemetry);
        given(locationTelemetryRepository.insert(new ArrayList<LocationTelemetry>())).willReturn(new ArrayList<LocationTelemetry>());

        assertThat("should not insert empty locationTelemetry", service.insertLocations(locationDTOList).size(), equalTo(0));

    }

    @Test
    public void testInsertSingleLocation() throws Exception {

        UserLocationDTO userLocationDTO = new UserLocationDTO();
        List<UserLocationDTO> locationDTOList = new ArrayList<>();
        locationDTOList.add(userLocationDTO);
        LocationTelemetry locationTelemetry = new LocationTelemetry();
        locationTelemetry.setAllieId("test");
        double[] location = new double[2];
        location[0] = 0;
        location[1] = 0;
        locationTelemetry.setLocation(location);
        locationTelemetry.setTimestamp(new DateTime(2016, 10, 19, 12, 31, 0, 0));
        List<LocationTelemetry> locationTelemetries = new ArrayList<>();
        locationTelemetries.add(locationTelemetry);
        given(locationFactory.createLocationTelemetry(userLocationDTO)).willReturn(locationTelemetry);
        given(locationTelemetryRepository.insert(locationTelemetries)).willReturn(locationTelemetries);

        assertThat("inserted LocationTelemetry is returned", service.insertLocations(locationDTOList).get(0), equalTo(locationTelemetry));
    }

    @Test
    public void testInsertMixedValidityLocations() {
        //Create some inputs
        UserLocationDTO userLocationDTO1 = new UserLocationDTO();
        UserLocationDTO userLocationDTO2 = new UserLocationDTO();
        UserLocationDTO userLocationDTO3 = new UserLocationDTO();
        UserLocationDTO userLocationDTO4 = new UserLocationDTO();
        UserLocationDTO userLocationDTO5 = new UserLocationDTO();
        userLocationDTO1.setAllieId("1");
        userLocationDTO2.setAllieId("2");
        userLocationDTO3.setAllieId("3");
        userLocationDTO4.setAllieId("4");
        userLocationDTO5.setAllieId("5");
        //put them in an array
        List<UserLocationDTO> locationDTOList = new ArrayList<>();
        locationDTOList.add(userLocationDTO1);
        locationDTOList.add(userLocationDTO2);
        locationDTOList.add(userLocationDTO3);
        locationDTOList.add(userLocationDTO4);
        locationDTOList.add(userLocationDTO5);

        //Create the transformed inputs
        //Good
        LocationTelemetry lt1 = new LocationTelemetry();
        lt1.setAllieId("lt1");
        double[] loc1 = new double[2];
        loc1[0] = 0;
        loc1[1] = 0;
        lt1.setLocation(loc1);
        lt1.setTimestamp(new DateTime(2016, 10, 19, 12, 31, 1, 0));

        //Bad
        LocationTelemetry lt2 = new LocationTelemetry();

        //Bad
        LocationTelemetry lt3 = new LocationTelemetry();
        lt3.setAllieId("lt3");
        double[] loc3 = new double[2];
        loc3[0] = 0;
        loc3[1] = 0;
        lt1.setLocation(loc3);

        //Bad
        LocationTelemetry lt4 = new LocationTelemetry();
        lt4.setAllieId("lt4");
        lt4.setTimestamp(new DateTime(2016, 10, 19, 12, 31, 4, 0));

        //Bad
        LocationTelemetry lt5 = new LocationTelemetry();
        double[] loc5 = new double[2];
        loc5[0] = 0;
        loc5[1] = 0;
        lt5.setLocation(loc5);
        lt5.setTimestamp(new DateTime(2016, 10, 19, 12, 31, 5, 0));
        //put them into an array
        List<LocationTelemetry> locationTelemetries = new ArrayList<>();
        locationTelemetries.add(lt1);
        locationTelemetries.add(lt2);
        locationTelemetries.add(lt3);
        locationTelemetries.add(lt4);
        locationTelemetries.add(lt5);

        //assume that the inputs transform into the telemetries
        given(locationFactory.createLocationTelemetry(userLocationDTO1)).willReturn(lt1);
        given(locationFactory.createLocationTelemetry(userLocationDTO2)).willReturn(lt2);
        given(locationFactory.createLocationTelemetry(userLocationDTO3)).willReturn(lt3);
        given(locationFactory.createLocationTelemetry(userLocationDTO4)).willReturn(lt4);
        given(locationFactory.createLocationTelemetry(userLocationDTO5)).willReturn(lt5);

        //another assumption
        List<LocationTelemetry> diminishedList = new ArrayList<LocationTelemetry>();
        diminishedList.add(lt1);
        given(locationTelemetryRepository.insert(diminishedList)).willReturn(diminishedList);

        assertThat("inserted LocationTelemetry is returned", service.insertLocations(locationDTOList).get(0), equalTo(lt1));
    }
}