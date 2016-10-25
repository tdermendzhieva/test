package com.allie.data.repository;

import com.allie.data.jpa.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
public interface UserRepository extends MongoRepository<User, String> {
    User findByAllieId(String allieId);
}
