package com.ftn.trippleaproject.repository.remote.dao.mock;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.usecase.repository.dependencies.remote.NewsArticleRemoteDao;
import io.reactivex.Single;

public class NewsArticleRemoteDaoMock implements NewsArticleRemoteDao {

    @Override
    public Single<List<NewsArticle>> read() {

        final List<NewsArticle> newsArticles = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            newsArticles.add(new NewsArticle(
                    i,
                    "Title " + i,
                    "https://www.autocar.co.uk/sites/autocar.co.uk/files/audi-rs7_1.jpg",
                    new ArrayList<>(),
                    new Date()));
        }

        return Single.just(newsArticles);
    }
}
