package com.ftn.trippleaproject.repository.remote.client;

import com.ftn.trippleaproject.usecase.repository.AuthenticationUseCase;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient implements HttpClient {

    private static final String API_URL = "https://cryptic-caverns-12966.herokuapp.com/";

    private final BackendApiService service;

    public RetrofitClient(AuthenticationUseCase authenticationUseCase) {

        final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .addNetworkInterceptor(loggingInterceptor);

        okHttpBuilder.addInterceptor(chain -> chain.proceed(chain.request().newBuilder()
                .addHeader("Authorization", authenticationUseCase.readToken().blockingFirst()).build()));

        final OkHttpClient client = okHttpBuilder.build();

        final Retrofit retrofit = new Retrofit.Builder().baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        service = retrofit.create(BackendApiService.class);
    }

    public BackendApiService getService() {
        return service;
    }
}