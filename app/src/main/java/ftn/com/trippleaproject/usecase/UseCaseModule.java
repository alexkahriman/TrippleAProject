package ftn.com.trippleaproject.usecase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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
}
