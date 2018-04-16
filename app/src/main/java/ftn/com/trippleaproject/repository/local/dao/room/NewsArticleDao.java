package ftn.com.trippleaproject.repository.local.dao.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import ftn.com.trippleaproject.repository.local.model.NewsArticleDb;

@Dao
public interface NewsArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long create(NewsArticleDb newsArticleDb);
}
