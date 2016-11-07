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

    @Override
    public String toString() {
        return "BrowserHistory{" +
                "dbId='" + dbId + '\'' +
                ", allieId='" + allieId + '\'' +
                ", url='" + url + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BrowserHistory that = (BrowserHistory) o;

        if (getDbId() != null ? !getDbId().equals(that.getDbId()) : that.getDbId() != null) return false;
        if (getAllieId() != null ? !getAllieId().equals(that.getAllieId()) : that.getAllieId() != null) return false;
        if (getUrl() != null ? !getUrl().equals(that.getUrl()) : that.getUrl() != null) return false;
        return getTimestamp() != null ? getTimestamp().equals(that.getTimestamp()) : that.getTimestamp() == null;

    }

    @Override
    public int hashCode() {
        int result = getDbId() != null ? getDbId().hashCode() : 0;
        result = 31 * result + (getAllieId() != null ? getAllieId().hashCode() : 0);
        result = 31 * result + (getUrl() != null ? getUrl().hashCode() : 0);
        result = 31 * result + (getTimestamp() != null ? getTimestamp().hashCode() : 0);
        return result;
    }
}
