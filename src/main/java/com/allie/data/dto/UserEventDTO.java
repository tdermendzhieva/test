package com.allie.data.dto;

import java.util.Map;

/**
 * Created by andrew.larsen on 10/24/2016.
 */
public class UserEventDTO {

    private String allieId;
    private String eventReceivedTimestamp;
    private Map neuraJson;

    @Override
    public String toString() {
        return "UserEventDTO{" +
                "allieId='" + allieId + '\'' +
                ", eventReceivedTimestamp='" + eventReceivedTimestamp + '\'' +
                ", neuraJson=" + neuraJson +
                '}';
    }

    public String getAllieId() {
        return allieId;
    }
    public void setAllieId(String allieId) {
        this.allieId = allieId;
    }

    public Map getNeuraJson() {
        return neuraJson;
    }
    public void setNeuraJson(Map neuraJson) {
        this.neuraJson = neuraJson;
    }

    public String getEventReceivedTimestamp() {
        return eventReceivedTimestamp;
    }
    public void setEventReceivedTimestamp(String eventReceivedTimestamp) {
        this.eventReceivedTimestamp = eventReceivedTimestamp;
    }
}
