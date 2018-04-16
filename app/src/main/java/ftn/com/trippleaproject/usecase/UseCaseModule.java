package ftn.com.trippleaproject.usecase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ftn.com.trippleaproject.usecase.repository.dependencies.remote.NewsArticleRemoteDao;
import ftn.com.trippleaproject.usecase.repository.NewsArticleUseCase;
import ftn.com.trippleaproject.usecase.repository.dependencies.local.NewsArticleLocalDao;

@Module
public class UseCaseModule {

    @Provides
    @Singleton
    NewsArticleUseCase provideNewsArticleCrudUseCase(NewsArticleRemoteDao newsArticleRemoteDao,
                                                     NewsArticleLocalDao newsArticleLocalDao) {
        return new NewsArticleUseCase(newsArticleRemoteDao, newsArticleLocalDao);
    }
}
