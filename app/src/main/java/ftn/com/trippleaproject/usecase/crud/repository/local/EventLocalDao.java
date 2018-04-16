package ftn.com.trippleaproject.usecase.crud.repository.local;

import java.util.List;

import ftn.com.trippleaproject.domain.Event;
import ftn.com.trippleaproject.repository.local.model.EventDb;
import io.reactivex.Flowable;

public interface EventLocalDao {

    void create(Event event);

    void update(Event event);

    Flowable<List<EventDb>> read();
}
