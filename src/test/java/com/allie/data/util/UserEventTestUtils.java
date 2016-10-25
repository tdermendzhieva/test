package com.allie.data.util;

import com.allie.data.dto.UserEventDTO;
import com.allie.data.jpa.model.UserEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import org.joda.time.DateTime;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew.larsen on 10/25/2016.
 */
public class UserEventTestUtils {
    final static ObjectMapper mapper = new ObjectMapper();

    /**
     * Helper create methods
     * @return
     * @throws IOException
     */
    public static UserEventDTO createEventDTO() throws IOException {
        UserEventDTO userEventDTO = new UserEventDTO();
        userEventDTO.setEventReceivedTimestamp(new DateTime().toString());
        userEventDTO.setAllieId("ALLIEID");
        String json = "{\"allieId\": \"uiujx3xdx1\",\"timestamp\": \"2012-04-23T18:25:43.511Z\",\"neuraJson\": {\"identifier\": \"YourEventIdentifier_userIsOnTheWayHome\",\"userId\": \"uiujx3xdx1\",\"event\": {\"name\": \"userIsOnTheWayHome\",\"timestamp\": 1477342584,\"metadata\": {}}" +
                "}" +
                "}";
        Map jsonMap = mapper.readValue(json, HashMap.class);
        userEventDTO.setNeuraJson(jsonMap);
        return userEventDTO;
    }
    public static UserEventDTO createEventDTONoAllieId() throws IOException {
        UserEventDTO userEventDTO = new UserEventDTO();
        userEventDTO.setEventReceivedTimestamp(new DateTime().toString());
        String json = "{\"test\" : 1}";
        Map jsonMap = mapper.readValue(json, HashMap.class);
        userEventDTO.setNeuraJson(jsonMap);
        return userEventDTO;
    }
    public static UserEventDTO createEventDTONoTimestamp() throws IOException {
        UserEventDTO userEventDTO = new UserEventDTO();
        String json = "{\"test\" : 1}";
        Map jsonMap = mapper.readValue(json, HashMap.class);
        userEventDTO.setNeuraJson(jsonMap);
        return userEventDTO;
    }
    public static UserEventDTO createEventDTONoJson() throws IOException {
        UserEventDTO userEventDTO = new UserEventDTO();
        userEventDTO.setEventReceivedTimestamp(new DateTime().toString());
        userEventDTO.setAllieId("ALLIEID");
        return userEventDTO;
    }
    public static UserEvent createEvent() throws IOException {
        UserEvent userEvent = new UserEvent();
        userEvent.setEventReceivedTimestamp(new DateTime());
        userEvent.setAllieId("ALLIEID");
        String json = "{\"test\" : 1}";
        Map jsonMap = mapper.readValue(json, HashMap.class);
        userEvent.setNeuraJson(new BasicDBObject(jsonMap));
        return userEvent;
    }
}
