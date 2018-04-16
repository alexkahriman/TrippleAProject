package ftn.com.trippleaproject.repository.local.model;

import android.arch.persistence.room.Entity;

import java.util.Date;

@Entity(tableName = "news_article")
public class NewsArticleDb extends BaseModelDb {

    private String title;

    private Date date;

    public NewsArticleDb(long id, String title, Date date) {
        super(id);
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "NewsArticleDb{" +
                "title='" + title + '\'' +
                ", date=" + date +
                "} " + super.toString();
    }
}
