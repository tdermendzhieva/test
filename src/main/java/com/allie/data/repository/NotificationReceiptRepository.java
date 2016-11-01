package com.allie.data.repository;

import com.allie.data.jpa.model.NotificationReceipt;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by jacob.headlee on 11/1/2016.
 */
public interface NotificationReceiptRepository extends MongoRepository<NotificationReceipt, String>{
}
