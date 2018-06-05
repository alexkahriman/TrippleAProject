package com.ftn.trippleaproject.system.geofence;


import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.usecase.business.dependency.GeoFenceProvider;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.ftn.trippleaproject.system.geofence.Constants.EVENT_ID_PREFIX;

public class GeoFenceProviderImpl implements GeoFenceProvider {

    private static final String TAG = GeoFenceProviderImpl.class.getSimpleName();

    private static final float GEO_FENCE_RADIUS = 200f;

    private final Context context;

    private final GeofencingClient geofencingClient;

    private PendingIntent geoFencePendingIntent;

    public GeoFenceProviderImpl(Context context) {
        this.context = context;
        geofencingClient = LocationServices.getGeofencingClient(context.getApplicationContext());
    }

    @Override
    public void addGeoFences(List<Event> events) {

        if (!checkPermissions()) {
            Log.e(TAG, "Lacking fine location permission for geo fencing.");
            return;
        }

        final List<Geofence> geoFences = new ArrayList<>();

        for (Event event: events) {
            geoFences.add(createGeoFence(event));
        }

        final GeofencingRequest request = createGeoFenceRequest(geoFences);

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
        builder.setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT);
        builder.setExpirationDuration(Geofence.NEVER_EXPIRE);

        return builder.build();
    }

    private GeofencingRequest createGeoFenceRequest(List<Geofence> geoFences) {

        final GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geoFences);

        return builder.build();
    }

    private PendingIntent getGeoFencePendingIntent() {

        // Reuse the PendingIntent if we already have it.
        if (geoFencePendingIntent != null) {
            return geoFencePendingIntent;
        }
        final Intent intent = new Intent(context.getApplicationContext(), GeoFenceBroadcastReceiver_.class);

        geoFencePendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return geoFencePendingIntent;
    }

    private boolean checkPermissions() {
        int permissionState = PermissionChecker.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }
}