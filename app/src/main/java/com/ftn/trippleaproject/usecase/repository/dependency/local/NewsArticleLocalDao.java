package com.ftn.trippleaproject.usecase.repository.dependency.local;


import com.ftn.trippleaproject.domain.NewsArticle;

import java.util.List;

import io.reactivex.Flowable;

public interface NewsArticleLocalDao {

    Flowable<List<NewsArticle>> read();

    Flowable<NewsArticle> read(long id);

    void create(List<NewsArticle> newsArticles);

    void update(NewsArticle newsArticle);
}
