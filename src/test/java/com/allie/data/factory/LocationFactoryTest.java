package com.allie.data.factory;

import com.allie.data.dto.Location;
import com.allie.data.dto.UserLocationDTO;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by andrew.larsen on 10/17/2016.
 */
public class LocationFactoryTest {
    LocationFactory locationFactory = new LocationFactory();
    @Test
    public void testCreateLocationTelemetryTimestamp() throws Exception {
        UserLocationDTO userLocationDTO = new UserLocationDTO();
        userLocationDTO.setTimestamp("2012-04-23T18:25:43.511Z");
        Location location = new Location();
        location.setLongitude(10);
        location.setLatitude(10);
        userLocationDTO.setLocation(location);
        assertThat(locationFactory.createLocationTelemetry(userLocationDTO).getTimestamp(), equalTo(new DateTime("2012-04-23T18:25:43.511Z")));

    }

    @Test
    public void testCreateLocationTelemetryLocation() throws Exception {
        UserLocationDTO userLocationDTO = new UserLocationDTO();
        Location location = new Location();
        location.setLongitude(10);
        location.setLatitude(10);
        userLocationDTO.setLocation(location);
        double[] locationArray = new double[2];
        locationArray[0] = location.getLongitude();
        locationArray[1] = location.getLatitude();
        assertThat(locationFactory.createLocationTelemetry(userLocationDTO).getLocation(), equalTo(locationArray));

    }

    @Test
    public void testCreateLocationTelemetryAllieId() throws Exception {
        UserLocationDTO userLocationDTO = new UserLocationDTO();
        Location location = new Location();
        location.setLongitude(10);
        location.setLatitude(10);
        userLocationDTO.setLocation(location);
        String allid = "asdfasdf";
        userLocationDTO.setAllieId(allid);
        assertThat(locationFactory.createLocationTelemetry(userLocationDTO).getAllieId(), equalTo(allid));

    }
}