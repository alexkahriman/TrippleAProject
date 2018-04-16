package ftn.com.trippleaproject.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.domain.Event;
import ftn.com.trippleaproject.ui.fragment.EventFormFragment;
import ftn.com.trippleaproject.ui.fragment.EventFormFragment_;

@EActivity(R.layout.actrivity_event_form)
public class EventFormActivity extends AppCompatActivity {

    @Extra
    Event event;

    @AfterViews
    void init() {

        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        EventFormFragment employeesFragment;
        if (event != null) {
            employeesFragment = EventFormFragment_.builder().event(event).build();
        } else {
            employeesFragment = EventFormFragment_.builder().build();
        }
        fragmentTransaction.replace(R.id.eventFormFragmentContainer, employeesFragment);
        fragmentTransaction.commit();
    }
}
