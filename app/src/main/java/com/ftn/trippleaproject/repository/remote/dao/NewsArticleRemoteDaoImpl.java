package com.ftn.trippleaproject.repository.remote.dao;


import com.ftn.trippleaproject.domain.NewsArticle;
import com.ftn.trippleaproject.repository.remote.client.BackendApiService;
import com.ftn.trippleaproject.repository.remote.dto.NewsArticleDto;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.NewsArticleRemoteDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class NewsArticleRemoteDaoImpl implements NewsArticleRemoteDao {

    private final BackendApiService backendApiService;

    public NewsArticleRemoteDaoImpl(BackendApiService backendApiService) {
        this.backendApiService = backendApiService;
    }

    @Override
    public Single<List<NewsArticle>> read() {
        return backendApiService.readNewsArticles()
                .map(this::convertNewsArticleDtosToNewsArticles).subscribeOn(Schedulers.io());
    }

    private List<NewsArticle> convertNewsArticleDtosToNewsArticles(List<NewsArticleDto> newsArticleDtos) {

        final List<NewsArticle> newsArticles = new ArrayList<>();

        for (NewsArticleDto dto: newsArticleDtos) {
            // TODO: Adapt this fully without mocked data.
            final NewsArticle newsArticle = new NewsArticle(dto.getId(), dto.getTitle(), "https://www.autocar.co.uk/sites/autocar.co.uk/files/audi-rs7_1.jpg", null, new Date());
            newsArticles.add(newsArticle);
        }

        return newsArticles;
    }
}
