package com.ftn.trippleaproject.repository.local;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.ftn.trippleaproject.repository.local.database.AppDatabase;
import com.ftn.trippleaproject.repository.local.database.dao.adapter.EventDaoAdapter;
import com.ftn.trippleaproject.repository.local.database.dao.adapter.NewsArticleDaoAdapter;
import com.ftn.trippleaproject.usecase.repository.dependency.local.EventLocalDao;
import com.ftn.trippleaproject.usecase.repository.dependency.local.NewsArticleLocalDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LocalRepositoryModule {

    private final Context context;

    public LocalRepositoryModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    AppDatabase provideDatabase() {
        return Room.databaseBuilder(context, AppDatabase.class, AppDatabase.DATABASE_NAME).fallbackToDestructiveMigration().build();
    }

    @Provides
    @Singleton
    NewsArticleLocalDao provideNewsArticleLocalDao(AppDatabase appDatabase) {
        return new NewsArticleDaoAdapter(appDatabase.newsArticleDao());
    }

    @Provides
    @Singleton
    EventLocalDao provideEventLocalDao(AppDatabase appDatabase) {
        return new EventDaoAdapter(appDatabase.eventDao());
    }
}
