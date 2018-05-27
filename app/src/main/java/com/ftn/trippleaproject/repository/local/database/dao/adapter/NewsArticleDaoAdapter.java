package com.ftn.trippleaproject.repository.local.database.dao.adapter;


import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.repository.local.database.dao.room.NewsArticleDao;
import com.ftn.trippleaproject.repository.local.database.model.NewsArticleDb;
import com.ftn.trippleaproject.usecase.repository.dependency.local.NewsArticleLocalDao;

public class NewsArticleDaoAdapter implements NewsArticleLocalDao {

    private final NewsArticleDao newsArticleDao;

    public NewsArticleDaoAdapter(NewsArticleDao newsArticleDao) {
        this.newsArticleDao = newsArticleDao;
    }

    @Override
    public void create(NewsArticle newsArticle) {
        final NewsArticleDb newsArticleDb = new NewsArticleDb(newsArticle.getId(), newsArticle.getTitle(), newsArticle.getDate());
        newsArticleDao.create(newsArticleDb);
    }
}
