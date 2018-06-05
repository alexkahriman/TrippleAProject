package com.ftn.trippleaproject.system.geofence;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.ui.activity.EventActivity_;
import com.ftn.trippleaproject.usecase.business.dependency.GeoFenceNotificationProvider;

public class GeoFenceNotificationProviderImpl implements GeoFenceNotificationProvider {

    private final Context context;

    private final NotificationManager notificationManager;

    public GeoFenceNotificationProviderImpl(Context context) {
        this.context = context;
        this.notificationManager = ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE));
    }

    @Override
    public void createNotificationForEvent(Event event) {

        final String channelId = context.getString(R.string.approach_id);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationChannel channel =
                    new NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        final Intent intent = EventActivity_.intent(context).event(event).get();
        final PendingIntent pendingIntent = PendingIntent
                .getActivity(context, (int) event.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        final Notification notification = new NotificationCompat.Builder(context, channelId)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_event)
                .setContentTitle(context.getString(R.string.event_approach))
                .setContentText(event.getTitle())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify((int) event.getId(), notification);
    }
}
