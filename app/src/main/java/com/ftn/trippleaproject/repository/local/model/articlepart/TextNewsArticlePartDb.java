package com.ftn.trippleaproject.repository.local.model.articlepart;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.ftn.trippleaproject.repository.local.model.NewsArticleDb;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "text_news_article_part",
        foreignKeys = @ForeignKey(entity = NewsArticleDb.class,
        parentColumns = "id",
        childColumns = "news_article_id",
        onDelete = CASCADE))
public class TextNewsArticlePartDb extends NewsArticlePartDb {

    @ColumnInfo(name = "news_article_id")
    private long newsArticleId;

    private String text;

    public TextNewsArticlePartDb(String text) {
        this.text = text;
    }

    public long getNewsArticleId() {
        return newsArticleId;
    }

    public void setNewsArticleId(long newsArticleId) {
        this.newsArticleId = newsArticleId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "TextNewsArticlePartDb{" +
                "text='" + text + '\'' +
                "} " + super.toString();
    }
}
