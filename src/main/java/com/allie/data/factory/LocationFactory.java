package com.allie.data.factory;

import com.allie.data.dto.UserLocationDTO;
import com.allie.data.jpa.model.LocationTelemetry;
import org.bson.types.BSONTimestamp;
import org.springframework.stereotype.Component;

/**
 * Created by andrew.larsen on 10/17/2016.
 */
@Component
public class LocationFactory {

    public LocationTelemetry createLocationTelemetry(UserLocationDTO userLocation){
        LocationTelemetry locationTelemetry = new LocationTelemetry();
        locationTelemetry.setAllieId(userLocation.getAllieId());
        //todo-update
        locationTelemetry.setTimestamp(new BSONTimestamp());
        if(userLocation.getLocation() == null) {
            double[] location = {userLocation.getLocation().getLatitude(), userLocation.getLocation().getLongitude()};
            locationTelemetry.setLocation(location);

        }

        return locationTelemetry;
    }
}
