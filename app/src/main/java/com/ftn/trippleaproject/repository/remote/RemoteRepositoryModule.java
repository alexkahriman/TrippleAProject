package com.ftn.trippleaproject.repository.remote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import com.ftn.trippleaproject.usecase.repository.dependencies.remote.NewsArticleRemoteDao;
import com.ftn.trippleaproject.repository.remote.dao.mock.NewsArticleRemoteDaoMock;

@Module
public class RemoteRepositoryModule {

    @Provides
    @Singleton
    NewsArticleRemoteDao provideNewsArticleRemoteDao() {
        return new NewsArticleRemoteDaoMock();
    }
}
