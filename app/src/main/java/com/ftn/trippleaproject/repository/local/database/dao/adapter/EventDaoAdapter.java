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
        eventDao.create(convertEventToEventDb(event));
    }

    @Override
    public void update(Event event) {
        eventDao.update(convertEventToEventDb(event));
    }

    @Override
    public void delete(Event event) {
        eventDao.delete(convertEventToEventDb(event));
    }

    @Override
    public void delete(List<Event> events) {
        for (Event event : events) {
            eventDao.delete(convertEventToEventDb(event));
        }
    }

    @Override
    public Flowable<List<Event>> read() {
        return eventDao.readAll().map(this::convertEventDbsToEvents);
    }

    private List<Event> convertEventDbsToEvents(List<EventDb> eventDbs) {
        final List<Event> events = new ArrayList<>();

        for (EventDb eventDb : eventDbs) {
            final Event event = new Event(eventDb.getId(), eventDb.getOwner(), eventDb.getTitle(), eventDb.getDescription(),
                    eventDb.getDate(), eventDb.getEndDate(),
                    new Event.Location(eventDb.getLatitude(), eventDb.getLongitude()));
            events.add(event);
        }

        return events;
    }

    private EventDb convertEventToEventDb(Event event) {
        return new EventDb(event.getId(), event.getOwner(), event.getTitle(), event.getDescription(),
                event.getDate(), event.getEndDate(), event.getLocation().getLatitude(), event.getLocation().getLongitude());
    }
}
