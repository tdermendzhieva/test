package com.allie.data.repository;

import com.allie.data.jpa.model.LocationTelemetry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LocationTelemetryRepository extends MongoRepository<LocationTelemetry, String> {

	public List<LocationTelemetry> findByAllieId(String allieId);
}
