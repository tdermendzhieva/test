package com.allie.data.jpa.model;

import org.joda.time.DateTime;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
public class Meeting {
    public Meeting() {};

    public DateTime dateTime;

    public DateTime getDateTime() {return this.dateTime;}
    public void setDateTime(DateTime dateTime) {this.dateTime = dateTime;}

}
