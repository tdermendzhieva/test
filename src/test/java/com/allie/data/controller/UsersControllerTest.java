package com.allie.data.controller;

import com.allie.data.dto.UserRequestDTO;
import com.allie.data.dto.UserResponseDTO;
import com.allie.data.jpa.model.User;
import com.allie.data.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import static com.allie.data.util.TestUtil.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
/**
 * Created by jacob.headlee on 10/20/2016.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UsersController.class)
public class UsersControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    UserService service;

    UserRequestDTO userRequestDTO;
    UserResponseDTO userResponseDTO;

    @Test
    public void testPostUserNoHeadersReturnsBadRequest ()throws Exception {
        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setAllieId("test");
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setAllieId("test");

        given(this.service.insertUser(userRequestDTO)).willReturn(userResponseDTO);

        this.mvc.perform(post("/allie-data/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(userRequestDTO)))
            .andExpect(status().isBadRequest());

    }

    @Test
    public void testPostUserReturnsCreated () throws Exception {
        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setAllieId("test");
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setAllieId("test");

        given(this.service.insertUser(userRequestDTO)).willReturn(userResponseDTO);

        this.mvc.perform(post("/allie-data/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(userRequestDTO))
            .header("x-allie-request-id", "request-id")
            .header("x-allie-correlation-id", "correlation-id"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("allieId", is("test")));
    }

    @Test
    public void testPostOtherReturnsNotFound() throws Exception{
        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setAllieId("test");
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setAllieId("test");

        given(this.service.insertUser(userRequestDTO)).willReturn(userResponseDTO);

        this.mvc.perform(post("/other")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(userRequestDTO))
            .header("x-allie-request-id", "request-id")
            .header("x-allie-correlation-id", "correlation-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPostUserReturnsConflict() throws Exception{
        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setAllieId("test");
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setAllieId("test");

        given(this.service.insertUser(userRequestDTO)).willThrow(new DataIntegrityViolationException(""));

        this.mvc.perform(post("/allie-data/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userRequestDTO))
                .header("x-allie-request-id", "request-id")
                .header("x-allie-correlation-id", "correlation-id"))
                    .andExpect(status().isConflict());
    }

    @Test
    public void testPostUserReturnsBadRequest() throws Exception{
        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setPushToken("");
        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setPushToken("");

        given(this.service.insertUser(userRequestDTO)).willThrow(new IllegalArgumentException(""));

        this.mvc.perform(post("/allie-data/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userRequestDTO))
                .header("x-allie-request-id", "request-id")
                .header("x-allie-correlation-id", "correlation-id"))
                    .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUserReturnsUser() throws Exception{
        userResponseDTO = new UserResponseDTO();
        userResponseDTO.setAllieId("test");
        given(this.service.selectUser("test")).willReturn(userResponseDTO);

        this.mvc.perform(get("/allie-data/v1/users/test")
            .header("x-allie-request-id", "request-id")
            .header("x-allie-correlation-id", "correlation-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("allieId", is("test")));
    }

    @Test
    public void testGetUserReturnsBadRequest() throws Exception{
        given(this.service.selectUser(" ")).willThrow(new IllegalArgumentException());

        this.mvc.perform(get("/allie-data/v1/users/ ")
            .header("x-allie-request-id", "request-id")
            .header("x-allie-correlation-id", "correlation-id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUserReturnsNotFound() throws Exception{
        given(this.service.selectUser("test")).willThrow(new MissingResourceException("", "", ""));

        this.mvc.perform(get("/allie-data/v1/users/test")
            .header("x-allie-request-id", "request-id")
            .header("x-allie-correlation-id", "correlation-id"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllAllieIds() throws Exception{
        List<String> list = new ArrayList<>();
        list.add("id0");
        given(this.service.getAllUserIds(Mockito.anyString())).willReturn(list);

        this.mvc.perform(get("/allie-data/v1/users/?format=list")
            .header("x-allie-request-id", "request-id")
            .header("x-allie-correlation-id", "correlation-id"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]", is("id0")));
    }

    @Test
    public void testGetAllAllieIdsIsBadRequest() throws Exception{
        List<String> list = new ArrayList<>();
        list.add("id0");
        given(this.service.getAllUserIds(Mockito.anyString())).willThrow(new IllegalArgumentException(""));

        this.mvc.perform(get("/allie-data/v1/users/?format=asddfasd")
                .header("x-allie-request-id", "request-id")
                .header("x-allie-correlation-id", "correlation-id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllAllieIdsIsNotFound() throws Exception{
        List<String> list = new ArrayList<>();
        list.add("id0");
        given(this.service.getAllUserIds(Mockito.anyString())).willThrow(new MissingResourceException("","",""));

        this.mvc.perform(get("/allie-data/v1/users/?format=list")
                .header("x-allie-request-id", "request-id")
                .header("x-allie-correlation-id", "correlation-id"))
                .andExpect(status().isNotFound());

    }

}
