package com.allie.data.jpa.model;

import org.joda.time.DateTime;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
public class Meeting {
    public Meeting() {};

    public String title;
    public DateTime dateTime;

    
    public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	public DateTime getDateTime() {return this.dateTime;}
    public void setDateTime(DateTime dateTime) {this.dateTime = dateTime;}

}
