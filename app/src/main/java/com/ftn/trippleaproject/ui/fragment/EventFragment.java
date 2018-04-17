package com.ftn.trippleaproject.ui.fragment;

import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

@EFragment(R.layout.fragment_event)
public class EventFragment extends Fragment {

    @App
    TrippleAApplication application;

    @FragmentArg
    Event event;

    @ViewById
    TextView title;

    @ViewById
    TextView description;

    @ViewById
    TextView time;

    @ViewById
    TextView location;

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

        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(event.getLocation().getLatitude(),
                    event.getLocation().getLongitude(), 1);
            String cityName = addresses.get(0).getAddressLine(0);

            location.setText(cityName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
