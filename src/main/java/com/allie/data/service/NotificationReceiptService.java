package com.allie.data.service;

import com.allie.data.dto.NotificationReceiptDTO;
import com.allie.data.factory.NotificationReceiptFactory;
import com.allie.data.jpa.model.NotificationReceipt;
import com.allie.data.repository.NotificationReceiptRepository;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by jacob.headlee on 11/1/2016.
 */
@Component
public class NotificationReceiptService {
    NotificationReceiptRepository repository;
    NotificationReceiptFactory factory;

    public NotificationReceiptService(NotificationReceiptRepository repository, NotificationReceiptFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    public NotificationReceipt insertNotificationReceipt(NotificationReceiptDTO notificationReceiptDTO) {
        NotificationReceipt notificationReceipt = factory.createNotificationReceipt(notificationReceiptDTO);
        if(notificationReceipt.getAllieId() != null && !notificationReceipt.getAllieId().trim().isEmpty()) {
            return repository.insert(notificationReceipt);
        } else {
            throw new IllegalArgumentException("missing required allieId");
        }
    }
}
