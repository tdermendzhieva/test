package com.allie.data.controller;

import com.allie.data.dto.NotificationReceiptDTO;
import com.allie.data.jpa.model.NotificationReceipt;
import com.allie.data.service.NotificationReceiptService;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.allie.data.util.TestUtil.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jacob.headlee on 11/1/2016.
 */

@RunWith(SpringRunner.class)
@WebMvcTest(NotificationReceiptController.class)
public class NotificationReceiptControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private NotificationReceiptService service;

    private NotificationReceiptDTO notificationReceiptDTO;
    private NotificationReceipt notificationReceipt;
    private String allieId;
    private String messageClass;
    private String timestamp;
    private String trackingNumber;
    private String type;
    private String userSelectedOption;

    @Before
    public void setup(){
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
    public void testPostNotificationReceiptReturnsAccepted() throws Exception {
        given(this.service.insertNotificationReceipt(notificationReceiptDTO))
                .willReturn(notificationReceipt);

        this.mvc.perform(post("/allie-data/v1/notificationReceipts")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-allie-correlation-id", "corr-id")
                .header("x-allie-request-id", "req-id")
                .content(asJsonString(notificationReceiptDTO)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testPostNotificationReceiptNoBody() throws Exception {

        given(this.service.insertNotificationReceipt(notificationReceiptDTO))
                .willReturn(notificationReceipt);


        this.mvc.perform(post("/allie-data/v1/notificationReceipts")
                .contentType(MediaType.APPLICATION_JSON)
                .header("x-allie-correlation-id", "corr-id")
                .header("x-allie-request-id", "req-id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostNotificationReceiptNoHeaders() throws Exception {

        given(this.service.insertNotificationReceipt(notificationReceiptDTO))
                .willReturn(notificationReceipt);


        this.mvc.perform(post("/allie-data/v1/notificationReceiptDTO")
                .content(asJsonString(notificationReceiptDTO)))
                .andExpect(status().isBadRequest());
    }
}
