package com.allie.data.jpa.model;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 * Created by andrew.larsen on 10/17/2016.
 */
public class LocationTelemetryTest {
    LocationTelemetry lt;

    @Before
    public void init() {
        String allieId = "test";
        double[] location = new double[2];
        location[0] = 1.23456789;
        location[1] = -9.87654321;
        DateTime timestamp = new DateTime("2012-04-23T18:25:43.511Z");
        lt = new LocationTelemetry(allieId, location, timestamp);
    }

    @Test
    public void testLocationsTelemetryConstructor() {
        double[] test  = new double[2];
        test[0] = 1.23456789;
        test[1] = -9.87654321;

        Assert.state(lt.allieId.equals("test"),"Constructor must set value for allieId");
        Assert.state((lt.location.length == test.length && lt.location.length == 2 && lt.location[0] == test[0] && lt.location[1] == test[1]), "Constructor must set value for location");
        Assert.state(lt.timestamp.equals(new DateTime("2012-04-23T18:25:43.511Z")), "Constructor must set value for timestamp");
    }

    public void testGetAllieId() {
        Assert.state(lt.getAllieId().equals("test"));
    }

    public void testGetLocation() {
        double[] test = {1.23456789, -9.87654321};

        Assert.state(lt.getLocation() == test);
    }

    public void testGetTimeStamp() {
        Assert.state(lt.getTimestamp().equals(new DateTime("2012-04-23T18:25:43.511Z")));
    }
}