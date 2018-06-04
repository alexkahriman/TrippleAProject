package com.ftn.trippleaproject.system;

import android.content.Context;

import com.ftn.trippleaproject.system.geofence.GeoFenceProviderImpl;
import com.ftn.trippleaproject.usecase.business.dependency.GeoFenceProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class SystemModule {

    private Context context;

    public SystemModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    GeoFenceProvider provideGeoFenceProvider() {
        return new GeoFenceProviderImpl(context);
    }
}
