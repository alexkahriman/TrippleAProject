package ftn.com.trippleaproject.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.domain.Event;
import ftn.com.trippleaproject.ui.fragment.EventFragment_;

@EActivity(R.layout.activity_event)
public class EventActivity extends AppCompatActivity {

    @Extra
    Event event;

    @AfterViews
    void init() {

        if (event == null) {
            return;
        }

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.eventFragmentContainer, EventFragment_.builder().event(event).build());
        fragmentTransaction.commit();
    }
}
