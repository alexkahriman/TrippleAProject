package com.ftn.trippleaproject.repository.remote.dao;

import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.repository.remote.client.BackendApiService;
import com.ftn.trippleaproject.repository.remote.dto.EventDto;
import com.ftn.trippleaproject.repository.remote.util.DateTimeUtility;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.EventRemoteDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class EventRemoteDaoImpl implements EventRemoteDao {

    private final BackendApiService backendApiService;

    private final DateTimeUtility dateTimeUtility;

    public EventRemoteDaoImpl(BackendApiService backendApiService, DateTimeUtility dateTimeUtility) {
        this.backendApiService = backendApiService;
        this.dateTimeUtility = dateTimeUtility;
    }

    @Override
    public Single<List<Event>> read() {
        return backendApiService.readEvents().onErrorReturn(throwable -> new ArrayList<>())
                .map(this::convertEventDtosToEvents).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Event> create(Event event) {
        return backendApiService.createEvent(convertEventToEventDto(event)).onErrorReturn(throwable -> null)
                .map(this::convertEventDtoToEvent).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Event> patch(Event event) {
        return backendApiService.patchEvent(convertEventToEventDto(event)).onErrorReturn(throwable -> null)
                .map(this::convertEventDtoToEvent).subscribeOn(Schedulers.io());
    }

    private List<Event> convertEventDtosToEvents(List<EventDto> eventDtos) {
        final List<Event> events = new ArrayList<>();

        for (EventDto eventDto : eventDtos) {
            events.add(convertEventDtoToEvent(eventDto));
        }

        return events;
    }

    private Event convertEventDtoToEvent(EventDto eventDto) {
        return new Event(eventDto.getId(), eventDto.getOwner(), eventDto.getTitle(), eventDto.getDescription(),
                dateTimeUtility.convertMongoDate(eventDto.getStart()), dateTimeUtility.convertMongoDate(eventDto.getEnd()),
                new Event.Location(eventDto.getLat(), eventDto.getLon()));
    }

    private EventDto convertEventToEventDto(Event event) {
        return new EventDto(event.getId(), event.getOwner(), event.getTitle(), event.getDescription(),
                (float) event.getLocation().getLatitude(), (float) event.getLocation().getLongitude(),
                dateTimeUtility.convertToMongoDate(event.getDate()), dateTimeUtility.convertToMongoDate(event.getEndDate()));
    }
}
