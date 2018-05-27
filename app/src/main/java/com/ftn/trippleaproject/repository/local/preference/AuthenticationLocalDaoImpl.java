package com.ftn.trippleaproject.repository.local.preference;


import android.content.SharedPreferences;

import com.ftn.trippleaproject.usecase.repository.dependency.local.AuthenticationLocalDao;

public class AuthenticationLocalDaoImpl implements AuthenticationLocalDao {

    private final SharedPreferences preferences;

    public AuthenticationLocalDaoImpl(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public void writeToken(String token) {
        preferences.edit().putString("token", token).apply();
    }

    @Override
    public String readToken() {
        return preferences.getString("token", null);
    }
}
