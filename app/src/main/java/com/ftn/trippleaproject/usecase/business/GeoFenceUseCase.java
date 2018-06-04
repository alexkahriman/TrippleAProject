package com.ftn.trippleaproject.usecase.business;


import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.usecase.business.dependency.GeoFenceProvider;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;

public class GeoFenceUseCase {

    private final GeoFenceProvider geoFenceProvider;

    public GeoFenceUseCase(GeoFenceProvider geoFenceProvider) {
        this.geoFenceProvider = geoFenceProvider;
    }

    public Observable addGeoFence(Event event) {
        return new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                geoFenceProvider.addGeoFence(event);
                observer.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }
}
