package com.ftn.trippleaproject.usecase.repository.dependency.local;


public interface AuthenticationLocalDao {

    void writeToken(String token);

    String readToken();
}
