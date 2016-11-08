package com.allie.data.dto;

import com.allie.data.constant.ValidationMessage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by jacob.headlee on 11/4/2016.
 */
public class BrowserHistoryDTO {

    @NotNull(message = ValidationMessage.ALLIEID_NOT_NULL)
    private String allieId;
    @Size(max = 500, message = ValidationMessage.URL_MAX_SIZE)
    private String url;
    private String timestamp;

    @Override
    public String toString() {
        return "BrowserHistoryDTO{" +
                "allieId='" + allieId + '\'' +
                ", url='" + url + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    public String getAllieId() {return this.allieId;}
    public void setAllieId(String allieId) {this.allieId = allieId;}

    public String getUrl() {return this.url;}
    public void setUrl(String url) {this.url = url;}

    public String getTimestamp() {return this.timestamp;}
    public void setTimestamp(String timestamp) {this.timestamp = timestamp;}

}
