package com.allie.data.controller;

import com.allie.data.dto.NotificationReceiptDTO;
import com.allie.data.service.NotificationReceiptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by jacob.headlee on 11/1/2016.
 */

@RestController
@RequestMapping(value = "/allie-data/v1")
@Api(value = "receiptNotifications", description = "Endpoints for receipt notifications")
public class NotificationReceiptController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    NotificationReceiptService service;

    @ApiOperation(value = "Persistence service call to store a notification receipt",
        notes = "The service will asynchronously store all data in backend persistence structure.  If the request successfully reaches the service, a 202 (accepted) HttpStatus will " +
                "be returned")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "The service successfully received the request"),
            @ApiResponse(code = 400, message = "The request was malformed to the point that no information can be stored"),
            @ApiResponse(code = 500, message = "There was an unspecified server error."),
            @ApiResponse(code = 503, message = "There was an issue connecting to a downstream system")
    })
    @RequestMapping(value="/notificationReceipts", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void postNotificationReceipts(@RequestBody NotificationReceiptDTO notificationReceipt,
                                         @RequestHeader("x-allie-request-id") String requestId,
                                         @RequestHeader("x-allie-correlation-id") String correlationId) {
        Thread thread = new Thread(() -> {service.insertNotificationReceipt(notificationReceipt);});
        thread.setName("insert-notification-receipt");
        thread.start();
        return;
    }

}
