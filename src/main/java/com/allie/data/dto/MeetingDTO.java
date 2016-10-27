package com.allie.data.dto;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
public class MeetingDTO {
    public MeetingDTO(){}
    public String title;
    public String dateTime;

    
    public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	public String getDateTime() {return this.dateTime;}
    public void setDateTime(String dateTime) {this.dateTime = dateTime;}

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MeetingDTO other = (MeetingDTO) obj;
		if (dateTime == null) {
			if (other.dateTime != null)
				return false;
		} else if (!dateTime.equals(other.dateTime))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateTime == null) ? 0 : dateTime.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
    
	@Override
	public String toString() {
		return "MeetingDTO [title=" + title + ", dateTime=" + dateTime + "]";
	}
	
}
