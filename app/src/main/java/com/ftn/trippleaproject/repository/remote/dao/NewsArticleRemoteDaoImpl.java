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
import okhttp3.ResponseBody;

public class NewsArticleRemoteDaoImpl implements NewsArticleRemoteDao {

    private final BackendApiService backendApiService;

    public NewsArticleRemoteDaoImpl(BackendApiService backendApiService) {
        this.backendApiService = backendApiService;
    }

    @Override
    public Single<List<NewsArticle>> read() {
        return backendApiService.readNewsArticles().onErrorReturn(throwable -> new ArrayList<>())
                .map(this::convertToNewsArticles).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<String> readContent(NewsArticle newsArticle) {
        String content = fetchContent(newsArticle);
        if (content == null) {
            content = "";
        }
        return Single.just(content).subscribeOn(Schedulers.io());
    }

    private List<NewsArticle> convertToNewsArticles(List<NewsArticleDto> newsArticleDtos) {

        final List<NewsArticle> newsArticles = new ArrayList<>();

        for (NewsArticleDto dto: newsArticleDtos) {
            final NewsArticle newsArticle = new NewsArticle(dto.getId(), dto.getTitle(), dto.getImage(), dto.getLink(), new Date());
            newsArticles.add(newsArticle);
        }

        return newsArticles;
    }

    private String fetchContent(NewsArticle newsArticle) {
        try {
            final ResponseBody responseBody = backendApiService.readNewsArticleContent(newsArticle.getLink()).blockingGet();
            return responseBody.string();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
