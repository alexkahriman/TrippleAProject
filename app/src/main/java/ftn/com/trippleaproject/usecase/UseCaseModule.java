package ftn.com.trippleaproject.usecase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ftn.com.trippleaproject.usecase.crud.NewsArticleCrudUseCase;
import ftn.com.trippleaproject.usecase.crud.mock.NewsArticleCrudUseCaseMock;

@Module
public class UseCaseModule {

    @Provides
    @Singleton
    NewsArticleCrudUseCase provideNewsArticleCrudUseCase() {
        return new NewsArticleCrudUseCaseMock();
    }
}
