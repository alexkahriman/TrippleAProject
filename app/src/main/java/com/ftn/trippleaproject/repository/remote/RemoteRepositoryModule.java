package com.ftn.trippleaproject.repository.remote;

import android.content.Context;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.repository.remote.client.BackendApiService;
import com.ftn.trippleaproject.repository.remote.client.HttpClient;
import com.ftn.trippleaproject.repository.remote.client.RetrofitClient;
import com.ftn.trippleaproject.repository.remote.dao.AuthenticationRemoteDaoImpl;
import com.ftn.trippleaproject.repository.remote.dao.EventRemoteDaoImpl;
import com.ftn.trippleaproject.repository.remote.dao.NewsArticleRemoteDaoImpl;
import com.ftn.trippleaproject.repository.remote.util.DateTimeUtility;
import com.ftn.trippleaproject.usecase.repository.AuthenticationUseCase;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.AuthenticationRemoteDao;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.EventRemoteDao;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.NewsArticleRemoteDao;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RemoteRepositoryModule {

    private final Context context;

    public RemoteRepositoryModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    HttpClient provideHttpClient(AuthenticationUseCase authenticationUseCase) {
        return new RetrofitClient(authenticationUseCase);
    }

    @Provides
    @Singleton
    BackendApiService provideBackendApiService(HttpClient httpClient) {
        return httpClient.getBackendApiService();
    }

    @Provides
    @Singleton
    DateTimeUtility providesDateTimeUtility() {
        return new DateTimeUtility();
    }

    @Provides
    @Singleton
    NewsArticleRemoteDao provideNewsArticleRemoteDao(BackendApiService backendApiService) {
        return new NewsArticleRemoteDaoImpl(backendApiService);
    }

    @Provides
    @Singleton
    EventRemoteDao providesEventRemoteDao(BackendApiService backendApiService, DateTimeUtility dateTimeUtility) {
        return new EventRemoteDaoImpl(backendApiService, dateTimeUtility);
    }

    @Provides
    @Singleton
    GoogleSignInClient providesGoogleSignInClient() {
        final GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(context.getString(R.string.server_client_id))
                        .requestEmail()
                        .build();

        return GoogleSignIn.getClient(context, gso);
    }

    @Provides
    @Singleton
    AuthenticationRemoteDao providesAuthenticationRemoteDao(GoogleSignInClient client) {
        return new AuthenticationRemoteDaoImpl(client);
    }
}
