package ftn.com.trippleaproject.repository.local.dao.adapter;

import java.util.ArrayList;
import java.util.List;

import ftn.com.trippleaproject.domain.Event;
import ftn.com.trippleaproject.repository.local.dao.room.EventDao;
import ftn.com.trippleaproject.repository.local.model.EventDb;
import ftn.com.trippleaproject.usecase.repository.dependencies.local.EventLocalDao;
import io.reactivex.Flowable;

public class EventDaoAdapter implements EventLocalDao {

    private final EventDao eventDao;

    public EventDaoAdapter(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public void create(Event event) {

        final EventDb eventDb = new EventDb(event.getTitle(), event.getDescription(), event.getDate(),
                event.getLocation().getLatitude(), event.getLocation().getLongitude());
        eventDao.create(eventDb);
    }

    @Override
    public void update(Event event) {

        final EventDb eventDb = new EventDb(event.getTitle(), event.getDescription(), event.getDate(),
                event.getLocation().getLatitude(), event.getLocation().getLongitude());
        eventDao.update(eventDb);
    }

    @Override
    public Flowable<List<Event>> read() {

        return eventDao.readAll().map(eventDbs -> {
            List<Event> events = new ArrayList<>();
            for (EventDb eventDb : eventDbs) {
                events.add(new Event(eventDb.getId(),
                        eventDb.getTitle(),
                        eventDb.getDescription(),
                        eventDb.getDate(),
                        new Event.Location(eventDb.getLatitude(), eventDb.getLongitude())));
            }

            return events;
        });

    }
}
