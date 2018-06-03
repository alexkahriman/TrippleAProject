package com.ftn.trippleaproject.usecase.repository;


import com.ftn.trippleaproject.usecase.repository.dependency.remote.AuthenticationRemoteDao;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;

public class AuthenticationUseCase {

    private AuthenticationRemoteDao authenticationRemoteDao;

    public AuthenticationUseCase(AuthenticationRemoteDao authenticationRemoteDao) {
        this.authenticationRemoteDao = authenticationRemoteDao;
    }

    public Observable<String> readToken() {
        return new Observable<String>() {
            @Override
            protected void subscribeActual(Observer<? super String> observer) {
                observer.onNext(authenticationRemoteDao.readToken());
                observer.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }
}
