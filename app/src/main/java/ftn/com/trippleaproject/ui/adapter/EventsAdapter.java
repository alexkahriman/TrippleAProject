package ftn.com.trippleaproject.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import ftn.com.trippleaproject.domain.database.Event;
import ftn.com.trippleaproject.ui.adapter.generic.RecyclerViewAdapterBase;
import ftn.com.trippleaproject.ui.adapter.generic.ViewWrapper;
import ftn.com.trippleaproject.ui.view.EventItemView;
import ftn.com.trippleaproject.ui.view.EventItemView_;

/**
 * Events {@link android.support.v7.widget.RecyclerView RecyclerView} adapter.
 */

@EBean
public class EventsAdapter extends RecyclerViewAdapterBase<Event, EventItemView> {

    @RootContext
    Context context;

    @Override
    protected EventItemView onCreateItemView(ViewGroup parent, int viewType) {
        return EventItemView_.build(context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewWrapper<EventItemView> holder, int position) {
        holder.getView().bind(items.get(position));
    }

    public void setEvents(List<Event> events) {

        this.items = events;
        notifyDataSetChanged();
    }
}
