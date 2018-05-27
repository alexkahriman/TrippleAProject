package com.ftn.trippleaproject.usecase.repository;


import com.ftn.trippleaproject.usecase.repository.dependency.local.AuthenticationLocalDao;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;

public class AuthenticationUseCase {

    private AuthenticationLocalDao authenticationLocalDao;

    public AuthenticationUseCase(AuthenticationLocalDao authenticationLocalDao) {
        this.authenticationLocalDao = authenticationLocalDao;
    }

    public Observable writeToken(String token) {
        return new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                authenticationLocalDao.writeToken(token);
                observer.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }

    public Observable<String> readToken() {
        return new Observable<String>() {
            @Override
            protected void subscribeActual(Observer<? super String> observer) {
                observer.onNext(authenticationLocalDao.readToken());
                observer.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }
}
