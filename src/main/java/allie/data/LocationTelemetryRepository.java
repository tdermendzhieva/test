package allie.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationTelemetryRepository extends MongoRepository<LocationTelemetry, String> {
	public List<LocationTelemetry> findByAllieId(String allieId);
}
