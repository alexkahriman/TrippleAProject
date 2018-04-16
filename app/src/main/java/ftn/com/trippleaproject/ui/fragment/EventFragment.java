package ftn.com.trippleaproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.domain.Event;

import static ftn.com.trippleaproject.ui.constatns.DateTimeFormatConstants.DATE_TIME_FORMAT;

@EFragment(R.layout.fragment_event)
public class EventFragment extends Fragment {

    @FragmentArg
    Event event;

    @ViewById
    TextView title, description, time, latitude, longitude;

    @AfterViews
    void init() {

        if (event == null) {
            return;
        }

        title.setText(event.getTitle());
        description.setText(event.getDescription());
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT,
                getResources().getConfiguration().locale);
        time.setText(formatter.format(event.getDate()));
        latitude.setText(String.valueOf(event.getLatitude()));
        longitude.setText(String.valueOf(event.getLongitude()));
    }
}
