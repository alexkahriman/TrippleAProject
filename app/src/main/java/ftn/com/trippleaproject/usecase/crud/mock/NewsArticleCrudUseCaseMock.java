package ftn.com.trippleaproject.usecase.crud.mock;

import java.util.ArrayList;
import java.util.List;

import ftn.com.trippleaproject.domain.database.NewsArticle;
import ftn.com.trippleaproject.usecase.crud.NewsArticleCrudUseCase;
import io.reactivex.Flowable;

public class NewsArticleCrudUseCaseMock implements NewsArticleCrudUseCase {

    @Override
    public Flowable<List<NewsArticle>> read() {

        final List<NewsArticle> newsArticles = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            newsArticles.add(new NewsArticle("Title " + i, "https://www.autocar.co.uk/sites/autocar.co.uk/files/audi-rs7_1.jpg"));
        }

        return Flowable.just(newsArticles);
    }
}
