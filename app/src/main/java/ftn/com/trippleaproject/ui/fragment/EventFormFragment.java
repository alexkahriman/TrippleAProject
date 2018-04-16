package ftn.com.trippleaproject.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.TrippleAApplication;
import ftn.com.trippleaproject.domain.Event;
import ftn.com.trippleaproject.usecase.repository.EventCrudUseCase;

import static ftn.com.trippleaproject.ui.constatns.DateTimeFormatConstants.DATE_FORMAT;
import static ftn.com.trippleaproject.ui.constatns.DateTimeFormatConstants.TIME_FORMAT;


@EFragment(R.layout.fragment_event_form)
public class EventFormFragment extends Fragment implements TimePickerFragment.TimeSetActionListener,
                                                            DatePickerFragment.DateSetActionListener {

    @App
    TrippleAApplication application;

    @Inject
    EventCrudUseCase eventCrudUseCase;

    @ViewById
    EditText title, description, latitude, longitude;

    @ViewById
    TextView date, time;

    @FragmentArg
    Event event;

    SimpleDateFormat timeFormatter;
    SimpleDateFormat dateFormatter;
    Context context;
    Calendar calendar;

    @AfterViews
    void init() {

        application.getDiComponent().inject(this);

        context = getContext();
        if (context == null) {
            return;
        }

        timeFormatter = new SimpleDateFormat(TIME_FORMAT,
                context.getResources().getConfiguration().locale);
        dateFormatter = new SimpleDateFormat(DATE_FORMAT,
                context.getResources().getConfiguration().locale);

        calendar = Calendar.getInstance();
        setTimeText(calendar);
        setDateText(calendar);

        if (event == null) {
            return;
        }

        title.setText(event.getTitle());
        description.setText(event.getDescription());
        latitude.setText(String.valueOf(event.getLatitude()));
        longitude.setText(String.valueOf(event.getLongitude()));

        calendar.setTime(event.getDate());
        setTimeText(calendar);
        setDateText(calendar);
    }

    @Click
    void time() {

        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setTimeSetActionListener(this);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            timePickerFragment.show(fragmentManager, "timePicker");
        }
    }

    @Click
    void date() {

        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setDateSetActionListener(this);
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            datePickerFragment.show(fragmentManager, "datePicker");
        }
    }

    @Click
    void add() {

        if (!validate()) {
            return;
        }

        Event event = new Event(title.getText().toString(),
                description.getText().toString(),
                calendar.getTime(),
                Long.valueOf(latitude.getText().toString()),
                Long.valueOf(longitude.getText().toString()));

        eventCrudUseCase.create(event).blockingSubscribe();
        Activity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    private boolean validate() {
        return !title.getText().toString().isEmpty() &&
                !description.getText().toString().isEmpty() &&
                calendar != null &&
                !latitude.getText().toString().isEmpty() &&
                !longitude.getText().toString().isEmpty();

    }

    @Override
    public void setTime(Calendar time) {

        setTimeText(time);
        calendar.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
        calendar.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
    }

    @Override
    public void dateSet(Calendar date) {

        setDateText(date);
        calendar.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.MONTH, date.get(Calendar.MONTH));
        calendar.set(Calendar.YEAR, date.get(Calendar.YEAR));
    }

    private void setTimeText(Calendar time) {
        this.time.setText(timeFormatter.format(time.getTime()));
    }

    private void setDateText(Calendar date) {
        this.date.setText(dateFormatter.format(date.getTime()));
    }
}
