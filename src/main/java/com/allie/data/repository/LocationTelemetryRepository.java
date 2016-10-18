package com.allie.data.repository;

import com.allie.data.jpa.model.LocationTelemetry;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationTelemetryRepository extends MongoRepository<LocationTelemetry, String> {
}
