package com.allie.data.dto;

/**
 * Created by jacob.headlee on 11/4/2016.
 */
public class BrowserHistoryDTO {
    private String allieId;
    private String url;
    private String timestamp;

    public String getAllieId() {return this.allieId;}
    public void setAllieId(String allieId) {this.allieId = allieId;}

    public String getUrl() {return this.url;}
    public void setUrl(String url) {this.url = url;}

    public String getTimestamp() {return this.timestamp;}
    public void setTimestamp(String timestamp) {this.timestamp = timestamp;}
}
