package ftn.com.trippleaproject.usecase.repository;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;
import java.util.List;

import ftn.com.trippleaproject.domain.Event;
import ftn.com.trippleaproject.repository.local.model.EventDb;
import ftn.com.trippleaproject.usecase.repository.dependencies.local.EventLocalDao;
import ftn.com.trippleaproject.usecase.repository.dependencies.remote.EventRemoteDao;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.schedulers.Schedulers;

public class EventCrudUseCase {

    private final EventRemoteDao eventRemoteDao;

    private final EventLocalDao eventLocalDao;

    public EventCrudUseCase(EventRemoteDao eventRemoteDao, EventLocalDao eventLocalDao) {

        this.eventRemoteDao = eventRemoteDao;
        this.eventLocalDao = eventLocalDao;
    }

    public Flowable<List<Event>> read() {

        return new Flowable<List<Event>>() {
            @Override
            protected void subscribeActual(Subscriber<? super List<Event>> subscriber) {

                final List<Event> events = eventRemoteDao.read().blockingGet();
                for (Event event: events) {
                    eventLocalDao.create(event);
                }
                subscriber.onNext(events);
                subscriber.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }

    public Observable create(Event event) {

        return new Observable() {
            @Override
            protected void subscribeActual(Observer observer) {
                eventLocalDao.create(event);
                observer.onComplete();
            }
        }.subscribeOn(Schedulers.io());
    }

    public Flowable<List<Event>> readAllLocal() {

        return eventLocalDao.read().map(eventDbs -> {
            List<Event> events = new ArrayList<>();
            for (EventDb eventDb: eventDbs) {
                events.add(new Event(eventDb.getTitle(),
                        eventDb.getDescription(),
                        eventDb.getDate(),
                        eventDb.getLatitude(),
                        eventDb.getLongitude()));
            }

            return events;
        });
    }
}
