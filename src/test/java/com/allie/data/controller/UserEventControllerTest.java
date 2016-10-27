package com.allie.data.controller;

import com.allie.data.dto.UserEventDTO;
import com.allie.data.jpa.model.UserEvent;
import com.allie.data.service.UserEventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.mongodb.MongoSocketException;
import com.mongodb.ServerAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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


    @Test
    public void testGetUserEvent() throws Exception{
        UserEventDTO dto = new UserEventDTO();
        dto.setAllieId("test");
        List<UserEventDTO> list = new ArrayList<>();
        list.add(dto);
        dto.setEventReceivedTimestamp("blah");
        given(this.service.selectEvents("test", "blah"))
                .willReturn(list);
        this.mvc.perform(get("/allie-data/v1/users/test/events?received_date=blah")
                .header("x-allie-correlation-id", "corr-id")
                .header("x-allie-request-id", "req-id"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].allieId", is("test")))
                    .andExpect(jsonPath("$[0].eventReceivedTimestamp", is("blah")));

    }

    @Test
    public void testGetUserReturns404() throws Exception{
        given(this.service.selectEvents("test", "blah"))
                .willThrow(new MissingResourceException("", "", ""));
        this.mvc.perform(get("/allie-data/v1/user/test/events?received_date=blah")
                .header("x-allie-correlation-id", "corr-id")
                .header("x-allie-request-id", "req-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetUserReturns400() throws Exception{
        given(this.service.selectEvents("test", "blah"))
                .willThrow(new IllegalArgumentException());
        this.mvc.perform(get("/allie-data/v1/users/test/events?received_date=blah")
                .header("x-allie-correlation-id", "corr-id")
                .header("x-allie-request-id", "req-id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUserReturns503OnSocketTimeout() throws Exception{
        given(this.service.selectEvents("test", "blah"))
                .willThrow(new MongoSocketException("TEST", new ServerAddress()));
        this.mvc.perform(get("/allie-data/v1/users/test/events?received_date=blah")
                .header("x-allie-correlation-id", "corr-id")
                .header("x-allie-request-id", "req-id"))
                .andExpect(status().isServiceUnavailable());
    }
}