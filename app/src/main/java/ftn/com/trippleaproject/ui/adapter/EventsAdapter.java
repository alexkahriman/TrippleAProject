package ftn.com.trippleaproject.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import ftn.com.trippleaproject.domain.Event;
import ftn.com.trippleaproject.ui.adapter.generic.RecyclerViewAdapterBase;
import ftn.com.trippleaproject.ui.adapter.generic.ViewWrapper;
import ftn.com.trippleaproject.ui.view.EventItemView;
import ftn.com.trippleaproject.ui.view.EventItemView_;

@EBean
public class EventsAdapter extends RecyclerViewAdapterBase<Event, EventItemView> {

    @RootContext
    Context context;

    private EventItemView.EventActionListener eventActionListener;

    @Override
    protected EventItemView onCreateItemView(ViewGroup parent, int viewType) {
        return EventItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewWrapper<EventItemView> holder, int position) {
        holder.getView().bind(items.get(position), eventActionListener);
    }

    public void setEvents(List<Event> events) {

        this.items = events;
        notifyDataSetChanged();
    }

    public void setEventActionListener(EventItemView.EventActionListener eventActionListener) {
        this.eventActionListener = eventActionListener;
    }
}
