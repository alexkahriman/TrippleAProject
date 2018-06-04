package com.ftn.trippleaproject.system.geofence;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.text.TextUtils;
import android.util.Log;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.system.geofence.error.GeoFenceErrorMessages;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import org.androidannotations.annotations.EService;

import java.util.ArrayList;
import java.util.List;

@EService
public class GeoFenceTransitionsIntentService extends JobIntentService {

    private static final String TAG = GeoFenceTransitionsIntentService.class.getSimpleName();

    private static final int JOB_ID = 42;

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, GeoFenceTransitionsIntentService.class, JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

        final GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = GeoFenceErrorMessages.getErrorString(this,
                    geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }

        // Get the transition type.
        final int geoFenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geoFenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            // Get the geo fences that were triggered. A single event can trigger
            // multiple geo fences.
            final List<Geofence> triggeringGeoFences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            final String geoFenceTransitionDetails = getGeoFenceTransitionDetails(
                    geoFenceTransition,
                    triggeringGeoFences);

            Log.i(TAG, geoFenceTransitionDetails);

            // TODO: Call use case here.
        } else {
            // Log the error.
            Log.e(TAG, getString(R.string.geofence_transition_invalid_type,
                    geoFenceTransition));
        }
    }

    private String getGeoFenceTransitionDetails(int geofenceTransition, List<Geofence> triggeringGeofences) {

        final String geoFenceTransitionString = getTransitionString(geofenceTransition);

        final ArrayList<String> triggeringGeofencesIdsList = new ArrayList<>();
        for (Geofence geofence : triggeringGeofences) {
            triggeringGeofencesIdsList.add(geofence.getRequestId());
        }
        final String triggeringGeoFencesIdsString = TextUtils.join(", ",  triggeringGeofencesIdsList);

        return geoFenceTransitionString + ": " + triggeringGeoFencesIdsString;
    }

    private String getTransitionString(int transitionType) {
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return getString(R.string.geofence_transition_entered);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return getString(R.string.geofence_transition_exited);
            default:
                return getString(R.string.unknown_geofence_transition);
        }
    }
}