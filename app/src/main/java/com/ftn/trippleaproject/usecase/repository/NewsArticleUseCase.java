package com.ftn.trippleaproject.usecase.repository;

import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.usecase.repository.dependency.local.NewsArticleLocalDao;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.NewsArticleRemoteDao;

import org.reactivestreams.Subscriber;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
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
                List<NewsArticle> localNewsArticles = readAllLocal().blockingFirst();
                for (NewsArticle newsArticle : newsArticles) {
                    if (localNewsArticles.size() > 50) {
                        if (checkNewsArticleDate(newsArticle)) {
                            newsArticleLocalDao.create(newsArticle);
                        }
                    } else {
                        newsArticleLocalDao.create(newsArticle);
                    }
                }
                subscriber.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }

    public Flowable<List<NewsArticle>> readAllLocal() {
        return newsArticleLocalDao.readAll().subscribeOn(Schedulers.io());
    }

    public void delete(NewsArticle newsArticle) {
        newsArticleLocalDao.delete(newsArticle);
    }

    public void delete(List<NewsArticle> newsArticles) {
        newsArticleLocalDao.delete(newsArticles);
    }

    private boolean checkNewsArticleDate(NewsArticle newsArticle) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, 5);
        return !newsArticle.getDate().before(calendar.getTime());
    }
}
