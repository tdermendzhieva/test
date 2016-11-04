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
    public BrowserHistory createBrowserHistory(BrowserHistoryDTO browserHistoryDTO) {
        BrowserHistory browserHistory = new BrowserHistory();
        browserHistory.setAllieId(browserHistoryDTO.getAllieId());
        browserHistory.setUrl(browserHistoryDTO.getUrl());
        browserHistory.setTimestamp(new DateTime(browserHistoryDTO.getTimestamp()));
        return browserHistory;
    }
}
