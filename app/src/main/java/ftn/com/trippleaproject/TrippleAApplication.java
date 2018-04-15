package ftn.com.trippleaproject;

import android.app.Application;

import org.androidannotations.annotations.EApplication;

import ftn.com.trippleaproject.usecase.UseCaseModule;

@EApplication
public class TrippleAApplication extends Application {

    private DiComponent diComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: This seems to be deprecated. Fix this when time allows for it.
        diComponent = DaggerDiComponent.builder().useCaseModule(new UseCaseModule()).build();
    }

    public DiComponent getDiComponent() {
        return diComponent;
    }
}
