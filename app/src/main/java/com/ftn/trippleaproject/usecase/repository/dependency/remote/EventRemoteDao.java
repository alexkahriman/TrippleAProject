package com.ftn.trippleaproject.usecase.repository.dependency.remote;

import com.ftn.trippleaproject.domain.Event;

import java.util.List;

import io.reactivex.Single;

public interface EventRemoteDao {

    Single<List<Event>> read();

    Single<Event> create(Event event);

    Single<Event> patch(Event event);
}
