package com.allie.data.repository;

import com.allie.data.jpa.model.UserEvent;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by andrew.larsen on 10/24/2016.
 */
public interface UserEventRepository extends MongoRepository<UserEvent, String> {
    /**
     * retrieves user events from MongoDB based on allieId and a date range
     * @param allieId the user whose events are being retrieved
     * @param startDate earliest eventReceivedTimestamp to retrieve inclusive
     * @param endDate latest eventReceivedTimestamp to retrieve exclusive
     * @return
     */
    @Query(value = "{$and: [{'allieId' : ?0}, {'eventReceivedTimestamp' : {$gte: ?1}}, {'eventReceivedTimestamp' : {$lt: ?2}}]}")
    List<UserEvent> findUserEvents(String allieId, DateTime startDate, DateTime endDate);
}
