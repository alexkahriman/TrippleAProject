package com.ftn.trippleaproject.domain;

import java.io.Serializable;
import java.util.Date;

public class Event extends BaseModel implements Serializable {

    private String owner;

    private String title;

    private String description;

    private Date date;

    private Date endDate;

    private Location location;

    public Event(long id, String title, String description, Date date, Date endDate, Location location) {
        super(id);
        this.title = title;
        this.description = description;
        this.date = date;
        this.endDate = endDate;
        this.location = location;
    }

    public Event(long id, String owner, String title, String description, Date date, Date endDate, Location location) {
        super(id);
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.date = date;
        this.endDate = endDate;
        this.location = location;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public static class Location implements Serializable {

        public double latitude;

        public double longitude;

        public Location() {
        }

        public Location(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "latitude=" + latitude +
                    ", longitude=" + longitude +
                    '}';
        }
    }
}
