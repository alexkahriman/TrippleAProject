package com.ftn.trippleaproject.repository.local.database.dao.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.ftn.trippleaproject.repository.local.database.model.NewsArticleDb;

@Dao
public interface NewsArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long create(NewsArticleDb newsArticleDb);

    @Delete
    void delete(NewsArticleDb newsArticle);
}
