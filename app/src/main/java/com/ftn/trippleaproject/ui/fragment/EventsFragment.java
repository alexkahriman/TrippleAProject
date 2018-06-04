package com.ftn.trippleaproject.ui.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.TrippleAApplication;
import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.system.AlarmBroadcastReceiver_;
import com.ftn.trippleaproject.ui.activity.EventActivity_;
import com.ftn.trippleaproject.ui.activity.EventFormActivity_;
import com.ftn.trippleaproject.ui.adapter.EventsAdapter;
import com.ftn.trippleaproject.ui.view.EventItemView;
import com.ftn.trippleaproject.usecase.repository.EventUseCase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.IgnoreWhen;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

@EFragment(R.layout.fragment_events)
public class EventsFragment extends Fragment implements Consumer<List<Event>>,
        EventItemView.EventActionListener {

    @App
    TrippleAApplication application;

    @ViewById
    RecyclerView recyclerView;

    @Bean
    EventsAdapter eventsAdapter;

    @Inject
    EventUseCase eventUseCase;

    @SystemService
    AlarmManager alarmManager;

    @AfterViews
    void init() {
        application.getDiComponent().inject(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(eventsAdapter);

        eventUseCase.readAllLocalAndUpdate().observeOn(AndroidSchedulers.mainThread()).subscribe(this);

        eventsAdapter.setEventActionListener(this);
    }

    @IgnoreWhen(IgnoreWhen.State.VIEW_DESTROYED)
    @Override
    public void accept(List<Event> events) throws Exception {
        final String action = getString(R.string.alarm_id);
        eventsAdapter.setEvents(events);
        for (Event event : events) {
            if (event.getEndDate().getTime() > System.currentTimeMillis()) {
                final Intent intent = new Intent(this.getContext(), AlarmBroadcastReceiver_.class);
                intent.setAction(action);
                intent.putExtra("event.id", event.getId());
                intent.putExtra("event.owner", event.getOwner());
                intent.putExtra("event.title", event.getTitle());
                intent.putExtra("event.description", event.getDescription());
                intent.putExtra("event.date", event.getDate());
                intent.putExtra("event.endDate", event.getEndDate());
                intent.putExtra("event.lat", event.getLocation().getLatitude());
                intent.putExtra("event.lon", event.getLocation().getLongitude());

                final PendingIntent pendingIntent =
                        PendingIntent.getBroadcast(this.getContext(), (int) event.getId(), intent, PendingIntent.FLAG_CANCEL_CURRENT);
                alarmManager.set(AlarmManager.RTC_WAKEUP, event.getDate().getTime() - AlarmManager.INTERVAL_HALF_HOUR, pendingIntent);
            }
        }
    }

    @Click
    void addEvent() {
        EventFormActivity_.intent(getContext()).start();
    }

    @Override
    public void eventSelected(Event event) {
        EventActivity_.intent(getContext()).event(event).start();
    }
}
