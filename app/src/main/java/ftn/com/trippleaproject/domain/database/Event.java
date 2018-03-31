package ftn.com.trippleaproject.domain.database;

/**
 * Event representation.
 */

public class Event {

    private String title;

    private String description;

    private int date;

    private long latitude;

    private long longitude;

    public Event() {
    }

    public Event(String title) {
        this.title = title;
    }

    public Event(String title, String description, int date, long latitude, long longitude) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
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
}
