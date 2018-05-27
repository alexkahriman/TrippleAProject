package com.ftn.trippleaproject.repository.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.ftn.trippleaproject.repository.local.database.converter.DateTypeConverter;
import com.ftn.trippleaproject.repository.local.database.dao.room.EventDao;
import com.ftn.trippleaproject.repository.local.database.dao.room.NewsArticleDao;
import com.ftn.trippleaproject.repository.local.database.model.EventDb;
import com.ftn.trippleaproject.repository.local.database.model.NewsArticleDb;
import com.ftn.trippleaproject.repository.local.database.model.articlepart.ImageNewsArticlePartDb;
import com.ftn.trippleaproject.repository.local.database.model.articlepart.TextNewsArticlePartDb;

@Database(entities = {
        NewsArticleDb.class,
        EventDb.class,
        TextNewsArticlePartDb.class,
        ImageNewsArticlePartDb.class},
        version = 1,
        exportSchema = false)

@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "tap_db";

    public abstract NewsArticleDao newsArticleDao();

    public abstract EventDao eventDao();
}

