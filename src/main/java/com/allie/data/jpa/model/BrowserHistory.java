package com.allie.data.jpa.model;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by jacob.headlee on 11/4/2016.
 */
@Document(collection = "BrowserHistoryRecords")
public class BrowserHistory {
    @Id
    private String dbId;
    private String allieId;
    private String url;
    private DateTime timestamp;

    public String getDbId() {return this.dbId;}
    public void setDbId(String dbId) {this.dbId = dbId;}

    public String getAllieId() {return this.allieId;}
    public void setAllieId(String allieId) {this.allieId = allieId;}

    public String getUrl() {return this.url;}
    public void setUrl(String url) {this.url = url;}

    public DateTime getTimestamp() {return this.timestamp;}
    public void setTimestamp(DateTime timestamp) {this.timestamp = timestamp;}
}
