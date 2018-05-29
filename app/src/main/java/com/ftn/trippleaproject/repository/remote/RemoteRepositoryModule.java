package com.ftn.trippleaproject.repository.remote;

import com.ftn.trippleaproject.repository.remote.client.BackendApiService;
import com.ftn.trippleaproject.repository.remote.client.HttpClient;
import com.ftn.trippleaproject.repository.remote.client.RetrofitClient;
import com.ftn.trippleaproject.repository.remote.dao.EventRemoteDaoImpl;
import com.ftn.trippleaproject.repository.remote.dao.NewsArticleRemoteDaoImpl;
import com.ftn.trippleaproject.repository.remote.util.DateTimeUtility;
import com.ftn.trippleaproject.usecase.repository.AuthenticationUseCase;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.EventRemoteDao;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.NewsArticleRemoteDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RemoteRepositoryModule {

    @Provides
    @Singleton
    HttpClient provideHttpClient(AuthenticationUseCase authenticationUseCase) {
        return new RetrofitClient(authenticationUseCase);
    }

    @Provides
    @Singleton
    BackendApiService provideBackendApiService(HttpClient httpClient) {
        return httpClient.getBackendApiService();
    }

    @Provides
    @Singleton
    DateTimeUtility providesDateTimeUtility() {
        return new DateTimeUtility();
    }

    @Provides
    @Singleton
    NewsArticleRemoteDao provideNewsArticleRemoteDao(BackendApiService backendApiService) {
        return new NewsArticleRemoteDaoImpl(backendApiService);
    }

    @Provides
    @Singleton
    EventRemoteDao providesEventRemoteDao(BackendApiService backendApiService, DateTimeUtility dateTimeUtility) {
        return new EventRemoteDaoImpl(backendApiService, dateTimeUtility);
    }
}
