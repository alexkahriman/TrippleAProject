package ftn.com.trippleaproject.usecase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ftn.com.trippleaproject.usecase.crud.EventCrudUseCase;
import ftn.com.trippleaproject.usecase.crud.repository.local.EventLocalDao;
import ftn.com.trippleaproject.usecase.crud.repository.remote.EventRemoteDao;
import ftn.com.trippleaproject.usecase.crud.repository.remote.NewsArticleRemoteDao;
import ftn.com.trippleaproject.usecase.crud.NewsArticleCrudUseCase;
import ftn.com.trippleaproject.usecase.crud.repository.local.NewsArticleLocalDao;

@Module
public class UseCaseModule {

    @Provides
    @Singleton
    NewsArticleCrudUseCase provideNewsArticleCrudUseCase(NewsArticleRemoteDao newsArticleRemoteDao,
                                                         NewsArticleLocalDao newsArticleLocalDao) {
        return new NewsArticleCrudUseCase(newsArticleRemoteDao, newsArticleLocalDao);
    }

    @Provides
    @Singleton
    EventCrudUseCase providesEventCrudUseCase(EventRemoteDao eventRemoteDao, EventLocalDao eventLocalDao) {
        return new EventCrudUseCase(eventRemoteDao, eventLocalDao);
    }
}
