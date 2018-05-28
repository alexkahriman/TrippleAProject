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

    public EventDto(long id, String owner, String title, String description, Float lat, Float lon, String start, String end) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.start = start;
        this.end = end;
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
