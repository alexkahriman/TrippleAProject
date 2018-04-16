package ftn.com.trippleaproject.ui.view;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.domain.Event;

import static ftn.com.trippleaproject.ui.constatns.DateTimeFormatConstants.DATE_TIME_FORMAT;

@EViewGroup(R.layout.item_view_event)
public class EventItemView extends RelativeLayout {

    @ViewById
    TextView title, time;

    private Event event;

    private EventActionListener eventActionListener;

    public EventItemView(Context context) {
        super(context);
    }

    public void bind(Event event, EventActionListener eventActionListener) {

        this.event = event;
        this.eventActionListener = eventActionListener;

        title.setText(event.getTitle());
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT,
                    getResources().getConfiguration().locale);
        time.setText(formatter.format(event.getDate()));
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
