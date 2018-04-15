package ftn.com.trippleaproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.domain.Event;
import ftn.com.trippleaproject.ui.activity.EventActivity_;
import ftn.com.trippleaproject.ui.adapter.EventsAdapter;
import ftn.com.trippleaproject.ui.view.EventItemView;

@EFragment(R.layout.fragment_events)
public class EventsFragment extends Fragment implements EventItemView.EventActionListener {

    @ViewById
    RecyclerView recyclerView;

    @Bean
    EventsAdapter eventsAdapter;

    @AfterViews
    void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(eventsAdapter);

        List<Event> events = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            events.add(new Event("title" + i));
        }

        eventsAdapter.setEventActionListener(this);
        eventsAdapter.setEvents(events);
    }

    @Override
    public void eventSelected(Event event) {
        EventActivity_.intent(getContext()).event(event).start();
    }
}
