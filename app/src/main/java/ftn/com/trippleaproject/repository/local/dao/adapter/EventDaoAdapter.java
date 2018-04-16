package ftn.com.trippleaproject.repository.local.dao.adapter;

import java.util.List;

import ftn.com.trippleaproject.domain.Event;
import ftn.com.trippleaproject.repository.local.dao.room.EventDao;
import ftn.com.trippleaproject.repository.local.model.EventDb;
import ftn.com.trippleaproject.usecase.crud.repository.local.EventLocalDao;
import io.reactivex.Flowable;

public class EventDaoAdapter implements EventLocalDao {

    private final EventDao eventDao;

    public EventDaoAdapter(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public void create(Event event) {

        final EventDb eventDb = new EventDb(event.getTitle(), event.getDescription(), event.getDate(),
                event.getLatitude(), event.getLongitude());
        eventDao.create(eventDb);
    }

    @Override
    public void update(Event event) {

        final EventDb eventDb = new EventDb(event.getTitle(), event.getDescription(), event.getDate(),
                event.getLatitude(), event.getLongitude());
        eventDao.update(eventDb);
    }

    @Override
    public Flowable<List<EventDb>> read() {

        return eventDao.readAll();
    }
}
