package com.ftn.trippleaproject.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.ui.adapter.generic.RecyclerViewAdapterBase;
import com.ftn.trippleaproject.ui.adapter.generic.ViewWrapper;
import com.ftn.trippleaproject.ui.view.EventItemView;
import com.ftn.trippleaproject.ui.view.EventItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

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
