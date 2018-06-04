package com.ftn.trippleaproject.usecase.repository.dependency.local;


import com.ftn.trippleaproject.domain.Event;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public interface EventLocalDao {

    void create(Event events);

    void create(List<Event> event);

    void update(Event event);

    void delete(Event event);

    void delete(List<Event> events);

    Flowable<List<Event>> read();

    Flowable<Event> read(long id);
}
