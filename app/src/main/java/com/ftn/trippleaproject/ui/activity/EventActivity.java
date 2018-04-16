package com.ftn.trippleaproject.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.domain.Event;

@EActivity(R.layout.activity_event)
public class EventActivity extends AppCompatActivity {

    @Extra
    Event event;

    @ViewById
    TextView title;

    @AfterViews
    void init() {

        if (event == null) {
            return;
        }

        title.setText(event.getTitle());
    }
}
