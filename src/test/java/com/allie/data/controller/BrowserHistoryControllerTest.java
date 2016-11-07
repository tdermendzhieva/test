package com.allie.data.controller;

import com.allie.data.dto.BrowserHistoryDTO;
import com.allie.data.service.BrowserHistoryService;
import com.allie.data.util.StringTestUtil;
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
    private MockMvc mvc;

    @MockBean
    private BrowserHistoryService service;
    @MockBean
    private ValidationService validationService;

    private String requestId;
    private String correlationId;
    private BrowserHistoryDTO browserHistoryDTO;
    private String urlStringMax;

    @Before
    public void setUp() {
        requestId = "req-id";
        correlationId = "corr-id";
        browserHistoryDTO = new BrowserHistoryDTO();
        browserHistoryDTO.setAllieId("allieId");
        browserHistoryDTO.setTimestamp("2016-11-04T09:01:48.483Z");
        urlStringMax = StringTestUtil.getStringOfLength(500);

    }

    @Test
    public void testPostBrowserHistoryReturns202() throws Exception{
        mvc.perform(post("/allie-data/v1/browserHistoryRecords")
            .contentType(MediaType.APPLICATION_JSON)
            .header("x-allie-request-id", requestId)
            .header("x-allie-correlation-id", correlationId)
            .content(asJsonString(browserHistoryDTO)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testPostBrowserHistoryReturns400() throws Exception{
        mvc.perform(post("/allie-data/v1/browserHistoryRecords")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-allie-request-id", requestId)
                .header("x-allie-correlation-id", correlationId)
                .content(asJsonString(null)))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void testPostBrowserHistoryReturns422ForNullAllieId() throws Exception{
        browserHistoryDTO.setAllieId(null);
        mvc.perform(post("/allie-data/v1/browserHistoryRecords")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-allie-request-id", requestId)
                .header("x-allie-correlation-id", correlationId)
                .content(asJsonString(browserHistoryDTO)))
                .andExpect(status().isUnprocessableEntity());
    }
    @Test
    public void testPostBrowserHistoryReturns202ForMaxLengthString() throws Exception{
        browserHistoryDTO.setUrl(urlStringMax);
        mvc.perform(post("/allie-data/v1/browserHistoryRecords")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-allie-request-id", requestId)
                .header("x-allie-correlation-id", correlationId)
                .content(asJsonString(browserHistoryDTO)))
                .andExpect(status().isAccepted());
    }
    @Test
    public void testPostBrowserHistoryReturns422ForExceededMaxUrlLength() throws Exception{
        String maxLengthExceeded = urlStringMax + "1";
        browserHistoryDTO.setUrl(maxLengthExceeded);
        mvc.perform(post("/allie-data/v1/browserHistoryRecords")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-allie-request-id", requestId)
                .header("x-allie-correlation-id", correlationId)
                .content(asJsonString(browserHistoryDTO)))
                .andExpect(status().isUnprocessableEntity());
    }
}
