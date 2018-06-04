package com.ftn.trippleaproject.usecase.business.dependency;


import com.ftn.trippleaproject.domain.Event;

public interface GeoFenceProvider {

    void addGeoFence(Event event);

    void removeGeoFence(Event event);
}
