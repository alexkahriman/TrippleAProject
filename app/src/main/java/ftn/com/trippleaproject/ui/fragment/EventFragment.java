package ftn.com.trippleaproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.TrippleAApplication;
import ftn.com.trippleaproject.domain.Event;
import ftn.com.trippleaproject.usecase.business.DateTimeFormatterUseCase;

import static ftn.com.trippleaproject.ui.constatns.DateTimeFormatConstants.DATE_TIME_FORMAT;

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
        time.setText(dateTimeFormatterUseCase.dateTimeFormat().format(event.getDate()));
        latitude.setText(String.valueOf(event.getLocation().getLatitude()));
        longitude.setText(String.valueOf(event.getLocation().getLongitude()));
    }
}
