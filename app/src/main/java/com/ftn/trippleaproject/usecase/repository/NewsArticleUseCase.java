package com.ftn.trippleaproject.usecase.repository;

import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.usecase.repository.dependency.local.NewsArticleLocalDao;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.NewsArticleRemoteDao;

import java.util.List;
import java.util.Objects;

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

    public Flowable<NewsArticle> read(long id) {
        return newsArticleLocalDao.read(id).map(this::addContentIfMissing).subscribeOn(Schedulers.io());
    }

    private Observable sync() {
        return new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                final List<NewsArticle> newsArticles = newsArticleRemoteDao.read().blockingGet();
                newsArticleLocalDao.create(newsArticles);
                observer.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }

    private NewsArticle addContentIfMissing(NewsArticle newsArticle) {

        if (newsArticle.getContent() != null && !Objects.equals(newsArticle.getContent(), "")) {
            return newsArticle;
        }

        final String content = newsArticleRemoteDao.readContent(newsArticle).blockingGet();
        newsArticle.setContent(content);
        newsArticleLocalDao.update(newsArticle);
        return newsArticle;
    }
}
