package com.ftn.trippleaproject.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.ui.adapter.generic.RecyclerViewAdapterBase;
import com.ftn.trippleaproject.ui.adapter.generic.ViewWrapper;
import com.ftn.trippleaproject.ui.view.EventOnMapItemView;
import com.ftn.trippleaproject.ui.view.EventOnMapItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

@EBean
public class EventsOnMapAdapter extends RecyclerViewAdapterBase<Event, EventOnMapItemView> {

    @RootContext
    Context context;

    private EventOnMapItemView.EventOnMapActionListener eventOnMapActionListener;

    @Override
    protected EventOnMapItemView onCreateItemView(ViewGroup parent, int viewType) {
        return EventOnMapItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewWrapper<EventOnMapItemView> holder, int position) {
        holder.getView().bind(items.get(position), eventOnMapActionListener);
    }

    public void setEvents(List<Event> events) {
        this.items = events;
        notifyDataSetChanged();
    }

    public void setEventOnMapActionListener(EventOnMapItemView.EventOnMapActionListener eventOnMapActionListener) {
        this.eventOnMapActionListener = eventOnMapActionListener;
    }
}
