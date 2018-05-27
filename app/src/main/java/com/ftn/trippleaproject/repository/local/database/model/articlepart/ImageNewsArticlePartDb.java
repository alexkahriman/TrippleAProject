package com.ftn.trippleaproject.repository.local.database.model.articlepart;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.ftn.trippleaproject.repository.local.database.model.NewsArticleDb;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "image_news_article_part",
        foreignKeys = @ForeignKey(entity = NewsArticleDb.class,
        parentColumns = "id",
        childColumns = "news_article_id",
        onDelete = CASCADE))
public class ImageNewsArticlePartDb extends NewsArticlePartDb {

    @ColumnInfo(name = "news_article_id")
    private long newsArticleId;

    private String url;

    public ImageNewsArticlePartDb(String url) {
        this.url = url;
    }

    public long getNewsArticleId() {
        return newsArticleId;
    }

    public void setNewsArticleId(long newsArticleId) {
        this.newsArticleId = newsArticleId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ImageNewsArticlePartDb{" +
                "url='" + url + '\'' +
                "} " + super.toString();
    }
}
