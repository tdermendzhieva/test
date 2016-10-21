package com.allie.data.dto;

import java.util.Map;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
public class MeetingDTO {
    public MeetingDTO(){}
    public String dateTime;

    public String getDateTime() {return this.dateTime;}
    public void setDateTime(String dateTime) {this.dateTime = dateTime;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeetingDTO that = (MeetingDTO) o;

        return getDateTime() != null ? getDateTime().equals(that.getDateTime()) : that.getDateTime() == null;

    }

    @Override
    public int hashCode() {
        return getDateTime() != null ? getDateTime().hashCode() : 0;
    }
}
