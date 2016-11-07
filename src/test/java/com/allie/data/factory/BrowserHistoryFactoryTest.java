package com.allie.data.factory;

import com.allie.data.dto.BrowserHistoryDTO;
import com.allie.data.jpa.model.BrowserHistory;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jacob.headlee on 11/4/2016.
 */
public class BrowserHistoryFactoryTest {
    BrowserHistoryDTO browserHistoryDTO;
    String allieId;
    String url;
    DateTime timestamp;
    BrowserHistoryFactory factory;

    @Before
    public void setUp() {
        factory = new BrowserHistoryFactory();
        allieId = "allieId";
        url = "http://somewebsite.com";
        String ts = "2016-11-04T09:06:39.234Z";
        timestamp = new DateTime(ts);
        browserHistoryDTO = new BrowserHistoryDTO();
        browserHistoryDTO.setAllieId(allieId);
        browserHistoryDTO.setUrl(url);
        browserHistoryDTO.setTimestamp(ts);
    }

    @Test
    public void testCreateBrowserHistoryAllieId() {
        BrowserHistory browserHistory = factory.createBrowserHistory(browserHistoryDTO);
        assertThat(browserHistory.getAllieId(), equalTo(allieId));
    }

    @Test
    public void testCreateBrowserHistoryUrl() {
        BrowserHistory browserHistory = factory.createBrowserHistory(browserHistoryDTO);
        assertThat(browserHistory.getUrl(), equalTo(url));
    }

    @Test
    public void testCreateBrowserHistoryTimestamp() {
        BrowserHistory browserHistory = factory.createBrowserHistory(browserHistoryDTO);
        assertThat(browserHistory.getTimestamp(), equalTo(timestamp));
    }

}
