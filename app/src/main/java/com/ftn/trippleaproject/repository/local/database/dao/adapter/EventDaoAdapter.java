package com.ftn.trippleaproject.repository.local.database.dao.adapter;

import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.repository.local.database.dao.room.EventDao;
import com.ftn.trippleaproject.repository.local.database.model.EventDb;
import com.ftn.trippleaproject.usecase.repository.dependency.local.EventLocalDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

public class EventDaoAdapter implements EventLocalDao {

    private final EventDao eventDao;

    public EventDaoAdapter(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public void create(Event event) {
        final EventDb eventDb = new EventDb(event.getId(), event.getTitle(), event.getDescription(), event.getDate(),
                event.getEndDate(), event.getLocation().getLatitude(), event.getLocation().getLongitude());
        eventDao.create(eventDb);
    }

    @Override
    public void update(Event event) {
        final EventDb eventDb = new EventDb(event.getId(), event.getTitle(), event.getDescription(), event.getDate(),
                event.getEndDate(), event.getLocation().getLatitude(), event.getLocation().getLongitude());
        eventDao.update(eventDb);
    }

    @Override
    public Flowable<List<Event>> read() {
        return eventDao.readAll().map(eventDbs -> {
            List<Event> events = new ArrayList<>();
            for (EventDb eventDb : eventDbs) {
                events.add(new Event(eventDb.getId(), eventDb.getTitle(), eventDb.getDescription(), eventDb.getDate(),
                        eventDb.getEndDate(), new Event.Location(eventDb.getLatitude(), eventDb.getLongitude())));
            }

            return events;
        });
    }
}
