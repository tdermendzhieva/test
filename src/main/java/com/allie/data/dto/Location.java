package com.allie.data.dto;

/**
 * Created by andrew.larsen on 10/17/2016.
 */
public class Location {
    private double longitude;
    private double latitude;

    public Location() {}


    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    /**
     * Generated equals method to compare the sub properties instead of property references
     * @param o object to compare
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (Double.compare(location.getLongitude(), getLongitude()) != 0) return false;
        return Double.compare(location.getLatitude(), getLatitude()) == 0;

    }

    /**
     * Generated hashCode method to use sub properties instead of property references
     * @return
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getLongitude());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getLatitude());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
