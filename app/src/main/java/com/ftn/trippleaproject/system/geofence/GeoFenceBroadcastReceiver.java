package com.ftn.trippleaproject.system.geofence;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ftn.trippleaproject.TrippleAApplication;
import com.ftn.trippleaproject.usecase.repository.EventUseCase;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EReceiver;

import java.util.List;

import javax.inject.Inject;

import static com.ftn.trippleaproject.system.geofence.Constants.EVENT_ID_PREFIX;

@EReceiver
public class GeoFenceBroadcastReceiver extends BroadcastReceiver {

    @App
    TrippleAApplication application;

    @Inject
    EventUseCase eventUseCase;

    @Override
    public void onReceive(Context context, Intent intent) {

        application.getDiComponent().inject(this);

        final GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            return;
        }

        // Get the transition type.
        final int geoFenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            // Get the geo fences that were triggered. A single event can trigger
            // multiple geo fences.
            final List<Geofence> triggeringGeoFences = geofencingEvent.getTriggeringGeofences();

            for (Geofence geofence: triggeringGeoFences) {

                if (!geofence.getRequestId().contains(EVENT_ID_PREFIX)) {
                    continue;
                }

                final String eventIdString = geofence.getRequestId().replace(EVENT_ID_PREFIX, "");
                final long eventId = Long.parseLong(eventIdString);

                eventUseCase.approachingEvent(eventId).subscribe();
            }
        }
    }
}