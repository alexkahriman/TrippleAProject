package com.ftn.trippleaproject.usecase.repository.dependency.local;


import com.ftn.trippleaproject.domain.NewsArticle;

import java.util.List;

public interface NewsArticleLocalDao {

    void create(NewsArticle newsArticle);

    void delete(NewsArticle newsArticle);

    void delete(List<NewsArticle> newsArticleList);
}
