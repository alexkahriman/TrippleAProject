package com.ftn.trippleaproject.usecase.business.dependency;


import com.ftn.trippleaproject.domain.Event;

import java.util.List;

public interface GeoFenceProvider {

    void addGeoFences(List<Event> events);

    void removeGeoFence(Event event);
}
