package ftn.com.trippleaproject.repository.remote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ftn.com.trippleaproject.repository.remote.dao.mock.EventRemoteDaoMock;
import ftn.com.trippleaproject.repository.remote.dao.mock.NewsArticleRemoteDaoMock;
import ftn.com.trippleaproject.usecase.repository.dependencies.remote.EventRemoteDao;
import ftn.com.trippleaproject.usecase.repository.dependencies.remote.NewsArticleRemoteDao;

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
