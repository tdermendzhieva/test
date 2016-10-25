package com.allie.data.jpa.model;

import com.mongodb.DBObject;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by andrew.larsen on 10/24/2016.
 */
@Document(collection="RawNeuraEvents")
public class UserEvent{

    @Id
    private String id;
    private String allieId;
    private DateTime eventReceivedTimestamp;
    private DBObject neuraJson;

    public String getAllieId() {
        return allieId;
    }

    public void setAllieId(String allieId) {
        this.allieId = allieId;
    }

    public DateTime getEventReceivedTimestamp() {
        return eventReceivedTimestamp;
    }

    public void setEventReceivedTimestamp(DateTime eventReceivedTimestamp) {
        this.eventReceivedTimestamp = eventReceivedTimestamp;
    }

    public DBObject getNeuraJson() {
        return neuraJson;
    }

    public void setNeuraJson(DBObject neuraJson) {
        this.neuraJson = neuraJson;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}