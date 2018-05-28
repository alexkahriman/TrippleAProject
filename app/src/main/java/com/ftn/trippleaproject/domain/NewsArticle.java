package com.ftn.trippleaproject.domain;

import java.io.Serializable;
import java.util.Date;


public class NewsArticle extends BaseModel implements Serializable {

    private String title;

    private String imageUrl;

    private String link;

    private String content;

    private Date date;

    public NewsArticle(long id, String title, String imageUrl, String link, Date date) {
        super(id);
        this.title = title;
        this.imageUrl = imageUrl;
        this.link = link;
        this.date = date;
    }

    public NewsArticle(long id, String title, String imageUrl, String link, String content, Date date) {
        super(id);
        this.title = title;
        this.imageUrl = imageUrl;
        this.link = link;
        this.content = content;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
                ", link='" + link + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                "} " + super.toString();
    }
}
