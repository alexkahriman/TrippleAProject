package com.ftn.trippleaproject.usecase.repository;

import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.usecase.repository.dependency.local.NewsArticleLocalDao;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.NewsArticleRemoteDao;

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
        sync().subscribe();
        return newsArticleLocalDao.read().subscribeOn(Schedulers.io());
    }

    private Observable sync() {
        return new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                final List<NewsArticle> newsArticles = newsArticleRemoteDao.read().blockingGet();
                for (NewsArticle newsArticle: newsArticles) {
                    newsArticleLocalDao.create(newsArticle);
                }
                observer.onComplete();
            }
        }.subscribeOn(Schedulers.io());

    }
}
