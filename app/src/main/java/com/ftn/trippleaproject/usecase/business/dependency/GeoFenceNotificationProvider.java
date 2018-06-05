package com.ftn.trippleaproject.usecase.business.dependency;


import com.ftn.trippleaproject.domain.Event;

public interface GeoFenceNotificationProvider {

    void createNotificationForEvent(Event event);
}
