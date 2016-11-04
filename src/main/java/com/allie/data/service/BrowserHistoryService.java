package com.allie.data.service;

import com.allie.data.dto.BrowserHistoryDTO;
import com.allie.data.factory.BrowserHistoryFactory;
import com.allie.data.jpa.model.BrowserHistory;
import com.allie.data.repository.BrowserHistoryRepository;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

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

    public BrowserHistory insertBrowserHistory(BrowserHistoryDTO browserHistoryDTO) {
        BrowserHistory browserHistory = factory.createBrowserHistory(browserHistoryDTO);
        BrowserHistory toReturn;
        if(browserHistory.getAllieId() != null && !browserHistory.getAllieId().isEmpty()) {
            toReturn = repository.insert(browserHistory);
        } else {
            logger.debug("missing required field allieId");
            throw new IllegalArgumentException("missing required field allieId");
        }
        if(toReturn.getDbId() == null) {
            logger.debug("Insert browser history failed for browserHistoryDTO {0}", browserHistoryDTO);
            throw new MongoException("failed to insert browser history");
        }
        return toReturn;

    }
}