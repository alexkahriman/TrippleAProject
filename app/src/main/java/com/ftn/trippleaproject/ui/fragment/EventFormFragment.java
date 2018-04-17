package com.ftn.trippleaproject.ui.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.TextView;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.TrippleAApplication;
import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.usecase.business.DateTimeFormatterUseCase;
import com.ftn.trippleaproject.usecase.repository.EventUseCase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

import javax.inject.Inject;


@EFragment(R.layout.fragment_event_form)
public class EventFormFragment extends Fragment implements TimePickerFragment.TimeSetActionListener,
        DatePickerFragment.DateSetActionListener {

    @App
    TrippleAApplication application;

    @Inject
    EventUseCase eventUseCase;

    @Inject
    DateTimeFormatterUseCase dateTimeFormatterUseCase;

    @ViewById
    EditText title;

    @ViewById
    EditText description;

    @ViewById
    EditText latitude;

    @ViewById
    EditText longitude;

    @ViewById
    TextView date, time;

    @FragmentArg
    Event event;

    private Context context;
    private Calendar calendar;

    private EventFormFragmentActionListener eventFormFragmentActionListener;

    @AfterViews
    void init() {

        application.getDiComponent().inject(this);

        context = getContext();

        calendar = Calendar.getInstance();
        setTimeText(calendar);
        setDateText(calendar);

        if (event == null) {
            return;
        }

        title.setText(event.getTitle());
        description.setText(event.getDescription());
        latitude.setText(String.valueOf(event.getLocation().getLatitude()));
        longitude.setText(String.valueOf(event.getLocation().getLongitude()));

        calendar.setTime(event.getDate());
        setTimeText(calendar);
        setDateText(calendar);
    }

    public void setEventFormFragmentActionListener(EventFormFragmentActionListener eventFormFragmentActionListener) {
        this.eventFormFragmentActionListener = eventFormFragmentActionListener;
    }

    @Click
    void time() {

        TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setTimeSetActionListener(this);
        timePickerFragment.show(getFragmentManager(), "timePicker");
    }

    @Click
    void date() {

        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setDateSetActionListener(this);
        datePickerFragment.show(getFragmentManager(), "datePicker");
    }

    @Click
    void add() {

        if (!checkEditTextNullValues()) {
            return;
        }

        Event event = new Event(1,
                title.getText().toString(),
                description.getText().toString(),
                calendar.getTime(),
                new Event.Location(Long.valueOf(latitude.getText().toString()),
                        Long.valueOf(longitude.getText().toString())));

        eventUseCase.create(event).blockingSubscribe();

        if (eventFormFragmentActionListener != null) {
            eventFormFragmentActionListener.finishActivity();
        }
    }

    private boolean checkEditTextNullValues() {
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
        this.time.setText(dateTimeFormatterUseCase.timeFormat(time.getTime()));
    }

    private void setDateText(Calendar date) {
        this.date.setText(dateTimeFormatterUseCase.dateFormat(date.getTime()));
    }

    public interface EventFormFragmentActionListener {
        void finishActivity();
    }
}
