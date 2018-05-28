package com.ftn.trippleaproject.repository.remote.client;

import com.ftn.trippleaproject.repository.remote.dto.NewsArticleDto;

import java.util.List;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface BackendApiService {

    @GET("news")
    Single<List<NewsArticleDto>> readNewsArticles();

    @GET
    Single<ResponseBody> readNewsArticleContent(@Url String url);
}
