package com.ftn.trippleaproject.repository.remote.dao;

import android.util.Log;

import com.ftn.trippleaproject.usecase.repository.dependency.remote.AuthenticationRemoteDao;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Tasks;

import java.util.concurrent.TimeUnit;

public class AuthenticationRemoteDaoImpl implements AuthenticationRemoteDao {

    private static final String TAG = AuthenticationRemoteDao.class.getName();

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
            Log.w(TAG, e.getMessage());
            return "";
        }
    }

    @Override
    public void deleteToken() {
        try {
            Tasks.await(client.signOut(), 1, TimeUnit.SECONDS);
        } catch (Exception e) {
            Log.w(TAG, e.getMessage());
        }
    }
}
