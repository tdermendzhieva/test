package com.allie.data.service;

import com.allie.data.dto.UserLocationDTO;
import com.allie.data.factory.LocationFactory;
import com.allie.data.jpa.model.LocationTelemetry;
import com.allie.data.repository.LocationTelemetryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew.larsen on 10/17/2016.
 */
@Component
public class LocationService {
    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

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
        List<LocationTelemetry> toReturn = repository.insert(locationTelemetries);

        //logging
        int initialSize = userLocationDTOs.size();
        int finalSize = toReturn.size();
        if(initialSize > finalSize) {
            logger.info("Failed to insert " + (initialSize - finalSize) + " messages of " + initialSize);
        } else {
            logger.info("Successfully inserted " + finalSize + " documents of " + finalSize);
        }
        return toReturn;
    }
}
