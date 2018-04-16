package ftn.com.trippleaproject.repository.remote.dao.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ftn.com.trippleaproject.domain.Event;
import ftn.com.trippleaproject.usecase.crud.repository.remote.EventRemoteDao;
import io.reactivex.Single;

public class EventRemoteDaoMock implements EventRemoteDao {

    @Override
    public Single<List<Event>> read() {

        final List<Event> events = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            events.add(new Event(
                    "Title" + i,
                    "Description" + i,
                    new Date(),
                    2,
                    5)
            );
        }

        return Single.just(events);
    }
}
