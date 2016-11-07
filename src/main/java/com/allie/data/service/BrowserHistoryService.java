package com.allie.data.service;

import com.allie.data.dto.BrowserHistoryDTO;
import com.allie.data.factory.BrowserHistoryFactory;
import com.allie.data.jpa.model.BrowserHistory;
import com.allie.data.repository.BrowserHistoryRepository;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by jacob.headlee on 11/4/2016.
 */
@Component
public class BrowserHistoryService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private BrowserHistoryRepository repository;
    private BrowserHistoryFactory factory;

    public BrowserHistoryService(BrowserHistoryFactory factory, BrowserHistoryRepository repository) {
        this.repository = repository;
        this.factory = factory;
    }

    /**
     * transforms then inserts browser history data into the mongo db
     * @param browserHistoryDTO the browser history information to persist
     * @return the persisted browser history information
     * @throws IllegalArgumentException when an allieId is not supplied, as this field is mandatory
     * @throws MongoException when the insert to mongo fails and a dbId is not returned
     */
    public BrowserHistory insertBrowserHistory(BrowserHistoryDTO browserHistoryDTO) {
        BrowserHistory browserHistory = factory.createBrowserHistory(browserHistoryDTO);
        BrowserHistory toReturn;
        toReturn = repository.insert(browserHistory);
        //if we don't get a dbId it didn't properly insert
        if (toReturn.getDbId() == null) {
            logger.debug("Insert browser history failed for browserHistoryDTO {0}", browserHistoryDTO);
            throw new MongoException("failed to insert browser history");
        }
        return toReturn;

    }
}
