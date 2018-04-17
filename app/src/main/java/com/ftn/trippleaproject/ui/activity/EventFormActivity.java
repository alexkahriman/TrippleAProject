package com.ftn.trippleaproject.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.ui.fragment.EventFormFragment;
import com.ftn.trippleaproject.ui.fragment.EventFormFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

@EActivity(R.layout.actrivity_event_form)
public class EventFormActivity extends AppCompatActivity implements EventFormFragment.EventFormFragmentActionListener {

    @Extra
    Event event;

    @AfterViews
    void init() {

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        EventFormFragment eventFormFragment = EventFormFragment_.builder().event(event).build();
        eventFormFragment.setEventFormFragmentActionListener(this);
        fragmentTransaction.replace(R.id.eventFormFragmentContainer, eventFormFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
