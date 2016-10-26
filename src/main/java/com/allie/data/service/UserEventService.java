package com.allie.data.service;

import com.allie.data.dto.UserEventDTO;
import com.allie.data.factory.UserEventFactory;
import com.allie.data.jpa.model.UserEvent;
import com.allie.data.repository.UserEventRepository;
import com.mongodb.MongoException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew.larsen on 10/24/2016.
 */
@Component
public class UserEventService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserEventRepository repository;
    private UserEventFactory factory;

    public UserEventService(UserEventRepository repository, UserEventFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    /**
     * Method to insert a user event to the Mongo collection
     * @param eventDTO UserEventDTO object containing AllieId, Timestamp, and Neura Json object as map
     * @return
     */
    public UserEvent insertEvent(UserEventDTO eventDTO) {
        UserEvent userEvent = factory.createUserEvent(eventDTO);
        if (
                userEvent.getAllieId() != null
                        && userEvent.getNeuraJson() != null
                        && userEvent.getEventReceivedTimestamp() != null
                ) {
        } else {
            logger.error("fields not provided " + userEvent);
            throw new IllegalArgumentException("Missing required field");

        }
        //insert user
        UserEvent returnEvent = repository.insert(userEvent);
        if(returnEvent == null || returnEvent.getId() == null){
            throw new MongoException("Failed to insert record");
        }
        return returnEvent;
    }

    public List<UserEventDTO> selectEvents(String allieId, String receivedDate) {
        if(allieId == null || allieId.trim().isEmpty()) {
            logger.debug("allieId null or empty, throwing IllegalArgumentException");
            throw new IllegalArgumentException("Get user events requires an allieId");
        }
        DateTime tempDate;
        DateTime startDate;
        DateTime endDate;
        try {
            tempDate = new DateTime(receivedDate);
        } catch (Exception e){
            logger.debug("Date was absent or malformed, assuming today");
            tempDate = new DateTime();
        }
        //Make sure we're just looking at a date not a specific time
        startDate = new DateTime(tempDate).withTimeAtStartOfDay();
        endDate = new DateTime(tempDate.plusDays(1)).withTimeAtStartOfDay();

        List<UserEvent> userEvents = repository.findUserEvents(allieId, startDate, endDate);

        //Transform the UserEvents into returnable DTOs
        List<UserEventDTO> toReturn = new ArrayList<>();
        for(UserEvent userEvent : userEvents) {
            toReturn.add(factory.createUserEventDTO(userEvent));
        }
        return toReturn;
    }
}
