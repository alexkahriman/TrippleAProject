package com.ftn.trippleaproject;

import android.app.Application;
import android.util.Log;

import com.amitshekhar.DebugDB;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.ftn.trippleaproject.repository.local.LocalRepositoryModule;
import com.ftn.trippleaproject.repository.remote.RemoteRepositoryModule;
import com.ftn.trippleaproject.system.SystemModule;
import com.ftn.trippleaproject.usecase.UseCaseModule;

import org.androidannotations.annotations.EApplication;

@EApplication
public class TrippleAApplication extends Application {

    private DiComponent diComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        diComponent = DaggerDiComponent.builder()
                .useCaseModule(new UseCaseModule())
                .remoteRepositoryModule(new RemoteRepositoryModule(this))
                .localRepositoryModule(new LocalRepositoryModule(this))
                .systemModule(new SystemModule(this))
                .build();

        Fresco.initialize(this);

        Log.d("Database", DebugDB.getAddressLog());
    }

    public DiComponent getDiComponent() {
        return diComponent;
    }
}
