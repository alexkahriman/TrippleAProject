package com.ftn.trippleaproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.TrippleAApplication;
import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.usecase.business.DateTimeFormatterUseCase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EFragment(R.layout.fragment_event)
public class EventFragment extends Fragment {

    @App
    TrippleAApplication application;

    @FragmentArg
    Event event;

    @ViewById
    TextView title, description, time, latitude, longitude;

    @Inject
    DateTimeFormatterUseCase dateTimeFormatterUseCase;

    @AfterViews
    void init() {

        application.getDiComponent().inject(this);

        if (event == null) {
            return;
        }

        title.setText(event.getTitle());
        description.setText(event.getDescription());
        time.setText(dateTimeFormatterUseCase.dateTimeFormat(event.getDate()));
        latitude.setText(String.valueOf(event.getLocation().getLatitude()));
        longitude.setText(String.valueOf(event.getLocation().getLongitude()));
    }
}
