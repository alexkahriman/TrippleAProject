package ftn.com.trippleaproject.repository.local.dao.adapter;


import ftn.com.trippleaproject.domain.NewsArticle;
import ftn.com.trippleaproject.repository.local.dao.room.NewsArticleDao;
import ftn.com.trippleaproject.repository.local.model.NewsArticleDb;
import ftn.com.trippleaproject.usecase.crud.repository.local.NewsArticleLocalDao;

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
