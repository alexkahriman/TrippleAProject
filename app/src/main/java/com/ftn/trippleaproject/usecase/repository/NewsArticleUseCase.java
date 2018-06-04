package com.ftn.trippleaproject.usecase.repository;

import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.usecase.repository.dependency.local.NewsArticleLocalDao;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.NewsArticleRemoteDao;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;

import static com.ftn.trippleaproject.system.DeleteDataJobService.NUMBER_OF_NEWS_TO_KEEP;

public class NewsArticleUseCase {

    private static final int DAYS_AGO_THAT_MAKE_NEWS_RELEVANTE = -5;

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

    public Flowable<List<NewsArticle>> readAllLocal() {
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
                final List<NewsArticle> localNewsArticles = readAllLocal().blockingFirst();
                if (localNewsArticles.size() < NUMBER_OF_NEWS_TO_KEEP) {
                    newsArticleLocalDao.create(newsArticles);
                } else {
                    checkAndCreateNewsArticles(newsArticles);
                }
                observer.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }

    public Observable delete(NewsArticle newsArticle) {
        return new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                newsArticleLocalDao.delete(newsArticle);
                observer.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }

    public Observable delete(List<NewsArticle> newsArticles) {
        return new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                newsArticleLocalDao.delete(newsArticles);
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

    private void checkAndCreateNewsArticles(List<NewsArticle> newsArticles) {
        for (NewsArticle newsArticle : newsArticles) {
            if (checkNewsArticleDate(newsArticle)) {
                newsArticleLocalDao.create(newsArticle);
            }
        }
    }

    private boolean checkNewsArticleDate(NewsArticle newsArticle) {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, DAYS_AGO_THAT_MAKE_NEWS_RELEVANTE);
        return !newsArticle.getDate().before(calendar.getTime());
    }
}
