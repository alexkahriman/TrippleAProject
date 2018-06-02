package com.ftn.trippleaproject.usecase.repository.dependency.local;


import com.ftn.trippleaproject.domain.NewsArticle;

import java.util.List;

import io.reactivex.Flowable;

public interface NewsArticleLocalDao {

    Flowable<List<NewsArticle>> readAll();

    void create(NewsArticle newsArticle);

    void delete(NewsArticle newsArticle);

    void delete(List<NewsArticle> newsArticleList);
}
