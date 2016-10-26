package com.allie.data.service;

import com.allie.data.dto.UserEventDTO;
import com.allie.data.factory.UserEventFactory;
import com.allie.data.jpa.model.UserEvent;
import com.allie.data.repository.UserEventRepository;
import com.mongodb.MongoException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.allie.data.util.UserEventTestUtils.createEvent;
import static com.allie.data.util.UserEventTestUtils.createEventDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Created by andrew.larsen on 10/25/2016.
 */
public class UserEventServiceInsertEventTest {

    private UserEventFactory factory;
    private UserEventRepository repository;
    private UserEventService service;

    UserEventDTO userEventDTO;
    UserEvent userEvent;
    UserEvent returnEvent;
    private String mongoID = "aksldhf23423";
    @Before
    public void setup() throws IOException {
        factory = mock(UserEventFactory.class);
        repository = mock(UserEventRepository.class);
        service = new UserEventService(repository, factory);
        userEventDTO = createEventDTO();
        userEvent = createEvent();
        returnEvent = createEvent();
        returnEvent.setId(mongoID);
    }
    @Test
    public void testInsertEventOk() throws Exception {
        given(factory.createUserEvent(userEventDTO)).willReturn(userEvent);
        given(repository.insert(userEvent)).willReturn(returnEvent);

        assertThat(service.insertEvent(userEventDTO)).isEqualTo(returnEvent);

    }
    @Test
    public void testInsertEventBadUserDTONoAllieID() throws Exception {
        userEvent.setAllieId(null);
        given(factory.createUserEvent(userEventDTO)).willReturn(userEvent);
        try {
            service.insertEvent(userEventDTO);
            fail("Should have thrown illegalargumentexception");
        }
        catch (IllegalArgumentException e){
            assert(true);
        }
        catch (Exception e){
            fail("threw incorrect exception");
        }
    }

    @Test
    public void testInsertEventBadUserDTONoJSON() throws Exception {
        userEvent.setNeuraJson(null);
        given(factory.createUserEvent(userEventDTO)).willReturn(userEvent);

        try {
            service.insertEvent(userEventDTO);
            fail("Should have thrown illegalargumentexception");
        }
        catch (IllegalArgumentException e){
            assert(true);
        }
        catch (Exception e){
            fail("threw incorrect exception");
        }
    }

    @Test
    public void testInsertEventBadUserDTONoTimestamp() throws Exception {
        userEvent.setEventReceivedTimestamp(null);
        given(factory.createUserEvent(userEventDTO)).willReturn(userEvent);

        try {
            service.insertEvent(userEventDTO);
            fail("Should have thrown illegalargumentexception");
        }
        catch (IllegalArgumentException e){
            assert(true);
        }
        catch (Exception e){
            fail("threw incorrect exception");
        }
    }
    @Test
    public void testInsertEventInsertFailsWithNull() throws Exception {
        given(factory.createUserEvent(userEventDTO)).willReturn(userEvent);
        given(repository.insert(userEvent)).willReturn(null);

        try {
            service.insertEvent(userEventDTO);
            fail("Should have thrown illegalargumentexception");
        }
        catch (MongoException e){
            assert(true);
        }
        catch (Exception e){
            fail("threw incorrect exception");
        }
    }
    @Test
    public void testInsertEventInsertFailsWithNoId() throws Exception {
        userEvent.setId(null);
        given(factory.createUserEvent(userEventDTO)).willReturn(userEvent);
        given(repository.insert(userEvent)).willReturn(userEvent);

        try {
            service.insertEvent(userEventDTO);
            fail("Should have thrown illegalargumentexception");
        }
        catch (MongoException e){
            assert(true);
        }
        catch (Exception e){
            fail("threw incorrect exception");
        }
    }
    @Test
    public void testInsertEventInsertFailsWithNojson() throws Exception {
        userEvent.setNeuraJson(null);
        given(factory.createUserEvent(userEventDTO)).willReturn(userEvent);
        given(repository.insert(userEvent)).willReturn(userEvent);

        try {
            service.insertEvent(userEventDTO);
            fail("Should have thrown illegalargumentexception");
        }
        catch (IllegalArgumentException e){
            assert(true);
        }
        catch (Exception e){
            fail("threw incorrect exception");
        }
    }

}