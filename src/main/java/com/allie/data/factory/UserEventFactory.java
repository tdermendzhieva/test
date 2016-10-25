package com.allie.data.factory;

import com.allie.data.dto.UserEventDTO;
import com.allie.data.jpa.model.UserEvent;
import com.mongodb.BasicDBObject;
import org.springframework.stereotype.Component;

/**
 * Created by andrew.larsen on 10/24/2016.
 */
@Component
public class UserEventFactory {

    public UserEvent createUserEvent(UserEventDTO userEventDTO) {
        UserEvent userEvent = new UserEvent();
        userEvent.setAllieId(userEventDTO.getAllieId());
        userEvent.setEventReceivedTimestamp(userEventDTO.getEventReceivedTimestamp());
        if(userEventDTO.getNeuraJson() != null) {
            userEvent.setNeuraJson(new BasicDBObject(userEventDTO.getNeuraJson()));
        }
        return userEvent;
    }
}
