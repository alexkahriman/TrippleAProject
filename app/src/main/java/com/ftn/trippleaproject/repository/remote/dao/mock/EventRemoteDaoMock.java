package com.ftn.trippleaproject.repository.remote.dao.mock;

import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.EventRemoteDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;

public class EventRemoteDaoMock implements EventRemoteDao {

    @Override
    public Single<List<Event>> read() {

        final List<Event> events = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            events.add(new Event((long) i,
                    "Owner" + i,
                    "Title" + i,
                    "Description" + i,
                    new Date(),
                    new Date(),
                    new Event.Location(1, 2)));
        }

        return Single.just(events);
    }

    @Override
    public Single<Event> create(Event event) {
        return null;
    }
}
