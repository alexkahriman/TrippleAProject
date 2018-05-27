package com.ftn.trippleaproject.repository.local;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import com.ftn.trippleaproject.repository.local.database.AppDatabase;
import com.ftn.trippleaproject.repository.local.database.dao.adapter.EventDaoAdapter;
import com.ftn.trippleaproject.repository.local.preference.AuthenticationLocalDaoImpl;
import com.ftn.trippleaproject.usecase.repository.dependency.local.AuthenticationLocalDao;
import com.ftn.trippleaproject.usecase.repository.dependency.local.EventLocalDao;
import com.ftn.trippleaproject.repository.local.database.dao.adapter.NewsArticleDaoAdapter;
import com.ftn.trippleaproject.usecase.repository.dependency.local.NewsArticleLocalDao;
import com.securepreferences.SecurePreferences;

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

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return new SecurePreferences(context);
    }

    @Provides
    @Singleton
    AuthenticationLocalDao provideAuthenticationLocalDao(SharedPreferences preferences) {
        return new AuthenticationLocalDaoImpl(preferences);
    }
}
