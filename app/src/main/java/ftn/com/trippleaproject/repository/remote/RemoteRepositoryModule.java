package ftn.com.trippleaproject.repository.remote;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ftn.com.trippleaproject.repository.remote.dao.NewsArticleRemoteDao;
import ftn.com.trippleaproject.repository.remote.dao.mock.NewsArticleRemoteDaoMock;

@Module
public class RemoteRepositoryModule {

    @Provides
    @Singleton
    NewsArticleRemoteDao provideNewsArticleRemoteDao() {
        return new NewsArticleRemoteDaoMock();
    }
}
