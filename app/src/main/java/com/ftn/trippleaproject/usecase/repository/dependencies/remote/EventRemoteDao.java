package com.ftn.trippleaproject.usecase.repository.dependencies.remote;

import com.ftn.trippleaproject.domain.Event;

import java.util.List;

import io.reactivex.Single;

public interface EventRemoteDao {

    Single<List<Event>> read();
}
