package com.allie.data.repository;

import com.allie.data.jpa.model.MovementTelemetry;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by jacob.headlee on 10/19/2016.
 */
public interface MovementTelemetryRepository extends MongoRepository<MovementTelemetry, String> {
}
