package com.allie.data.service;

import com.allie.data.dto.BrowserHistoryDTO;
import com.allie.data.factory.BrowserHistoryFactory;
import com.allie.data.jpa.model.BrowserHistory;
import com.allie.data.repository.BrowserHistoryRepository;
import com.mongodb.MongoException;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Created by jacob.headlee on 11/4/2016.
 */
public class BrowserHistoryServiceTest {
    private BrowserHistoryFactory factory;
    private BrowserHistoryRepository repository;
    private BrowserHistoryService service;

    private BrowserHistory browserHistory;
    private BrowserHistoryDTO browserHistoryDTO;

    private String dbId;
    private String url;
    private String ts;
    private DateTime timestamp;
    private String allieId;

    @Before
    public void setUp(){
        allieId = "allieId";
        url = "http://wherever.something";
        ts = "2016-11-04T11:02:45.384Z";
        timestamp = new DateTime(ts);
        dbId = "notNull";

        browserHistoryDTO = new BrowserHistoryDTO();
        browserHistory = new BrowserHistory();
        browserHistory.setDbId(dbId);
        browserHistory.setAllieId(allieId);
        browserHistory.setTimestamp(timestamp);
        browserHistory.setUrl(url);
        browserHistoryDTO.setAllieId(allieId);
        browserHistoryDTO.setUrl(url);
        browserHistoryDTO.setTimestamp(ts);

        factory = mock(BrowserHistoryFactory.class);
        repository = mock(BrowserHistoryRepository.class);
        service = new BrowserHistoryService(factory, repository);
    }

    @Test
    public void testInsert(){
        given(repository.insert(Matchers.any(BrowserHistory.class))).willReturn(browserHistory);
        given(factory.createBrowserHistory(Mockito.anyObject())).willReturn(browserHistory);
        assertThat(service.insertBrowserHistory(browserHistoryDTO), equalTo(browserHistory));
    }

    @Test
    public void testInsertFailed() {
        given(repository.insert(Matchers.any(BrowserHistory.class))).willReturn(new BrowserHistory());
        given(factory.createBrowserHistory(Mockito.anyObject())).willReturn(browserHistory);
        try {
            service.insertBrowserHistory(browserHistoryDTO);
        } catch (Exception e) {
            assertThat(e.getClass(), equalTo(MongoException.class));
            return;
        }
        fail();
    }

}
