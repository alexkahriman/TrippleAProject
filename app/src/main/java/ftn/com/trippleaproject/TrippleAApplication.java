package ftn.com.trippleaproject;

import android.app.Application;
import android.util.Log;

import com.amitshekhar.DebugDB;
import com.facebook.drawee.backends.pipeline.Fresco;

import org.androidannotations.annotations.EApplication;

import ftn.com.trippleaproject.repository.local.LocalRepositoryModule;
import ftn.com.trippleaproject.repository.remote.RemoteRepositoryModule;
import ftn.com.trippleaproject.usecase.UseCaseModule;

@EApplication
public class TrippleAApplication extends Application {

    private DiComponent diComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: This seems to be deprecated. Fix this when time allows for it.
        diComponent = DaggerDiComponent.builder()
                .useCaseModule(new UseCaseModule())
                .remoteRepositoryModule(new RemoteRepositoryModule())
                .localRepositoryModule(new LocalRepositoryModule(this))
                .build();

        Fresco.initialize(this);

        Log.d("Database", DebugDB.getAddressLog());
    }

    public DiComponent getDiComponent() {
        return diComponent;
    }
}
