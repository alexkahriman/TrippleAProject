package com.ftn.trippleaproject.repository.remote.dao;

import com.ftn.trippleaproject.usecase.repository.dependency.remote.AuthenticationRemoteDao;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;

public class AuthenticationRemoteDaoImpl implements AuthenticationRemoteDao {

    private final GoogleSignInClient client;

    public AuthenticationRemoteDaoImpl(GoogleSignInClient client) {
        this.client = client;
    }

    @Override
    public String readToken() {
        try {
            final GoogleSignInAccount account = client.silentSignIn().getResult(ApiException.class);
            return account.getIdToken();
        } catch (ApiException e) {
            return "";
        }
    }
}
