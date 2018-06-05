package com.ftn.trippleaproject.system.geofence;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeoFenceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

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


            // TODO: Call use case here.
        }
    }
}