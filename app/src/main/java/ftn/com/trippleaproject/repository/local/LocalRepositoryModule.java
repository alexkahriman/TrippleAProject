package ftn.com.trippleaproject.repository.local;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ftn.com.trippleaproject.repository.local.dao.adapter.EventDaoAdapter;
import ftn.com.trippleaproject.repository.local.dao.adapter.NewsArticleDaoAdapter;
import ftn.com.trippleaproject.usecase.crud.repository.local.EventLocalDao;
import ftn.com.trippleaproject.usecase.crud.repository.local.NewsArticleLocalDao;

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
    EventLocalDao providesEventLocalDao(AppDatabase appDatabase) {
        return new EventDaoAdapter(appDatabase.eventDao());
    }
}
