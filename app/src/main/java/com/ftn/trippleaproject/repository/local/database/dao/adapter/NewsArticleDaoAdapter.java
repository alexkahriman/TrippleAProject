package com.ftn.trippleaproject.repository.local.database.dao.adapter;


import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.repository.local.database.dao.room.NewsArticleDao;
import com.ftn.trippleaproject.repository.local.database.model.NewsArticleDb;
import com.ftn.trippleaproject.usecase.repository.dependency.local.NewsArticleLocalDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

public class NewsArticleDaoAdapter implements NewsArticleLocalDao {

    private final NewsArticleDao newsArticleDao;

    public NewsArticleDaoAdapter(NewsArticleDao newsArticleDao) {
        this.newsArticleDao = newsArticleDao;
    }

    @Override
    public Flowable<List<NewsArticle>> readAll() {
        return Flowable.just(convertNewsArticlesDbToNewsArticle(newsArticleDao.readAll().blockingFirst()));
    }

    @Override
    public void create(NewsArticle newsArticle) {
        newsArticleDao.create(convertNewsArticleToNewsArticleDb(newsArticle));
    }

    @Override
    public void delete(NewsArticle newsArticle) {
        newsArticleDao.deleteById(newsArticle.getId());
    }

    @Override
    public void delete(List<NewsArticle> newsArticles) {
        for (NewsArticle newsArticle : newsArticles) {
            newsArticleDao.deleteById(newsArticle.getId());
        }
    }

    private NewsArticleDb convertNewsArticleToNewsArticleDb(NewsArticle newsArticle) {
        return new NewsArticleDb(newsArticle.getId(), newsArticle.getTitle(), newsArticle.getDate());
    }

    private List<NewsArticle> convertNewsArticlesDbToNewsArticle(List<NewsArticleDb> newsArticleDbs) {
        List<NewsArticle> newsArticles = new ArrayList<>();

        for (NewsArticleDb newsArticleDb : newsArticleDbs) {
            newsArticles.add(new NewsArticle(newsArticleDb.getId(), newsArticleDb.getDate()));
        }

        return newsArticles;
    }
}
