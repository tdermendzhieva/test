package com.allie.data.service;

import com.allie.data.dto.UserEventDTO;
import com.allie.data.factory.UserEventFactory;
import com.allie.data.jpa.model.UserEvent;
import com.allie.data.repository.UserEventRepository;
import com.mongodb.MongoException;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

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
            logger.info("fields not provided {}", userEvent);
            throw new IllegalArgumentException("Missing required field");

        }

        UserEvent returnEvent = repository.insert(userEvent);

        if(returnEvent == null || returnEvent.getId() == null){
            throw new MongoException("Failed to insert record");
        }
        return returnEvent;
    }

    /**
     * Method to retrieve events from the repository
     * @param allieId user whose events should be retrieved
     * @param receivedDate date of events to retrieve
     * @return
     */
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

        if(userEvents.size() > 0 ) {
            //Transform the UserEvents into returnable DTOs
            List<UserEventDTO> toReturn = new ArrayList<>();
            for (UserEvent userEvent : userEvents) {
                toReturn.add(factory.createUserEventDTO(userEvent));
            }
            return toReturn;
        } else {
            logger.debug("No user events found for allieId " + allieId + " at date " + tempDate.toString(ISODateTimeFormat.date()));
            throw new MissingResourceException("No user events found for allieId " + allieId + " at date " + tempDate.toString(ISODateTimeFormat.date()), UserEvent.class.getName(), allieId);
        }
    }
}
