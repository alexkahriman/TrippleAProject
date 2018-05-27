package com.ftn.trippleaproject.usecase;

import com.ftn.trippleaproject.usecase.business.DateTimeFormatterUseCase;
import com.ftn.trippleaproject.usecase.repository.AuthenticationUseCase;
import com.ftn.trippleaproject.usecase.repository.EventUseCase;
import com.ftn.trippleaproject.usecase.repository.NewsArticleUseCase;
import com.ftn.trippleaproject.usecase.repository.dependency.local.AuthenticationLocalDao;
import com.ftn.trippleaproject.usecase.repository.dependency.local.EventLocalDao;
import com.ftn.trippleaproject.usecase.repository.dependency.local.NewsArticleLocalDao;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.EventRemoteDao;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.NewsArticleRemoteDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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
    EventUseCase provideEventCrudUseCase(EventRemoteDao eventRemoteDao,
                                         EventLocalDao eventLocalDao) {
        return new EventUseCase(eventRemoteDao, eventLocalDao);
    }

    @Provides
    @Singleton
    AuthenticationUseCase provideAuthenticationUseCase(AuthenticationLocalDao authenticationLocalDao) {
        return new AuthenticationUseCase(authenticationLocalDao);
    }

    @Provides
    @Singleton
    DateTimeFormatterUseCase providesDateTimeFormatterUseCase() {
        return new DateTimeFormatterUseCase();
    }
}
