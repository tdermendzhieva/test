package com.allie.data.service;

import com.allie.data.dto.BrowserHistoryDTO;
import com.allie.data.factory.BrowserHistoryFactory;
import com.allie.data.jpa.model.BrowserHistory;
import com.allie.data.repository.BrowserHistoryRepository;
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

    public BrowserHistory insertBrowserHistory(BrowserHistoryDTO browserHistoryDTO) {
        BrowserHistory browserHistory = factory.createBrowserHistory(browserHistoryDTO);
        BrowserHistory toReturn = repository.insert(browserHistory);
        if(toReturn.getDbId() == null) {
            logger.debug("Insert browser history failed for browserHistoryDTO {0}", browserHistoryDTO);
        }
        return toReturn;

    }
}
