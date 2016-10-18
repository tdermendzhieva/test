package allie.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DBLocationRepository extends MongoRepository<DBLocation, String> {
	public List<DBLocation> findByAllieId(String allieId);
}
