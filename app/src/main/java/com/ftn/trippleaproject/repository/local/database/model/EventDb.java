package com.ftn.trippleaproject.repository.local.database.model;

import android.arch.persistence.room.Entity;

import java.util.Date;

@Entity(tableName = "event")
public class EventDb extends BaseModelDb {

    private String owner;

    private String title;

    private String description;

    private Date date;

    private Date endDate;

    private double latitude;

    private double longitude;

    public EventDb(long id, String owner, String title, String description, Date date, Date endDate, double latitude, double longitude) {
        super(id);
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.date = date;
        this.endDate = endDate;
        this.latitude = latitude;
        this.longitude = longitude;
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
        return "EventDb{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
