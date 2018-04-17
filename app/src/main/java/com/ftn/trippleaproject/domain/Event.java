package com.ftn.trippleaproject.domain;

import java.io.Serializable;
import java.util.Date;

public class Event extends BaseModel implements Serializable {

    private String title;

    private String description;

    private Date date;

    private Location location;

    public Event(long id, String title, String description, Date date, Location location) {
        super(id);
        this.title = title;
        this.description = description;
        this.date = date;
        this.location = location;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public static class Location implements Serializable {

        public long latitude;

        public long longitude;

        public Location() {
        }

        public Location(long latitude, long longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public long getLatitude() {
            return latitude;
        }

        public void setLatitude(long latitude) {
            this.latitude = latitude;
        }

        public long getLongitude() {
            return longitude;
        }

        public void setLongitude(long longitude) {
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
