package ftn.com.trippleaproject.ui.view;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.domain.database.Event;

/**
 * Created by aradosevic on 3/31/18.
 */

@EViewGroup(R.layout.item_view_event)
public class EventItemView extends RelativeLayout {

    @ViewById
    TextView title;

    private Event event;

    public EventItemView(Context context) {
        super(context);
    }

    public void bind(Event event) {
        this.event = event;

        title.setText(event.getTitle());
    }
}
