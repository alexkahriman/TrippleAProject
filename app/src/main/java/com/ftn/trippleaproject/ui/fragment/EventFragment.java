package com.ftn.trippleaproject.ui.fragment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.TrippleAApplication;
import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.usecase.business.DateTimeFormatterUseCase;
import com.ftn.trippleaproject.usecase.repository.EventUseCase;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.IgnoreWhen;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

@EFragment(R.layout.fragment_event)
public class EventFragment extends Fragment implements Consumer<Event> {

    @App
    TrippleAApplication application;

    @FragmentArg
    Event event;

    @ViewById
    TextView title;

    @ViewById
    TextView description;

    @ViewById
    TextView time;

    @ViewById
    TextView endTime;

    @ViewById
    TextView location;

    @ViewById
    TextView contact;

    @ViewById
    TextView dateStartMonth;

    @ViewById
    TextView dateStartDay;

    @Inject
    DateTimeFormatterUseCase dateTimeFormatterUseCase;

    @Inject
    EventUseCase eventUseCase;

    @AfterViews
    void init() {
        application.getDiComponent().inject(this);

        if (event == null) {
            return;
        }

        eventUseCase.read(event.getId()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);
    }

    private void setupUI() {
        title.setText(event.getTitle());
        description.setText(event.getDescription());
        time.setText(dateTimeFormatterUseCase.dateTimeFormat(event.getDate()));
        endTime.setText(dateTimeFormatterUseCase.dateTimeFormat(event.getEndDate()));
        contact.setText(event.getOwner());

        dateStartDay.setText(dateTimeFormatterUseCase.eventStartDayFormat(event.getDate()));
        dateStartMonth.setText(dateTimeFormatterUseCase.eventStartMonthFormat(event.getDate()));

        final Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            final List<Address> addresses = geocoder.getFromLocation(event.getLocation().getLatitude(),
                    event.getLocation().getLongitude(), 1);
            final String cityName = addresses.get(0).getAddressLine(0);

            location.setText(cityName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Click
    void contactLayout() {
        sendEMail(event.getOwner());
    }

    private void sendEMail(String mail) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        final String[] to = {mail};
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.setType("message/rfc822");
        final Intent chooser = Intent.createChooser(intent, "Send E-Mail");
        this.startActivity(chooser);
    }

    @IgnoreWhen(IgnoreWhen.State.VIEW_DESTROYED)
    @Override
    public void accept(Event event) throws Exception {
        this.event = event;
        setupUI();
    }
}
