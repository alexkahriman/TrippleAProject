package ftn.com.trippleaproject.repository.remote.dao.mock;


import java.util.ArrayList;
import java.util.List;

import ftn.com.trippleaproject.domain.database.NewsArticle;
import ftn.com.trippleaproject.repository.remote.dao.NewsArticleRemoteDao;
import io.reactivex.Single;

public class NewsArticleRemoteDaoMock implements NewsArticleRemoteDao {

    @Override
    public Single<List<NewsArticle>> read() {

        final List<NewsArticle> newsArticles = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            newsArticles.add(new NewsArticle("Title " + i, "https://www.autocar.co.uk/sites/autocar.co.uk/files/audi-rs7_1.jpg"));
        }

        return Single.just(newsArticles);
    }
}
