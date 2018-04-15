package ftn.com.trippleaproject.repository.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import ftn.com.trippleaproject.repository.local.converter.DateTypeConverter;
import ftn.com.trippleaproject.repository.local.dao.room.NewsArticleDao;
import ftn.com.trippleaproject.repository.local.model.NewsArticleDb;
import ftn.com.trippleaproject.repository.local.model.articlepart.ImageNewsArticlePartDb;
import ftn.com.trippleaproject.repository.local.model.articlepart.TextNewsArticlePartDb;

@Database(entities = {
        NewsArticleDb.class,
        TextNewsArticlePartDb.class,
        ImageNewsArticlePartDb.class},
        version = 1,
        exportSchema = false)

@TypeConverters({DateTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "db";

    public abstract NewsArticleDao newsArticleDao();
}

