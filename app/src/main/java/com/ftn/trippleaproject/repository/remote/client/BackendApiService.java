package com.ftn.trippleaproject.repository.remote.client;

import com.ftn.trippleaproject.repository.remote.dto.NewsArticleDto;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface BackendApiService {

    @GET("news")
    Single<List<NewsArticleDto>> readNewsArticles();
}
