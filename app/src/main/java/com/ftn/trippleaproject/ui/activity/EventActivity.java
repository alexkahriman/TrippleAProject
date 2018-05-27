package com.ftn.trippleaproject.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.ui.fragment.EventFragment_;
import com.ftn.trippleaproject.ui.fragment.MapFragment;
import com.ftn.trippleaproject.ui.fragment.MapFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentByTag;

@EActivity(R.layout.activity_event)
public class EventActivity extends AppCompatActivity implements MapFragment.MapFragmentActionListener {

    public  static final int REQUEST_CHECK_SETTINGS = 33;

    @Extra
    Event event;

    @FragmentByTag(MAP_FRAGMENT_TAG)
    MapFragment mapFragment;

    private static final String MAP_FRAGMENT_TAG = "mapFragment";

    @AfterViews
    void init() {
        if (event == null) {
            return;
        }

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.eventFragmentContainer, EventFragment_.builder().event(event).build());
        if (mapFragment == null) {
            mapFragment = MapFragment_.builder().event(null).build();
            mapFragment.setMapFragmentActionListener(this);
            mapFragment.setRetainInstance(true);
        }
        fragmentTransaction.replace(R.id.mapFragmentContainer, mapFragment, MAP_FRAGMENT_TAG);

        fragmentTransaction.commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                if (resultCode == -1) {
                    mapFragment.getCurrentLocation();
                } else {
                    finish();
                }
                break;
        }
    }

    @Override
    public void permissionDenied() {
        finish();
    }
}
