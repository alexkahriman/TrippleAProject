package com.ftn.trippleaproject.repository.local.database.dao.adapter;


import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.repository.local.database.dao.room.NewsArticleDao;
import com.ftn.trippleaproject.repository.local.database.model.NewsArticleDb;
import com.ftn.trippleaproject.usecase.repository.dependency.local.NewsArticleLocalDao;

import java.util.List;

public class NewsArticleDaoAdapter implements NewsArticleLocalDao {

    private final NewsArticleDao newsArticleDao;

    public NewsArticleDaoAdapter(NewsArticleDao newsArticleDao) {
        this.newsArticleDao = newsArticleDao;
    }

    @Override
    public void create(NewsArticle newsArticle) {
        newsArticleDao.create(convertNewsArticleToNewsArticleDb(newsArticle));
    }

    @Override
    public void delete(NewsArticle newsArticle) {
        newsArticleDao.delete(convertNewsArticleToNewsArticleDb(newsArticle));
    }

    @Override
    public void delete(List<NewsArticle> newsArticles) {
        for (NewsArticle newsArticle : newsArticles) {
            newsArticleDao.delete(convertNewsArticleToNewsArticleDb(newsArticle));
        }
    }

    private NewsArticleDb convertNewsArticleToNewsArticleDb(NewsArticle newsArticle) {
        return new NewsArticleDb(newsArticle.getId(), newsArticle.getTitle(), newsArticle.getDate());
    }
}
