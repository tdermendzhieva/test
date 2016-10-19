package com.allie.data.factory;

import com.allie.data.dto.UserLocationDTO;
import com.allie.data.jpa.model.LocationTelemetry;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;



/**
 * Created by andrew.larsen on 10/17/2016.
 */
@Component
public class LocationFactory {

    public LocationTelemetry createLocationTelemetry(UserLocationDTO userLocation){
        LocationTelemetry locationTelemetry = new LocationTelemetry();
        locationTelemetry.setAllieId(userLocation.getAllieId());

        locationTelemetry.setTimestamp(new DateTime(userLocation.getTimestamp()));
        if(userLocation.getLocation() != null) {
            double[] location = new double[2];
            location[0] = userLocation.getLocation().getLongitude();
            location[1] = userLocation.getLocation().getLatitude();
            locationTelemetry.setLocation(location);
        }
        return locationTelemetry;
    }
}
