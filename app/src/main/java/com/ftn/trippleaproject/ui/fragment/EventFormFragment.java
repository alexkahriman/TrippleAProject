package com.ftn.trippleaproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.TrippleAApplication;
import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.domain.Location;
import com.ftn.trippleaproject.usecase.business.DateTimeFormatterUseCase;
import com.ftn.trippleaproject.usecase.repository.EventUseCase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.FragmentByTag;
import org.androidannotations.annotations.IgnoreWhen;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@EFragment(R.layout.fragment_event_form)
public class EventFormFragment extends Fragment implements MapFragment.MapFragmentActionListener, Consumer<Event> {

    private static final String MAP_FRAGMENT_TAG = "mapFragment";

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
    TextView startTime;

    @ViewById
    TextView startDate;

    @ViewById
    TextView endDate;

    @ViewById
    TextView endTime;

    @FragmentArg
    Event event;

    @FragmentArg
    boolean edit;

    @FragmentByTag(MAP_FRAGMENT_TAG)
    MapFragment mapFragment;

    private Calendar calendar;

    private Calendar endCalendar;

    private EventFormFragmentActionListener eventFormFragmentActionListener;

    @AfterViews
    void init() {
        application.getDiComponent().inject(this);
        eventUseCase.read(event.getId()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);

        final FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            mapFragment = MapFragment_.builder().event(event).edit(edit).build();
            mapFragment.setMapFragmentActionListener(this);
            mapFragment.setRetainInstance(true);
            fragmentTransaction.replace(R.id.mapFragmentContainer, mapFragment, MAP_FRAGMENT_TAG);
            fragmentTransaction.commit();
        }

        calendar = Calendar.getInstance();
        setTimeText(calendar);
        setDateText(calendar);

        endCalendar = Calendar.getInstance();
        endCalendar.add(Calendar.MINUTE, 15);
        setEndTimeText(endCalendar);
        setEndDateText(endCalendar);

        if (event == null) {
            return;
        }
    }

    private void setEventUI() {
        title.setText(event.getTitle());
        description.setText(event.getDescription());

        calendar.setTime(event.getDate());
        setTimeText(calendar);
        setDateText(calendar);

        endCalendar.setTime(event.getEndDate());
        setEndTimeText(endCalendar);
        setEndDateText(endCalendar);
    }

    public void setEventFormFragmentActionListener(EventFormFragmentActionListener eventFormFragmentActionListener) {
        this.eventFormFragmentActionListener = eventFormFragmentActionListener;
    }

    @Click
    void startTime() {
        final TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setTimeSetActionListener(time -> {
            setTimeText(time);
            setCalendarTime(calendar, time);
        });
        final FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            timePickerFragment.show(fragmentManager, "timePicker");
        }
    }

    @Click
    void startDate() {
        final DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setDateSetActionListener(date -> {
            setDateText(date);
            setCalendarDate(calendar, date);
        });
        final FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            datePickerFragment.show(fragmentManager, "datePicker");
        }
    }

    @Click
    void endTime() {
        final TimePickerFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.setTimeSetActionListener(time -> {
            setEndTimeText(time);
            setCalendarTime(endCalendar, time);
        });
        final FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            timePickerFragment.show(fragmentManager, "timePicker");
        }
    }

    @Click
    void endDate() {
        final DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setDateSetActionListener(date -> {
            setEndDateText(date);
            setCalendarDate(endCalendar, date);
        });
        final FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            datePickerFragment.show(fragmentManager, "datePicker");
        }
    }

    @Click
    void confirm() {
        if (!checkEditTextNullValues()) {
            showToast("Please fill in all required data");
            return;
        }

        if (edit) {
            patchEvent();
        } else {
            createEvent();
        }
    }

    private void createEvent() {
        final Event event = new Event(1,
                title.getText().toString(),
                description.getText().toString(),
                calendar.getTime(),
                endCalendar.getTime(),
                new Location(mapFragment.getLocation().getLatitude(),
                        mapFragment.getLocation().getLongitude()));

        eventUseCase.create(event).subscribeOn(Schedulers.io()).subscribe(object -> onSuccess(),
                e -> onError());
    }

    private void patchEvent() {
        final Event event = new Event(this.event.getId(),
                title.getText().toString(),
                description.getText().toString(),
                calendar.getTime(),
                endCalendar.getTime(),
                new Location(mapFragment.getLocation().getLatitude(),
                        mapFragment.getLocation().getLongitude()));
        eventUseCase.patch(event).subscribeOn(Schedulers.io()).subscribe(object -> onSuccess(),
                e -> onError());
    }

    private void onSuccess() {
        if (eventFormFragmentActionListener != null) {
            eventFormFragmentActionListener.finishActivity();
        }
    }

    private void onError() {
        if (eventFormFragmentActionListener != null) {
            eventFormFragmentActionListener.finishActivityOnError();
        }
    }

    private boolean checkEditTextNullValues() {
        return !title.getText().toString().isEmpty()
                && !description.getText().toString().isEmpty()
                && calendar != null
                && mapFragment.getLocation().getLatitude() != 0
                && mapFragment.getLocation().getLongitude() != 0;
    }

    private void setTimeText(Calendar time) {
        startTime.setText(dateTimeFormatterUseCase.timeFormat(time.getTime()));
    }

    private void setDateText(Calendar date) {
        startDate.setText(dateTimeFormatterUseCase.dateFormat(date.getTime()));
    }

    private void setEndTimeText(Calendar time) {
        endTime.setText(dateTimeFormatterUseCase.timeFormat(time.getTime()));
    }

    private void setEndDateText(Calendar date) {
        endDate.setText(dateTimeFormatterUseCase.dateFormat(date.getTime()));
    }

    private void setCalendarTime(Calendar calendar, Calendar time) {
        calendar.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
        calendar.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
    }

    private void setCalendarDate(Calendar calendar, Calendar date) {
        calendar.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.MONTH, date.get(Calendar.MONTH));
        calendar.set(Calendar.YEAR, date.get(Calendar.YEAR));
    }

    @Override
    public void permissionDenied() {
        eventFormFragmentActionListener.finishActivity();
    }

    @IgnoreWhen(IgnoreWhen.State.VIEW_DESTROYED)
    @Override
    public void accept(Event event) {
        this.event = event;
        setEventUI();
    }

    public interface EventFormFragmentActionListener {
        void finishActivity();

        void finishActivityOnError();
    }

    @UiThread
    void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }
}
