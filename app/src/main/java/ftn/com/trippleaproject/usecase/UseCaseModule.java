package ftn.com.trippleaproject.usecase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ftn.com.trippleaproject.repository.remote.dao.NewsArticleRemoteDao;
import ftn.com.trippleaproject.usecase.crud.NewsArticleCrudUseCase;

@Module
public class UseCaseModule {

    @Provides
    @Singleton
    NewsArticleCrudUseCase provideNewsArticleCrudUseCase(NewsArticleRemoteDao newsArticleRemoteDao) {
        return new NewsArticleCrudUseCase(newsArticleRemoteDao);
    }
}
