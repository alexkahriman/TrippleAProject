package com.ftn.trippleaproject.usecase.repository;

import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.usecase.business.GeoFenceUseCase;
import com.ftn.trippleaproject.usecase.repository.dependency.local.EventLocalDao;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.EventRemoteDao;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;

public class EventUseCase {

    private final EventRemoteDao eventRemoteDao;

    private final EventLocalDao eventLocalDao;

    private final GeoFenceUseCase geoFenceUseCase;

    public EventUseCase(EventRemoteDao eventRemoteDao, EventLocalDao eventLocalDao, GeoFenceUseCase geoFenceUseCase) {
        this.eventRemoteDao = eventRemoteDao;
        this.eventLocalDao = eventLocalDao;
        this.geoFenceUseCase = geoFenceUseCase;
    }

    private Flowable<List<Event>> sync() {
        return new Flowable<List<Event>>() {
            @Override
            protected void subscribeActual(Subscriber<? super List<Event>> subscriber) {
                final List<Event> events = eventRemoteDao.read().blockingGet();
                subscriber.onNext(events);
                checkAndCreateEvents(events);
                subscriber.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }

    public Observable create(Event event) {
        return new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                final Event eventResponse = eventRemoteDao.create(event).blockingGet();
                if (eventResponse != null) {
                    eventLocalDao.create(eventResponse);
                }
                observer.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }

    public Flowable<List<Event>> readAllLocalAndUpdate() {
        sync().blockingSubscribe();
        return eventLocalDao.read().subscribeOn(Schedulers.io());
    }

    public Flowable<List<Event>> readAllLocal() {
        return eventLocalDao.read().subscribeOn(Schedulers.io());
    }

    public Observable delete(List<Event> events) {
        return new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                eventLocalDao.delete(events);
                observer.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }

    public Observable delete(Event event) {
        return new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                eventLocalDao.delete(event);
                observer.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }

    private void checkAndCreateEvents(List<Event> events) {

        final List<Event> validEvents = new ArrayList<>();

        for (Event event : events) {
            if (checkEventEndDate(event)) {
                eventLocalDao.create(event);
                validEvents.add(event);
            }
        }

        geoFenceUseCase.addGeoFence(validEvents).subscribe();
    }

    private boolean checkEventEndDate(Event event) {
        return event.getEndDate().after(new Date());
    }
}
