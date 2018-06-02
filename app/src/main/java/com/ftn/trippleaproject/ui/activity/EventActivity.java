package com.ftn.trippleaproject.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.ui.fragment.EventFragment_;
import com.ftn.trippleaproject.ui.fragment.MapFragment;
import com.ftn.trippleaproject.ui.fragment.MapFragment_;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentByTag;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.Date;

@EActivity(R.layout.activity_event)
public class EventActivity extends AppCompatActivity implements MapFragment.MapFragmentActionListener {

    public static final int REQUEST_CHECK_SETTINGS = 33;

    private static final int MINUTES_UNTIL_EVENT_CAN_BE_EDITED = -30;

    @Extra
    Event event;

    @FragmentByTag(MAP_FRAGMENT_TAG)
    MapFragment mapFragment;

    @ViewById
    FloatingActionButton edit;

    private static final String MAP_FRAGMENT_TAG = "mapFragment";

    @AfterViews
    void init() {
        if (event == null) {
            return;
        }

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.eventFragmentContainer, EventFragment_.builder().event(event).build());
        mapFragment = MapFragment_.builder().event(event).build();
        mapFragment.setMapFragmentActionListener(this);
        mapFragment.setRetainInstance(true);
        fragmentTransaction.replace(R.id.mapFragmentContainer, mapFragment, MAP_FRAGMENT_TAG);

        fragmentTransaction.commit();

        checkEventCanBeEdited();
    }

    private void checkEventCanBeEdited() {
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, MINUTES_UNTIL_EVENT_CAN_BE_EDITED);
        if (event.getDate().after(calendar.getTime())) {
            checkLoggedInUser();
        } else {
            edit.setVisibility(View.GONE);
        }
    }

    private void checkLoggedInUser() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null && acct.getEmail() != null) {
            edit.setVisibility((acct.getEmail().equals(event.getOwner())) ? View.VISIBLE : View.GONE);
        } else {
            edit.setVisibility(View.GONE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                mapFragment.getCurrentLocation();
            } else {
                finish();
            }
        }
    }

    @Click
    void edit() {
        EventFormActivity_.intent(this).event(event).edit(true).start();
    }

    @Override
    public void permissionDenied() {
        finish();
    }
}
