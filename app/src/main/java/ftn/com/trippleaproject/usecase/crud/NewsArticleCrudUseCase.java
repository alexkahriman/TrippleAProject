package ftn.com.trippleaproject.usecase.crud;

import org.reactivestreams.Subscriber;

import java.util.List;

import ftn.com.trippleaproject.domain.NewsArticle;
import ftn.com.trippleaproject.usecase.crud.repository.remote.NewsArticleRemoteDao;
import ftn.com.trippleaproject.usecase.crud.repository.local.NewsArticleLocalDao;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class NewsArticleCrudUseCase {

    private final NewsArticleRemoteDao newsArticleRemoteDao;

    private final NewsArticleLocalDao newsArticleLocalDao;

    public NewsArticleCrudUseCase(NewsArticleRemoteDao newsArticleRemoteDao, NewsArticleLocalDao newsArticleLocalDao) {
        this.newsArticleRemoteDao = newsArticleRemoteDao;
        this.newsArticleLocalDao = newsArticleLocalDao;
    }

    public Flowable<List<NewsArticle>> read() {
        return new Flowable<List<NewsArticle>>() {
            @Override
            protected void subscribeActual(Subscriber<? super List<NewsArticle>> subscriber) {
                final List<NewsArticle> newsArticles = newsArticleRemoteDao.read().blockingGet();
                for (NewsArticle newsArticle: newsArticles) {
                    newsArticleLocalDao.create(newsArticle);
                }
                subscriber.onNext(newsArticles);
                subscriber.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }
}
