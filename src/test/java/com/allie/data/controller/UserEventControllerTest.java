package com.allie.data.controller;

import com.allie.data.dto.UserEventDTO;
import com.allie.data.jpa.model.UserEvent;
import com.allie.data.service.UserEventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by andrew.larsen on 10/25/2016.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserEventController.class)
public class UserEventControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserEventService service;
    UserEventDTO userEventDTO = new UserEventDTO();

    @Test
    public void testCreateUserEvent() throws Exception {
        given(this.service.insertEvent(new UserEventDTO()))
                .willReturn(new UserEvent());
        this.mvc.perform(post("/allie-data/v1/events")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-allie-correlation-id", "corr-id")
                .header("x-allie-request-id", "req-id")
                .content(new ObjectMapper().writeValueAsString(new UserEventDTO())))
                .andExpect(status().isCreated());
    }
    @Test
    public void testCreateUserThrows503() throws Exception {
        given(this.service.insertEvent(Mockito.anyObject()))
                .willThrow(new MongoException("TEST"));
        this.mvc.perform(post("/allie-data/v1/events")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-allie-correlation-id", "corr-id")
                .header("x-allie-request-id", "req-id")
                .content(new ObjectMapper().writeValueAsString(userEventDTO)))
                .andExpect(status().isServiceUnavailable());
    }
    @Test
    public void testCreateUserThrowsIllegalExceptionReturns400() throws Exception {
        given(this.service.insertEvent(Mockito.anyObject()))
                .willThrow(new IllegalArgumentException("TEST"));
        this.mvc.perform(post("/allie-data/v1/events")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-allie-correlation-id", "corr-id")
                .header("x-allie-request-id", "req-id")
                .content(new ObjectMapper().writeValueAsString(userEventDTO)))
                .andExpect(status().isBadRequest());
    }
}