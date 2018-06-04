package com.ftn.trippleaproject.system.geofence;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GeoFenceBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Enqueues a JobIntentService passing the context and intent as parameters
        GeoFenceTransitionsIntentService.enqueueWork(context, intent);
    }
}