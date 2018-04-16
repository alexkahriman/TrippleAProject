package ftn.com.trippleaproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.IgnoreWhen;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import javax.inject.Inject;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.TrippleAApplication;
import ftn.com.trippleaproject.domain.Event;
import ftn.com.trippleaproject.ui.activity.EventActivity_;
import ftn.com.trippleaproject.ui.activity.EventFormActivity_;
import ftn.com.trippleaproject.ui.adapter.EventsAdapter;
import ftn.com.trippleaproject.ui.view.EventItemView;
import ftn.com.trippleaproject.usecase.crud.EventCrudUseCase;
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
    EventCrudUseCase eventCrudUseCase;

    @AfterViews
    void init() {

        application.getDiComponent().inject(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(eventsAdapter);

        eventCrudUseCase.readAllLocal().observeOn(AndroidSchedulers.mainThread()).subscribe(this);

        eventsAdapter.setEventActionListener(this);
    }

    @IgnoreWhen(IgnoreWhen.State.VIEW_DESTROYED)
    @Override
    public void accept(List<Event> events) throws Exception {
        eventsAdapter.setEvents(events);
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
