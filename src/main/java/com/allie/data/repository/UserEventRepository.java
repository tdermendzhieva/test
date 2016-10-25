package com.allie.data.repository;

import com.allie.data.jpa.model.UserEvent;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by andrew.larsen on 10/24/2016.
 */
public interface UserEventRepository extends MongoRepository<UserEvent, String> {
}
