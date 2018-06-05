package com.ftn.trippleaproject.usecase.business;


import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.usecase.business.dependency.GeoFenceProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;

public class GeoFenceUseCase {

    private final GeoFenceProvider geoFenceProvider;

    public GeoFenceUseCase(GeoFenceProvider geoFenceProvider) {
        this.geoFenceProvider = geoFenceProvider;
    }

    public Observable addGeoFence(List<Event> events) {
        return new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                geoFenceProvider.addGeoFences(events);
                observer.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }
}
