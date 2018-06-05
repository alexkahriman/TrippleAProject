package com.ftn.trippleaproject;

import com.ftn.trippleaproject.repository.local.LocalRepositoryModule;
import com.ftn.trippleaproject.repository.remote.RemoteRepositoryModule;
import com.ftn.trippleaproject.system.DeleteDataJobService;
import com.ftn.trippleaproject.system.SystemModule;
import com.ftn.trippleaproject.system.geofence.GeoFenceBroadcastReceiver;
import com.ftn.trippleaproject.ui.activity.LoginActivity;
import com.ftn.trippleaproject.ui.fragment.EventFormFragment;
import com.ftn.trippleaproject.ui.fragment.EventFragment;
import com.ftn.trippleaproject.ui.fragment.EventsFragment;
import com.ftn.trippleaproject.ui.fragment.EventsOnMapFragment;
import com.ftn.trippleaproject.ui.fragment.MapFragment;
import com.ftn.trippleaproject.ui.fragment.NewsArticleFragment;
import com.ftn.trippleaproject.ui.fragment.NewsFeedFragment;
import com.ftn.trippleaproject.ui.fragment.SettingsFragment;
import com.ftn.trippleaproject.ui.view.EventItemView;
import com.ftn.trippleaproject.ui.view.EventOnMapItemView;
import com.ftn.trippleaproject.usecase.UseCaseModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {UseCaseModule.class, RemoteRepositoryModule.class, LocalRepositoryModule.class, SystemModule.class})
@Singleton
public interface DiComponent {

    void inject(LoginActivity loginActivity);

    void inject(NewsFeedFragment newsFeedFragment);

    void inject(NewsArticleFragment newsArticleFragment);

    void inject(EventsFragment eventsFragment);

    void inject(EventFormFragment eventFormFragment);

    void inject(EventFragment eventFragment);

    void inject(EventItemView eventItemView);

    void inject(MapFragment mapFragment);

    void inject(EventsOnMapFragment eventsOnMapFragment);

    void inject(EventOnMapItemView eventOnMapItemView);

    void inject(SettingsFragment settingsFragment);

    void inject(DeleteDataJobService deleteDataJobService);

    void inject(GeoFenceBroadcastReceiver geoFenceBroadcastReceiver);
}
