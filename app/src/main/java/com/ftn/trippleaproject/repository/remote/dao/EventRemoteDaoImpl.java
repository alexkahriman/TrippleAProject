package com.ftn.trippleaproject.repository.remote.dao;

import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.repository.remote.client.BackendApiService;
import com.ftn.trippleaproject.repository.remote.dto.EventDto;
import com.ftn.trippleaproject.usecase.repository.dependency.remote.EventRemoteDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class EventRemoteDaoImpl implements EventRemoteDao {

    private final BackendApiService backendApiService;

    public EventRemoteDaoImpl(BackendApiService backendApiService) {
        this.backendApiService = backendApiService;
    }

    @Override
    public Single<List<Event>> read() {
        return backendApiService.readEvents()
                .map(this::convertEventDtosToEvents).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Event> create(Event event) {
        return backendApiService.createEvent(new EventDto(event))
                .map(this::convertEventDtoToEvent).subscribeOn(Schedulers.io());
    }

    private List<Event> convertEventDtosToEvents(List<EventDto> eventDtos) {
        List<Event> events = new ArrayList<>();

        for (EventDto eventDto : eventDtos) {
            final Event event = new Event(eventDto.getId(), eventDto.getOwner(), eventDto.getTitle(), eventDto.getDescription(),
                    convertMongoDate(eventDto.getStart()), convertMongoDate(eventDto.getEnd()),
                    new Event.Location(eventDto.getLat(), eventDto.getLon()));
            events.add(event);
        }

        return events;
    }

    private Event convertEventDtoToEvent(EventDto eventDto) {
        return new Event(eventDto.getId(), eventDto.getOwner(), eventDto.getTitle(), eventDto.getDescription(),
                convertMongoDate(eventDto.getStart()), convertMongoDate(eventDto.getEnd()),
                new Event.Location(eventDto.getLat(), eventDto.getLon()));
    }

    private static Date convertMongoDate(String val) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            return inputFormat.parse(val);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String convertToMongoDate(Date date) {
        SimpleDateFormat outputForm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return outputForm.format(date);
    }
}
