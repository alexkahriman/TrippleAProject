package com.ftn.trippleaproject.ui.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.ui.fragment.EventsOnMapFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_events_on_map)
public class EventsOnMapActivity extends AppCompatActivity {

    @AfterViews
    void init() {
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.eventsOnMapFragmentContainer, EventsOnMapFragment_.builder().build());
        fragmentTransaction.commit();
    }
}
