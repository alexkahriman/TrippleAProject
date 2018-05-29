package com.ftn.trippleaproject.repository.local.database.dao.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ftn.trippleaproject.repository.local.database.model.NewsArticleDb;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface NewsArticleDao {

    @Query("SELECT * FROM news_article ORDER BY date DESC")
    Flowable<List<NewsArticleDb>> read();

    @Query("SELECT * FROM news_article WHERE id = :id LIMIT 1")
    Flowable<NewsArticleDb> read(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long create(NewsArticleDb newsArticleDb);

    @Update
    void update(NewsArticleDb newsArticleDb);
}
