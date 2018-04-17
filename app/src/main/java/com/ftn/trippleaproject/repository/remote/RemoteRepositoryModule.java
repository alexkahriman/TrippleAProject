package com.ftn.trippleaproject.repository.remote;

import com.ftn.trippleaproject.repository.remote.dao.mock.EventRemoteDaoMock;
import com.ftn.trippleaproject.repository.remote.dao.mock.NewsArticleRemoteDaoMock;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.EventRemoteDao;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.NewsArticleRemoteDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RemoteRepositoryModule {

    @Provides
    @Singleton
    NewsArticleRemoteDao provideNewsArticleRemoteDao() {
        return new NewsArticleRemoteDaoMock();
    }

    @Provides
    @Singleton
    EventRemoteDao providesEventRemoteDao() {
        return new EventRemoteDaoMock();
    }
}
