package com.ftn.trippleaproject.repository.local.database.dao.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ftn.trippleaproject.repository.local.database.model.NewsArticleDb;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface NewsArticleDao {

    @Query("SELECT * FROM news_article")
    Flowable<List<NewsArticleDb>> read();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long create(NewsArticleDb newsArticleDb);
}
