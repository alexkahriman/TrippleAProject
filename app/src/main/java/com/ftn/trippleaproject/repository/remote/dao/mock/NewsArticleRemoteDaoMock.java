package com.ftn.trippleaproject.repository.remote.dao.mock;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.domain.articlepart.ImageNewsArticlePart;
import com.ftn.trippleaproject.domain.articlepart.NewsArticlePart;
import com.ftn.trippleaproject.domain.articlepart.TextNewsArticlePart;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.NewsArticleRemoteDao;
import io.reactivex.Single;

public class NewsArticleRemoteDaoMock implements NewsArticleRemoteDao {

    @Override
    public Single<List<NewsArticle>> read() {

        final List<NewsArticle> newsArticles = new ArrayList<>();

        for (int i = 0; i < 100; i++) {

            final List<NewsArticlePart> parts = new ArrayList<>();
            parts.add(new ImageNewsArticlePart(2 * i, "https://www.autocar.co.uk/sites/autocar.co.uk/files/audi-rs7_1.jpg"));
            parts.add(new TextNewsArticlePart(2 * i + 1,
                    "The new Audi RS7 ladies and gentlemen. The new Audi RS7 ladies and gentlemen. The new Audi RS7 ladies and gentlemen. The new Audi RS7 ladies and gentlemen. The new Audi RS7 ladies and gentlemen."));
            parts.add(new ImageNewsArticlePart(2 * i, "https://i.ytimg.com/vi/2YWpQayFNMY/maxresdefault.jpg"));

            newsArticles.add(new NewsArticle(
                    i,
                    "Title " + i,
                    "https://www.autocar.co.uk/sites/autocar.co.uk/files/audi-rs7_1.jpg",
                    parts,
                    new Date()));
        }

        return Single.just(newsArticles);
    }
}
