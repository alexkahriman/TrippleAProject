package com.ftn.trippleaproject.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.TrippleAApplication;
import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.ui.activity.EventActivity_;
import com.ftn.trippleaproject.ui.adapter.EventsOnMapAdapter;
import com.ftn.trippleaproject.ui.view.EventOnMapItemView;
import com.ftn.trippleaproject.usecase.repository.EventUseCase;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.IgnoreWhen;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

@EFragment(R.layout.fragment_events_on_map)
public class EventsOnMapFragment extends Fragment implements EventOnMapItemView.EventOnMapActionListener,
        Consumer<List<Event>> {

    @App
    TrippleAApplication trippleAApplication;

    @Inject
    EventUseCase eventUseCase;

    @Bean
    EventsOnMapAdapter eventsOnMapAdapter;

    @ViewById
    RecyclerView recyclerView;

    private GoogleMap map;

    private List<Marker> markers = new ArrayList<>();

    private SupportMapFragment mapFragment;

    private Marker previousMarker;

    private Marker currentMarker;

    private LinearLayoutManager layoutManager;

    @AfterViews
    void init() {
        trippleAApplication.getDiComponent().inject(this);

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(googleMap -> {
                map = googleMap;
                eventUseCase.readAllLocal().observeOn(AndroidSchedulers.mainThread()).subscribe(this);

                map.setOnMarkerClickListener(marker -> {
                    for (int i = 0; i < markers.size(); i++) {
                        if (marker.hashCode() == markers.get(i).hashCode()) {
                            recyclerView.smoothScrollToPosition(i);
                            highlightEventOnMap(i);
                            break;
                        }
                    }

                    return false;
                });
            });
        }

        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        eventsOnMapAdapter.setEventOnMapActionListener(this);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(eventsOnMapAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    highlightEventOnMap(layoutManager.findFirstCompletelyVisibleItemPosition());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            }
        });

        eventsOnMapAdapter.setEventOnMapActionListener(this);
    }

    private void highlightEventOnMap(int position) {
        if (position != RecyclerView.NO_POSITION) {
            previousMarker = currentMarker;
            currentMarker = markers.get(position);
            previousMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            currentMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            previousMarker.hideInfoWindow();
            currentMarker.showInfoWindow();
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(markers.get(position).getPosition(), 18.0f));
        } else {
            highlightEventOnMap(layoutManager.findLastVisibleItemPosition());
        }
    }

    @Override
    public void eventSelected(Event event) {
        EventActivity_.intent(getContext()).event(event).start();
    }

    @IgnoreWhen(IgnoreWhen.State.VIEW_DESTROYED)
    @Override
    public void accept(List<Event> events) {
        eventsOnMapAdapter.setEvents(events);
        arrangeMarkers(events);
    }

    private void arrangeMarkers(List<Event> events) {
        for (Event event : events) {
            markers.add(map.addMarker(new MarkerOptions()
                    .position(new LatLng(event.getLocation().getLatitude(), event.getLocation().getLongitude()))
                    .title(event.getTitle())));
        }

        previousMarker = markers.get(0);
        currentMarker = markers.get(0);
        highlightEventOnMap(0);
    }
}
