package com.ftn.trippleaproject.usecase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import com.ftn.trippleaproject.usecase.repository.dependencies.remote.NewsArticleRemoteDao;
import com.ftn.trippleaproject.usecase.repository.NewsArticleUseCase;
import com.ftn.trippleaproject.usecase.repository.dependencies.local.NewsArticleLocalDao;

@Module
public class UseCaseModule {

    @Provides
    @Singleton
    NewsArticleUseCase provideNewsArticleUseCase(NewsArticleRemoteDao newsArticleRemoteDao,
                                                     NewsArticleLocalDao newsArticleLocalDao) {
        return new NewsArticleUseCase(newsArticleRemoteDao, newsArticleLocalDao);
    }
}
