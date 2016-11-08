package com.allie.data.factory;

import com.allie.data.dto.NotificationReceiptDTO;
import com.allie.data.jpa.model.NotificationReceipt;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jacob.headlee on 11/1/2016.
 */
public class NotificationReceiptFactoryTest {
    NotificationReceiptFactory factory;
    NotificationReceipt notificationReceipt;
    NotificationReceiptDTO notificationReceiptDTO;

    String allieId;
    String type;
    String messageClass;
    String userSelectedOption;
    String trackingNumber;
    String timestamp;
    @Before
    public void setUp() {
        factory = new NotificationReceiptFactory();
        notificationReceiptDTO = new NotificationReceiptDTO();

        allieId = "allieId";
        type = "SOME_TYPE";
        messageClass = "MESSAGE_CLASS";
        userSelectedOption = "sure, why not?";
        trackingNumber = "tracking#######";
        timestamp = "2016-11-01T11:37:42.123Z";

        notificationReceiptDTO.setAllieId(allieId);
        notificationReceiptDTO.setType(type);
        notificationReceiptDTO.setMessageClass(messageClass);
        notificationReceiptDTO.setUserSelectedOption(userSelectedOption);
        notificationReceiptDTO.setTrackingNumber(trackingNumber);
        notificationReceiptDTO.setTimestamp(timestamp);

    }

    @Test
    public void testFactorySetsAllieId() {
        notificationReceipt = factory.createNotificationReceipt(notificationReceiptDTO);
        assertThat(notificationReceipt.getAllieId(), equalTo(allieId));
    }

    @Test
    public void testFactorySetsType() {
        notificationReceipt = factory.createNotificationReceipt(notificationReceiptDTO);
        assertThat(notificationReceipt.getType(), equalTo(type));
    }

    @Test
    public void testFactorySetsMessageClass() {
        notificationReceipt = factory.createNotificationReceipt(notificationReceiptDTO);
        assertThat(notificationReceipt.getMessageClass(), equalTo(messageClass));
    }

    @Test
    public void testFactorySetsUserSelectedOption() {
        notificationReceipt = factory.createNotificationReceipt(notificationReceiptDTO);
        assertThat(notificationReceipt.getUserSelectedOption(), equalTo(userSelectedOption));
    }

    @Test
    public void testFactorySetsTrackingNumber() {
        notificationReceipt = factory.createNotificationReceipt(notificationReceiptDTO);
        assertThat(notificationReceipt.getTrackingNumber(), equalTo(trackingNumber));
    }

    @Test
    public void testFactorySetsTimestamp() {
        notificationReceipt = factory.createNotificationReceipt(notificationReceiptDTO);
        assertThat(notificationReceipt.getTimestamp(), equalTo(new DateTime(timestamp)));
    }
}
