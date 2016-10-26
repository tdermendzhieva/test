package com.allie.data.factory;

import com.allie.data.dto.UserEventDTO;
import com.allie.data.jpa.model.UserEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by andrew.larsen on 10/25/2016.
 */
public class UserEventFactoryTest {

    UserEventFactory factory = new UserEventFactory();
    private String userId = "TESTID";
    private String timestamp = new DateTime().toString();
    final static ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testCreateUserEventUserId() throws Exception {
        UserEventDTO userEventDTO = new UserEventDTO();
        userEventDTO.setAllieId(userId);
        UserEvent userEvent = factory.createUserEvent(userEventDTO);
        assertThat(userEvent.getAllieId()).isEqualTo(userId);

    }
    @Test
    public void testCreateUserEventTimestamp() throws Exception {
        UserEventDTO userEventDTO = new UserEventDTO();
        userEventDTO.setEventReceivedTimestamp(timestamp);
        UserEvent userEvent = factory.createUserEvent(userEventDTO);
        assertThat(userEvent.getEventReceivedTimestamp()).isEqualTo(new DateTime(timestamp));

    }
    @Test
    public void testCreateUserEventJson() throws Exception {
        UserEventDTO userEventDTO = new UserEventDTO();
        String json = "{\"test\" : 1}";
        Map jsonMap = mapper.readValue(json, HashMap.class);
        userEventDTO.setNeuraJson(jsonMap);
        UserEvent userEvent = factory.createUserEvent(userEventDTO);
        assertThat(userEvent.getNeuraJson().toString().replaceAll("\\s+", "")).isEqualTo(json.replaceAll("\\s+",""));

    }
    @Test
    public void testCreateUserEventAllNull() throws Exception {
        UserEventDTO userEventDTO = new UserEventDTO();
        UserEvent userEvent = factory.createUserEvent(userEventDTO);
        assertThat(userEvent.getNeuraJson()).isEqualTo(null);

    }

    @Test
    public void testCreateUserEventDTOAllieId() throws Exception {
        UserEvent userEvent = new UserEvent();
        userEvent.setAllieId("test");

        UserEventDTO userEventDTO = factory.createUserEventDTO(userEvent);
        assertThat(userEventDTO.getAllieId()).isEqualTo("test");
    }

    @Test
    public void testCreateUserEventDTOEventReceivedTimestamp() throws Exception{
        UserEvent userEvent = new UserEvent();
        userEvent.setEventReceivedTimestamp(new DateTime("2016-10-26T10:53:12.111Z"));

        UserEventDTO userEventDTO = factory.createUserEventDTO(userEvent);
        assertThat(userEventDTO.getEventReceivedTimestamp()).isEqualTo(new DateTime("2016-10-26T10:53:12.111Z").toString());
    }
}