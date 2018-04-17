package com.ftn.trippleaproject.usecase.repository.dependency.local;


import com.ftn.trippleaproject.domain.Event;

import java.util.List;

import io.reactivex.Flowable;

public interface EventLocalDao {

    void create(Event event);

    void update(Event event);

    Flowable<List<Event>> read();
}
