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
        return newsArticleDao.read().map(this::convertToNewsArticles);
    }

    @Override
    public Flowable<NewsArticle> read(long id) {
        return newsArticleDao.read(id).map(this::convertToNewsArticle);
    }

    @Override
    public void create(List<NewsArticle> newsArticles) {
        newsArticleDao.create(convertToNewsArticleDbs(newsArticles).toArray(new NewsArticleDb[newsArticles.size()]));
    }

    @Override
    public void create(NewsArticle newsArticle) {
        newsArticleDao.create(convertToNewsArticleDb(newsArticle));
    }

    @Override
    public void update(NewsArticle newsArticle) {
        final NewsArticleDb newsArticleDb = convertToNewsArticleDb(newsArticle);
        newsArticleDao.update(newsArticleDb);
    }

    @Override
    public void delete(List<NewsArticle> newsArticles) {
        for (NewsArticle newsArticle : newsArticles) {
            newsArticleDao.delete(convertToNewsArticleDb(newsArticle));
        }
    }

    @Override
    public void delete(NewsArticle newsArticle) {
        newsArticleDao.delete(convertToNewsArticleDb(newsArticle));
    }

    private List<NewsArticleDb> convertToNewsArticleDbs(List<NewsArticle> newsArticles) {

        final List<NewsArticleDb> newsArticlesDbs = new ArrayList<>();

        for (NewsArticle newsArticle : newsArticles) {
            final NewsArticleDb newsArticleDb = convertToNewsArticleDb(newsArticle);
            newsArticlesDbs.add(newsArticleDb);
        }

        return newsArticlesDbs;
    }

    private List<NewsArticle> convertToNewsArticles(List<NewsArticleDb> newsArticleDbs) {

        final List<NewsArticle> newsArticles = new ArrayList<>();

        for (NewsArticleDb newsArticleDb : newsArticleDbs) {
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
