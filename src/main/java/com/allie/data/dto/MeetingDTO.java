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

    /**
     * Generated equals method to compare the sub properties instead of property references
     * @param o object to compare
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MeetingDTO that = (MeetingDTO) o;

        return getDateTime() != null ? getDateTime().equals(that.getDateTime()) : that.getDateTime() == null;

    }

    /**
     * Generated hashCode method to use sub properties instead of property references
     * @return
     */
    @Override
    public int hashCode() {
        return getDateTime() != null ? getDateTime().hashCode() : 0;
    }
}
