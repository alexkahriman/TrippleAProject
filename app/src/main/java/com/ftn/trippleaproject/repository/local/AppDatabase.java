package com.ftn.trippleaproject.repository.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.ftn.trippleaproject.repository.local.converter.DateTypeConverter;
import com.ftn.trippleaproject.repository.local.dao.room.NewsArticleDao;
import com.ftn.trippleaproject.repository.local.model.NewsArticleDb;
import com.ftn.trippleaproject.repository.local.model.articlepart.ImageNewsArticlePartDb;
import com.ftn.trippleaproject.repository.local.model.articlepart.TextNewsArticlePartDb;

@Database(entities = {
        NewsArticleDb.class,
        TextNewsArticlePartDb.class,
        ImageNewsArticlePartDb.class},
        version = 1,
        exportSchema = false)

@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "tap_db";

    public abstract NewsArticleDao newsArticleDao();
}

