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
        for (int i = 0; i < 5; i++) {
            newsArticles.add(new NewsArticle("title" + i));
        }

        return Flowable.just(newsArticles);
    }
}
