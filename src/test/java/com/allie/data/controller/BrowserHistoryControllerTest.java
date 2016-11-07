package com.allie.data.controller;

import com.allie.data.dto.BrowserHistoryDTO;
import com.allie.data.service.BrowserHistoryService;
import com.allie.data.validation.ValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.allie.data.util.TestUtil.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jacob.headlee on 11/4/2016.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(BrowserHistoryController.class)
public class BrowserHistoryControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    BrowserHistoryService service;
    @MockBean
    ValidationService validationService;

    String requestId;
    String correlationId;
    BrowserHistoryDTO browserHistoryDTO;

    @Before
    public void setUp() {
        requestId = "req-id";
        correlationId = "corr-id";
        browserHistoryDTO = new BrowserHistoryDTO();
        browserHistoryDTO.setAllieId("allieId");
        browserHistoryDTO.setTimestamp("2016-11-04T09:01:48.483Z");
    }

    @Test
    public void testPostBrowserHistoryReturns202() throws Exception{
        mvc.perform(post("/allie-data/v1/browserHistories")
            .contentType(MediaType.APPLICATION_JSON)
            .header("x-allie-request-id", requestId)
            .header("x-allie-correlation-id", correlationId)
            .content(asJsonString(browserHistoryDTO)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testPostBrowserHistoryReturns400() throws Exception{
        mvc.perform(post("/allie-data/v1/browserHistories")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-allie-request-id", requestId)
                .header("x-allie-correlation-id", correlationId)
                .content(asJsonString(null)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testPostBrowserHistoryReturns422() throws Exception{
        browserHistoryDTO.setAllieId(null);
        mvc.perform(post("/allie-data/v1/browserHistories")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-allie-request-id", requestId)
                .header("x-allie-correlation-id", correlationId)
                .content(asJsonString(browserHistoryDTO)))
                .andExpect(status().isUnprocessableEntity());
    }
}
