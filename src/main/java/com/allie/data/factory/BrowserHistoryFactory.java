package com.allie.data.factory;

import com.allie.data.dto.BrowserHistoryDTO;
import com.allie.data.jpa.model.BrowserHistory;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

/**
 * Created by jacob.headlee on 11/4/2016.
 */
@Component
public class BrowserHistoryFactory {

    /**
     * Generates a BrowserHistory object from a BrowserHistoryDTO
     * @param browserHistoryDTO the dto to transform
     * @return BrowserHistory transformed from browserHistoryDTO
     */
    public BrowserHistory createBrowserHistory(BrowserHistoryDTO browserHistoryDTO) {
        BrowserHistory browserHistory = new BrowserHistory();
        browserHistory.setAllieId(browserHistoryDTO.getAllieId());
        browserHistory.setUrl(browserHistoryDTO.getUrl());
        browserHistory.setTimestamp(new DateTime(browserHistoryDTO.getTimestamp()));
        return browserHistory;
    }
}
