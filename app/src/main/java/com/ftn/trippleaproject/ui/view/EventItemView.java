package com.ftn.trippleaproject.ui.view;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.domain.Event;

@EViewGroup(R.layout.item_view_event)
public class EventItemView extends RelativeLayout {

    @ViewById
    TextView title;

    private Event event;

    private EventActionListener eventActionListener;

    public EventItemView(Context context) {
        super(context);
    }

    public void bind(Event event, EventActionListener eventActionListener) {
        this.event = event;
        this.eventActionListener = eventActionListener;

        title.setText(event.getTitle());
    }

    @Click
    void card() {
        if (eventActionListener != null) {
            eventActionListener.eventSelected(event);
        }
    }

    /**
     * Handles actions in events list.
     */
    public interface EventActionListener {

        /**
         * Handles tap on a event.
         *
         * @param event Tapped event.
         */
        void eventSelected(Event event);
    }
}
