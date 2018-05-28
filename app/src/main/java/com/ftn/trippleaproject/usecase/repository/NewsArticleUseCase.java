package com.ftn.trippleaproject.usecase.repository;

import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.usecase.repository.dependency.local.NewsArticleLocalDao;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.NewsArticleRemoteDao;

import org.reactivestreams.Subscriber;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;

public class NewsArticleUseCase {

    private final NewsArticleRemoteDao newsArticleRemoteDao;

    private final NewsArticleLocalDao newsArticleLocalDao;

    public NewsArticleUseCase(NewsArticleRemoteDao newsArticleRemoteDao, NewsArticleLocalDao newsArticleLocalDao) {
        this.newsArticleRemoteDao = newsArticleRemoteDao;
        this.newsArticleLocalDao = newsArticleLocalDao;
    }

    public Flowable<List<NewsArticle>> read() {
        return new Flowable<List<NewsArticle>>() {
            @Override
            protected void subscribeActual(Subscriber<? super List<NewsArticle>> subscriber) {
                final List<NewsArticle> newsArticles = newsArticleRemoteDao.read().blockingGet();
                subscriber.onNext(newsArticles);
                for (NewsArticle newsArticle : newsArticles) {
                    newsArticleLocalDao.create(newsArticle);
                }
                subscriber.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }

    public void delete(NewsArticle newsArticle) {
        newsArticleLocalDao.delete(newsArticle);
    }

    public void delete(List<NewsArticle> newsArticles) {
        newsArticleLocalDao.delete(newsArticles);
    }
}
