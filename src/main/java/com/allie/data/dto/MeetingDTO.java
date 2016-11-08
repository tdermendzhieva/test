package com.allie.data.dto;

/**
 * Created by jacob.headlee on 10/20/2016.
 */
public class MeetingDTO {

    private String title;
    private String dateTime;

	@Override
	public String toString() {
		return "MeetingDTO{" +
				"title='" + title + '\'' +
				", dateTime='" + dateTime + '\'' +
				'}';
	}

	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}

	public String getDateTime() {return this.dateTime;}
    public void setDateTime(String dateTime) {this.dateTime = dateTime;}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MeetingDTO that = (MeetingDTO) o;

		if (getTitle() != null ? !getTitle().equals(that.getTitle()) : that.getTitle() != null) return false;
		return getDateTime() != null ? getDateTime().equals(that.getDateTime()) : that.getDateTime() == null;

	}

	@Override
	public int hashCode() {
		int result = getTitle() != null ? getTitle().hashCode() : 0;
		result = 31 * result + (getDateTime() != null ? getDateTime().hashCode() : 0);
		return result;
	}
}
