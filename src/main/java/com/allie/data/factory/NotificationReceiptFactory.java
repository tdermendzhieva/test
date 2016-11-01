package com.allie.data.factory;

import com.allie.data.dto.NotificationReceiptDTO;
import com.allie.data.jpa.model.NotificationReceipt;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

/**
 * Created by jacob.headlee on 11/1/2016.
 */
@Component
public class NotificationReceiptFactory {
    public NotificationReceipt createNotificationReceipt(NotificationReceiptDTO notificationReceiptDTO) {
        NotificationReceipt notificationReceipt = new NotificationReceipt();

        notificationReceipt.setAllieId(notificationReceiptDTO.getAllieId());
        notificationReceipt.setUserSelectedOptions(notificationReceiptDTO.getUserSelectedOption());
        notificationReceipt.setType(notificationReceiptDTO.getType());
        notificationReceipt.setMessageClass(notificationReceiptDTO.getMessageClass());
        notificationReceipt.setTrackingNumber(notificationReceiptDTO.getTrackingNumber());
        notificationReceipt.setTimestamp(new DateTime(notificationReceiptDTO.getTimestamp()));

        return notificationReceipt;
    }
}
