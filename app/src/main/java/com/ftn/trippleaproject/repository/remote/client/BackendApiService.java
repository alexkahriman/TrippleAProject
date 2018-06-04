package com.ftn.trippleaproject.repository.remote.client;

import com.ftn.trippleaproject.repository.remote.dto.EventDto;
import com.ftn.trippleaproject.repository.remote.dto.NewsArticleDto;

import java.util.List;

import io.reactivex.Single;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface BackendApiService {

    @GET("news")
    Single<List<NewsArticleDto>> readNewsArticles();

    @GET
    Single<ResponseBody> readNewsArticleContent(@Url String url);

    @GET("events")
    Single<List<EventDto>> readEvents();

    @POST("events")
    Single<EventDto> createEvent(@Body EventDto event);

    @PATCH("events")
    Single<EventDto> patchEvent(@Body EventDto event);
}
