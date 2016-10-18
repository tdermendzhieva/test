package com.allie.data.service;

import com.allie.data.dto.UserLocationDTO;
import com.allie.data.factory.LocationFactory;
import com.allie.data.jpa.model.LocationTelemetry;
import com.allie.data.repository.LocationTelemetryRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew.larsen on 10/17/2016.
 */
@Component
public class LocationService {
    private LocationTelemetryRepository repository;
    private LocationFactory locationFactory;

    //springboot will automagically autowire these
    public LocationService(LocationTelemetryRepository repository, LocationFactory locationFactory) {
        this.repository = repository;
        this.locationFactory = locationFactory;
    }

    /**
     * Method to convert userLocationDTO objects to LocationTelemetry
     * and batch insert
     * @param userLocationDTOs
     */
    public List<LocationTelemetry> insertLocations(List<UserLocationDTO> userLocationDTOs){
        List<LocationTelemetry> locationTelemetries = new ArrayList<>();
        //convert to JPA objects
        for(UserLocationDTO userLocation : userLocationDTOs){
            locationTelemetries.add(locationFactory.createLocationTelemetry(userLocation));
        }
        //batch insert
        return repository.insert(locationTelemetries);
    }
}
