package com.allie.data.service;

import com.allie.data.dto.NotificationReceiptDTO;
import com.allie.data.factory.NotificationReceiptFactory;
import com.allie.data.jpa.model.NotificationReceipt;
import com.allie.data.repository.NotificationReceiptRepository;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Created by jacob.headlee on 11/1/2016.
 */
public class NotificationReceiptServiceTest {
    private NotificationReceiptService service;
    private NotificationReceiptFactory factory;
    private NotificationReceiptRepository repository;

    private NotificationReceipt notificationReceipt;
    private NotificationReceiptDTO notificationReceiptDTO;

    private String allieId;
    private String messageClass;
    private String timestamp;
    private String trackingNumber;
    private String type;
    private String userSelectedOption;

    @Before
    public void setUp() {
        factory = mock(NotificationReceiptFactory.class);
        repository = mock(NotificationReceiptRepository.class);
        service = new NotificationReceiptService(repository, factory);


        notificationReceiptDTO = new NotificationReceiptDTO();
        notificationReceipt = new NotificationReceipt();
        allieId = "allieId";
        messageClass = "MESSAGE_CLASS";
        timestamp = "2016-11-01T10:10:10.111Z";
        trackingNumber = "tracking#########";
        type = "SOME_TYPE";
        userSelectedOption = "sure why not?";

        notificationReceiptDTO.setAllieId(allieId);
        notificationReceiptDTO.setMessageClass(messageClass);
        notificationReceiptDTO.setTimestamp(timestamp);
        notificationReceiptDTO.setTrackingNumber(trackingNumber);
        notificationReceiptDTO.setType(type);
        notificationReceiptDTO.setUserSelectedOption(userSelectedOption);

        notificationReceipt.setAllieId(allieId);
        notificationReceipt.setMessageClass(messageClass);
        notificationReceipt.setTimestamp(new DateTime(timestamp));
        notificationReceipt.setTrackingNumber(trackingNumber);
        notificationReceipt.setType(type);
        notificationReceipt.setUserSelectedOption(userSelectedOption);
    }

    @Test
    public void testInsertNotificationReceipt() {
        given(factory.createNotificationReceipt(notificationReceiptDTO)).willReturn(notificationReceipt);
        given(repository.insert(notificationReceipt)).willReturn(notificationReceipt);

        assertThat(service.insertNotificationReceipt(notificationReceiptDTO), equalTo(notificationReceipt));
    }

    @Test
    public void testInsertNotificationReceiptNoAllieId() {
        notificationReceipt.setAllieId(null);
        notificationReceiptDTO.setAllieId(null);


        given(factory.createNotificationReceipt(notificationReceiptDTO)).willReturn(notificationReceipt);
        given(repository.insert(notificationReceipt)).willReturn(notificationReceipt);

        try {
            service.insertNotificationReceipt(notificationReceiptDTO);
        } catch (IllegalArgumentException e) {
            assert(true);
            return;
        }
        fail("didn't throw correct exception");
    }
}
