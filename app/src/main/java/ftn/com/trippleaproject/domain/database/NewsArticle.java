package ftn.com.trippleaproject.domain.database;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import ftn.com.trippleaproject.domain.database.articlepart.NewsArticlePart;

public class NewsArticle implements Serializable {

    private String title;

    private String imageUrl;

    private List<NewsArticlePart> parts;

    private Date date;

    public NewsArticle(String title, String imageUrl, List<NewsArticlePart> parts, Date date) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.parts = parts;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<NewsArticlePart> getParts() {
        return parts;
    }

    public void setParts(List<NewsArticlePart> parts) {
        this.parts = parts;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
                "title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", parts=" + parts +
                ", date=" + date +
                '}';
    }
}
