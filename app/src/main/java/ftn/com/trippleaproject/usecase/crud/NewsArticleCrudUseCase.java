package ftn.com.trippleaproject.usecase.crud;

import java.util.List;

import ftn.com.trippleaproject.domain.database.NewsArticle;
import ftn.com.trippleaproject.repository.remote.dao.NewsArticleRemoteDao;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class NewsArticleCrudUseCase {

    private final NewsArticleRemoteDao newsArticleRemoteDao;

    public NewsArticleCrudUseCase(NewsArticleRemoteDao newsArticleRemoteDao) {
        this.newsArticleRemoteDao = newsArticleRemoteDao;
    }

    public Flowable<List<NewsArticle>> read() {
        return Flowable.just(newsArticleRemoteDao.read().blockingGet()).subscribeOn(Schedulers.io());
    }
}
