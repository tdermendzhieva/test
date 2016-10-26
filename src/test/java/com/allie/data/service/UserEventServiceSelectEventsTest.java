package com.allie.data.service;

import com.allie.data.dto.UserEventDTO;
import com.allie.data.factory.UserEventFactory;
import com.allie.data.jpa.model.UserEvent;
import com.allie.data.repository.UserEventRepository;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Created by jacob.headlee on 10/26/2016.
 */
public class UserEventServiceSelectEventsTest {
    UserEventFactory factory;
    UserEventRepository repository;
    UserEventService service;
    UserEventDTO userEventDTO;
    UserEvent userEvent;

    List<UserEventDTO> eventDTOs;
    List<UserEvent> events;

    @Before
    public void setup() throws IOException {
        events = new ArrayList<>();
        eventDTOs = new ArrayList<>();

        factory = mock(UserEventFactory.class);
        repository = mock(UserEventRepository.class);
        service = new UserEventService(repository, factory);

        userEventDTO = new UserEventDTO();
        userEvent = new UserEvent();
        userEventDTO.setAllieId("test");
        userEventDTO.setEventReceivedTimestamp("");
        userEvent.setAllieId("test");
        userEvent.setEventReceivedTimestamp(new DateTime());

        for(int i = 0; i < 10; i++) {
            events.add(userEvent);
            eventDTOs.add(userEventDTO);
        }

    }

    @Test
    public void testSelectEvents() throws Exception {
        DateTime startDate = new DateTime().withDate(2016, 10, 26).withTimeAtStartOfDay();
        DateTime endDate = new DateTime().withDate(2016, 10, 27).withTimeAtStartOfDay();
        given(repository.findUserEvents("test", startDate, endDate)).willReturn(events);
        given(factory.createUserEventDTO(userEvent)).willReturn(userEventDTO);

        assertThat(service.selectEvents("test", "2016-10-26"), equalTo(eventDTOs));
    }

    @Test
    public void testSelectEventsGarbageDate() throws Exception {
        DateTime startDate = new DateTime().withTimeAtStartOfDay();
        DateTime endDate = new DateTime().plusDays(1).withTimeAtStartOfDay();
        given(repository.findUserEvents("test", startDate, endDate)).willReturn(events);
        given(factory.createUserEventDTO(userEvent)).willReturn(userEventDTO);

        assertThat(service.selectEvents("test", "garbage"), equalTo(eventDTOs));
    }

    @Test
    public void testSelectEventEmptyAllieId() throws Exception {
        try{
            service.selectEvents("", "garbage");
        } catch (Exception e) {
            assertThat(e.getClass(), equalTo(IllegalArgumentException.class));
            return;
        }

        assertThat("exception was thrown", true, equalTo(false));
    }

}
