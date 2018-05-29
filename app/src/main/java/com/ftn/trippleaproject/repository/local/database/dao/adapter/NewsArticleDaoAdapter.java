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
    public Flowable<List<NewsArticle>> read() {
        return newsArticleDao.read().map(this::convertNewsArticleDbsToNewsArticles);
    }

    @Override
    public Flowable<NewsArticle> read(long id) {
        return newsArticleDao.read(id).map(this::convertToNewsArticle);
    }

    @Override
    public void create(NewsArticle newsArticle) {
        final NewsArticleDb newsArticleDb = convertToNewsArticleDb(newsArticle);
        newsArticleDao.create(newsArticleDb);
    }

    @Override
    public void update(NewsArticle newsArticle) {
        final NewsArticleDb newsArticleDb = convertToNewsArticleDb(newsArticle);
        newsArticleDao.update(newsArticleDb);
    }

    private List<NewsArticle> convertNewsArticleDbsToNewsArticles(List<NewsArticleDb> newsArticleDbs) {

        final List<NewsArticle> newsArticles = new ArrayList<>();

        for (NewsArticleDb newsArticleDb: newsArticleDbs) {
            final NewsArticle newsArticle = convertToNewsArticle(newsArticleDb);
            newsArticles.add(newsArticle);
        }

        return newsArticles;
    }

    private NewsArticleDb convertToNewsArticleDb(NewsArticle newsArticle) {
        return new NewsArticleDb(
                newsArticle.getId(), newsArticle.getTitle(), newsArticle.getImageUrl(),
                newsArticle.getLink(), newsArticle.getContent(), newsArticle.getDate());
    }

    private NewsArticle convertToNewsArticle(NewsArticleDb newsArticleDb) {
        return new NewsArticle(
                newsArticleDb.getId(), newsArticleDb.getTitle(), newsArticleDb.getImageUrl(),
                newsArticleDb.getLink(), newsArticleDb.getContent(), newsArticleDb.getDate());
    }
}
