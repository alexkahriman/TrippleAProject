package com.ftn.trippleaproject;

import android.app.Application;
import android.util.Log;

import com.amitshekhar.DebugDB;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.androidannotations.annotations.EApplication;

import com.ftn.trippleaproject.repository.local.LocalRepositoryModule;
import com.ftn.trippleaproject.repository.remote.RemoteRepositoryModule;
import com.ftn.trippleaproject.system.DeleteDataJobService;
import com.ftn.trippleaproject.usecase.UseCaseModule;

@EApplication
public class TrippleAApplication extends Application {

    private DiComponent diComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: This seems to be deprecated. Fix this when time allows for it.
        diComponent = DaggerDiComponent.builder()
                .useCaseModule(new UseCaseModule())
                .remoteRepositoryModule(new RemoteRepositoryModule(this))
                .localRepositoryModule(new LocalRepositoryModule(this))
                .build();

        Fresco.initialize(this);

        Log.d("Database", DebugDB.getAddressLog());
    }

    public DiComponent getDiComponent() {
        return diComponent;
    }
}
