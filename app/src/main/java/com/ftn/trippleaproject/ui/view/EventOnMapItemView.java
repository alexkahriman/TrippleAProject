package com.ftn.trippleaproject.ui.view;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.TrippleAApplication;
import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.usecase.business.DateTimeFormatterUseCase;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

@EViewGroup(R.layout.item_view_event_on_map)
public class EventOnMapItemView extends RelativeLayout {

    @App
    TrippleAApplication trippleAApplication;

    @ViewById
    TextView title;

    @ViewById
    TextView start;

    @ViewById
    TextView location;

    @Inject
    DateTimeFormatterUseCase dateTimeFormatterBuilder;

    private EventOnMapActionListener eventOnMapActionListener;

    private Event event;

    public EventOnMapItemView(Context context) {
        super(context);
    }

    public void bind(Event event, EventOnMapItemView.EventOnMapActionListener eventOnMapActionListener) {
        trippleAApplication.getDiComponent().inject(this);

        this.event = event;
        this.eventOnMapActionListener = eventOnMapActionListener;

        title.setText(event.getTitle());
        start.setText(dateTimeFormatterBuilder.dateTimeFormat(event.getDate()));
        location.setText(getGeoLocation(event.getLocation()));
    }

    @Click
    void card() {
        if (eventOnMapActionListener != null) {
            eventOnMapActionListener.eventSelected(event);
        }
    }

    public interface EventOnMapActionListener {
        void eventSelected(Event event);
    }

    private String getGeoLocation(Event.Location location) {
        final String cityName = "Current location";
        final Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            return addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
            return cityName;
        }
    }
}
