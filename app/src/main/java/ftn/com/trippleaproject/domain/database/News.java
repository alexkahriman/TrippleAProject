package ftn.com.trippleaproject.domain.database;

/**
 * News representation.
 */

public class News {

    private String title;

    private String description;

    private String shortDescription;

    private int date;

    public News() {
    }

    public News(String title) {
        this.title = title;
    }

    public News(String title, String description, String shortDescription, int date) {
        this.title = title;
        this.description = description;
        this.shortDescription = shortDescription;
        this.date = date;
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
