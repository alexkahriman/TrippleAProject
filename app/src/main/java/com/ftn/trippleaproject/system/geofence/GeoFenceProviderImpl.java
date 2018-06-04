package com.ftn.trippleaproject.system.geofence;


import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.usecase.business.dependency.GeoFenceProvider;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Collections;

public class GeoFenceProviderImpl implements GeoFenceProvider {

    private static final String TAG = GeoFenceProviderImpl.class.getSimpleName();

    private static final String EVENT_ID_PREFIX = "event-";

    private static final int GEO_FENCE_RADIUS = 200;

    private final Context context;

    private final GeofencingClient geofencingClient;

    private PendingIntent geoFencePendingIntent;

    public GeoFenceProviderImpl(Context context) {
        this.context = context;
        geofencingClient = LocationServices.getGeofencingClient(context);
    }

    @Override
    public void addGeoFence(Event event) {

        if (!checkPermissions()) {
            Log.e(TAG, context.getString(R.string.insufficient_permissions));
            return;
        }

        final Geofence geofence = createGeoFence(event);
        final GeofencingRequest request = createGeoFenceRequest(geofence);

        geofencingClient.addGeofences(request, getGeoFencePendingIntent());
    }

    @Override
    public void removeGeoFence(Event event) {
        geofencingClient.removeGeofences(Collections.singletonList(EVENT_ID_PREFIX + event.getId()));
    }

    private Geofence createGeoFence(Event event) {

        final Geofence.Builder builder = new Geofence.Builder();
        builder.setRequestId(EVENT_ID_PREFIX + event.getId());
        builder.setCircularRegion(event.getLocation().getLatitude(), event.getLocation().getLongitude(), GEO_FENCE_RADIUS);
        builder.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER);

        return builder.build();
    }

    private GeofencingRequest createGeoFenceRequest(Geofence geofence) {

        final GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofence(geofence);

        return builder.build();
    }

    private PendingIntent getGeoFencePendingIntent() {

        // Reuse the PendingIntent if we already have it.
        if (geoFencePendingIntent != null) {
            return geoFencePendingIntent;
        }
        final Intent intent = new Intent(context, GeoFenceBroadcastReceiver.class);

        geoFencePendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return geoFencePendingIntent;
    }

    private boolean checkPermissions() {
        int permissionState = PermissionChecker.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
}