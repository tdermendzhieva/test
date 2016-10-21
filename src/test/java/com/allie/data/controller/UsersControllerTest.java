package com.allie.data.controller;

import com.allie.data.dto.UserDTO;
import com.allie.data.jpa.model.User;
import com.allie.data.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.allie.data.util.TestUtil.asJsonString;
import static org.mockito.BDDMockito.given;
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

    UserDTO userDTO;
    User user;

    @Test
    public void testPostUserNoHeadersReturnsBadRequest ()throws Exception {
        user = new User();
        user.setAllieId("test");
        userDTO = new UserDTO();
        userDTO.setAllieId("test");

        given(this.service.insertUser(userDTO)).willReturn(user);

        this.mvc.perform(post("/allie-data/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(userDTO)))
            .andExpect(status().isBadRequest());

    }

    @Test
    public void testPostUserReturnsCreated () throws Exception {
        user = new User();
        user.setAllieId("test");
        userDTO = new UserDTO();
        userDTO.setAllieId("test");

        given(this.service.insertUser(userDTO)).willReturn(user);

        this.mvc.perform(post("/allie-data/v1/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(userDTO))
            .header("x-allie-request-id", "request-id")
            .header("x-allie-correlation-id", "correlation-id"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("allieId", is("test")));
    }

    @Test
    public void testPostOtherReturnsNotFound() throws Exception{
        user = new User();
        user.setAllieId("test");
        userDTO = new UserDTO();
        userDTO.setAllieId("test");

        given(this.service.insertUser(userDTO)).willReturn(user);

        this.mvc.perform(post("/other")
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(userDTO))
            .header("x-allie-request-id", "request-id")
            .header("x-allie-correlation-id", "correlation-id"))
                .andExpect(status().isNotFound());
    }

//    @Test
//    public void testPostUserReturnsConflict() throws Exception{
//        user = new User();
//        user.setAllieId("test");
//        userDTO = new UserDTO();
//        userDTO.setAllieId("test");
//
//        given(this.service.insertUser(userDTO)).willThrow(new DataIntegrityViolationException(""));
//
//        this.mvc.perform(post("/allie-data/v1/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(asJsonString(userDTO))
//                .header("x-allie-request-id", "request-id")
//                .header("x-allie-correlation-id", "correlation-id"))
//                    .andExpect(status().isConflict());
//    }

    @Test
    public void testPostUserReturnsBadRequest() throws Exception{
        user = new User();
        user.setPushToken("");
        userDTO = new UserDTO();
        userDTO.setPushToken("");

        given(this.service.insertUser(userDTO)).willThrow(new IllegalArgumentException(""));

        this.mvc.perform(post("/allie-data/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userDTO))
                .header("x-allie-request-id", "request-id")
                .header("x-allie-correlation-id", "correlation-id"))
                    .andExpect(status().isBadRequest());
    }




}
