package com.ftn.trippleaproject.repository.remote.dto;

import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.repository.remote.dao.EventRemoteDaoImpl;

public class EventDto {

    private long id;

    private String owner;

    private String title;

    private String description;

    private Float lat;

    private Float lon;

    private String start;

    private String end;

    public EventDto() {
    }

    public EventDto(Event event) {
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.lat = (float) event.getLocation().getLatitude();
        this.lon = (float) event.getLocation().getLongitude();
        this.start = EventRemoteDaoImpl.convertToMongoDate(event.getDate());
        this.end = EventRemoteDaoImpl.convertToMongoDate(event.getDate());
    }

    public long getId() {
        return id;
    }

    public String getOwner() {
        return owner;
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

    public Float getLat() {
        return lat;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLon() {
        return lon;
    }

    public void setLon(Float lon) {
        this.lon = lon;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
