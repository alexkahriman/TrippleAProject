package com.ftn.trippleaproject.ui.view;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.TrippleAApplication;
import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.usecase.business.DateTimeFormatterUseCase;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

@EViewGroup(R.layout.item_view_event)
public class EventItemView extends RelativeLayout {

    @App
    TrippleAApplication application;

    @ViewById
    TextView title;

    @ViewById
    TextView time;

    @Inject
    DateTimeFormatterUseCase dateTimeFormatterBuilder;

    private Event event;

    private EventActionListener eventActionListener;

    public EventItemView(Context context) {
        super(context);
    }

    public void bind(Event event, EventActionListener eventActionListener) {

        application.getDiComponent().inject(this);

        this.event = event;
        this.eventActionListener = eventActionListener;

        title.setText(event.getTitle());
        time.setText(dateTimeFormatterBuilder.dateTimeFormat(event.getDate()));
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
