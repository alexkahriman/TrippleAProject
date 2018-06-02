package com.ftn.trippleaproject.usecase.repository;

import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.usecase.repository.dependency.local.EventLocalDao;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.EventRemoteDao;

import org.reactivestreams.Subscriber;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;

public class EventUseCase {

    private final EventRemoteDao eventRemoteDao;

    private final EventLocalDao eventLocalDao;

    public EventUseCase(EventRemoteDao eventRemoteDao, EventLocalDao eventLocalDao) {

        this.eventRemoteDao = eventRemoteDao;
        this.eventLocalDao = eventLocalDao;
    }

    public Flowable<List<Event>> read() {
        return new Flowable<List<Event>>() {
            @Override
            protected void subscribeActual(Subscriber<? super List<Event>> subscriber) {
                final List<Event> events = eventRemoteDao.read().blockingGet();
                subscriber.onNext(events);
                for (Event event : events) {
                    eventLocalDao.create(event);
                }
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
        read().blockingSubscribe();
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
}
