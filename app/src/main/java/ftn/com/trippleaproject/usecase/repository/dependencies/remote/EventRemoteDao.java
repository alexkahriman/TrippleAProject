package ftn.com.trippleaproject.usecase.repository.dependencies.remote;

import java.util.List;

import ftn.com.trippleaproject.domain.Event;
import io.reactivex.Single;

public interface EventRemoteDao {

    Single<List<Event>> read();
}
