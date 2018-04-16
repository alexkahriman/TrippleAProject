package ftn.com.trippleaproject.usecase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ftn.com.trippleaproject.usecase.repository.EventCrudUseCase;
import ftn.com.trippleaproject.usecase.repository.NewsArticleUseCase;
import ftn.com.trippleaproject.usecase.repository.dependencies.local.EventLocalDao;
import ftn.com.trippleaproject.usecase.repository.dependencies.local.NewsArticleLocalDao;
import ftn.com.trippleaproject.usecase.repository.dependencies.remote.EventRemoteDao;
import ftn.com.trippleaproject.usecase.repository.dependencies.remote.NewsArticleRemoteDao;

@Module
public class UseCaseModule {

    @Provides
    @Singleton
    NewsArticleUseCase provideNewsArticleUseCase(NewsArticleRemoteDao newsArticleRemoteDao,
                                                 NewsArticleLocalDao newsArticleLocalDao) {
        return new NewsArticleUseCase(newsArticleRemoteDao, newsArticleLocalDao);
    }

    @Provides
    @Singleton
    EventCrudUseCase providesEventCrudUseCase(EventRemoteDao eventRemoteDao,
                                              EventLocalDao eventLocalDao) {
        return new EventCrudUseCase(eventRemoteDao, eventLocalDao);
    }
}
