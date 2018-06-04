package com.ftn.trippleaproject.system;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.TrippleAApplication;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.ReceiverAction;
import org.androidannotations.annotations.SystemService;

@EReceiver
public class AlarmBroadcastReceiver extends BroadcastReceiver {

    private final String CHANNEL_ID = "com.ftm.trippleaproject.alarms";

    @App
    TrippleAApplication application;

    @SystemService
    NotificationManager notificationManager;

    @ReceiverAction(actions = CHANNEL_ID)
    void showNotification(
            @ReceiverAction.Extra("event.id") String sid,
            @ReceiverAction.Extra("event.title") String title,
            @ReceiverAction.Extra("event.description") String description,
            Context context,
            Intent intent) {
        final String channelId = application.getString(R.string.alarm_id);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final NotificationChannel channel =
                    new NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        final Notification notification = new NotificationCompat.Builder(context, channelId)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_event)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(PendingIntent.getActivity(context, 0, intent, 0)) // necessary evil
                .build();

        final int id = (sid == null) ? 0 : Integer.valueOf(sid);
        notificationManager.notify(id, notification);
    }


    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
