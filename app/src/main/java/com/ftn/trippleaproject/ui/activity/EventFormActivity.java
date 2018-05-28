package com.ftn.trippleaproject.ui.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.ui.fragment.EventFormFragment;
import com.ftn.trippleaproject.ui.fragment.EventFormFragment_;
import com.ftn.trippleaproject.ui.fragment.MapFragment;
import com.ftn.trippleaproject.ui.fragment.MapFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentByTag;

@EActivity(R.layout.actrivity_event_form)
public class EventFormActivity extends AppCompatActivity implements EventFormFragment.EventFormFragmentActionListener {

    @Extra
    Event event;

    @AfterViews
    void init() {
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        final EventFormFragment eventFormFragment = EventFormFragment_.builder().event(event).build();
        fragmentTransaction.replace(R.id.eventFormFragmentContainer, eventFormFragment);
        eventFormFragment.setEventFormFragmentActionListener(this);

        fragmentTransaction.commit();
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
