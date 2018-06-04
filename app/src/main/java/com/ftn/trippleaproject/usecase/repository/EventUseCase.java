package com.ftn.trippleaproject.usecase.repository;

import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.usecase.repository.dependency.local.EventLocalDao;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.EventRemoteDao;

import org.reactivestreams.Subscriber;

import java.util.Date;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;

import static com.ftn.trippleaproject.system.DeleteDataJobService.NUMBER_OF_EVENTS_TO_KEEP;

public class EventUseCase {

    private final EventRemoteDao eventRemoteDao;

    private final EventLocalDao eventLocalDao;

    public EventUseCase(EventRemoteDao eventRemoteDao, EventLocalDao eventLocalDao) {
        this.eventRemoteDao = eventRemoteDao;
        this.eventLocalDao = eventLocalDao;
    }

    private Flowable<List<Event>> sync() {
        return new Flowable<List<Event>>() {
            @Override
            protected void subscribeActual(Subscriber<? super List<Event>> subscriber) {
                final List<Event> events = eventRemoteDao.read().blockingGet();
                final List<Event> localEvents = readAllLocal().blockingFirst();
                subscriber.onNext(events);

                if (localEvents.size() < NUMBER_OF_EVENTS_TO_KEEP) {
                    eventLocalDao.create(events);
                } else {
                    checkAndCreateEvents(events);
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
        for (Event event : events) {
            if (checkEventEndDate(event)) {
                eventLocalDao.create(event);
            }
        }
    }

    private boolean checkEventEndDate(Event event) {
        return event.getEndDate().after(new Date());
    }
}
