package com.allie.data.integration;

import com.allie.data.dto.BrowserHistoryDTO;
import com.allie.data.jpa.model.BrowserHistory;
import com.allie.data.repository.AllieSkillsRepository;
import com.allie.data.repository.BrowserHistoryRepository;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jacob.headlee on 11/4/2016.
 */
@ActiveProfiles("DEVTEST")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEndBrowserHistoryTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MongoTemplate template;

    @Autowired
    private BrowserHistoryRepository repository;

    private BrowserHistory bh;
    private BrowserHistoryDTO dto;
    private String allieId;
    private String url;
    private String ts;
    private DateTime timeStamp;

    @Before
    public void setUp() {
        allieId = "allieId";
        url = "https://google.com?query=let+me+google+that+for+you";
        ts = "2016-11-04T10:16:49.128Z";
        timeStamp = new DateTime(ts);

        dto = new BrowserHistoryDTO();
        dto.setAllieId(allieId);
        dto.setTimestamp(ts);
        dto.setUrl(url);

        bh = new BrowserHistory();
        bh.setAllieId(allieId);
        bh.setUrl(url);
        bh.setTimestamp(timeStamp);

    }

    @After
    public void dropDB() {
        //Make sure we have the right db
        assertThat(template.getDb().getName(), equalTo("TEST"));
        //Then drop it
        template.getDb().dropDatabase();
    }


    @Test
    public void testPostBrowserHistory() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<BrowserHistoryDTO> entity = new HttpEntity<>(dto, headers);

        ResponseEntity response = restTemplate.postForEntity("/allie-data/v1/browserHistories", entity, Object.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.ACCEPTED));

        //wait on the thread that the post spins up
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread: threadSet) {
            if(thread.isAlive() && thread.getName().equals("insert-browser-history")) {
                thread.join(5 * 1000);
            }
        }

        List<BrowserHistory> historyList = repository.findAll();
        assertThat(historyList.size(), equalTo(1));
        historyList.get(0).setDbId(null);
        assertThat(historyList.get(0), equalTo(bh));
    }

    @Test
    public void testPostBrowserHistoryNoAllieId() throws Exception {
        dto.setAllieId(null);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<BrowserHistoryDTO> entity = new HttpEntity<>(dto, headers);

        ResponseEntity response = restTemplate.postForEntity("/allie-data/v1/browserHistories", entity, Object.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.ACCEPTED));

        //wait on the thread that the post spins up
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread: threadSet) {
            if(thread.isAlive() && thread.getName().equals("insert-browser-history")) {
                thread.join(5 * 1000);
            }
        }

        List<BrowserHistory> historyList = repository.findAll();
        assertThat(historyList.size(), equalTo(0));
    }

    @Test
    public void testPostBrowserHistoryNullBody() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("x-allie-request-id", "req-id");
        headers.add("x-allie-correlation-id", "corr-id");
        HttpEntity<BrowserHistoryDTO> entity = new HttpEntity<>(null, headers);

        ResponseEntity response = restTemplate.postForEntity("/allie-data/v1/browserHistories", entity, Object.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));

    }
}
