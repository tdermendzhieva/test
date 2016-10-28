package com.allie.data.repository;

import com.allie.data.jpa.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
public interface UserRepository extends MongoRepository<User, String> {
    User findByAllieId(String allieId);

    @Query(value="{'allieId': {$ne:null}}", fields="{'allieId': 1, '_id': 0}")
    List<User> findAllAllieIds();
}
