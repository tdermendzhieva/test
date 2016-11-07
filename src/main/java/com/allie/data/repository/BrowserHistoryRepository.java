package com.allie.data.repository;

import com.allie.data.jpa.model.BrowserHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by jacob.headlee on 11/4/2016.
 */
public interface BrowserHistoryRepository extends MongoRepository<BrowserHistory, String> {
}
