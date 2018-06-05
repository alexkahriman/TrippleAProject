package com.ftn.trippleaproject.ui.fragment;

import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.TrippleAApplication;
import com.ftn.trippleaproject.domain.Event;
import com.ftn.trippleaproject.usecase.repository.EventUseCase;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.IgnoreWhen;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.ftn.trippleaproject.ui.activity.EventActivity.REQUEST_CHECK_SETTINGS;

@EFragment(R.layout.fragment_map)
public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, Consumer<Event> {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 11;

    private static final int PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 12;

    private static final int LOCATION_REQUEST_INTERVAL = 60000;

    private static final int LOCATION_REQUEST_SHORT_INTERVAL = 10000;

    private static final int LOCATION_REQUEST_FASTEST_INTERVAL = 5000;

    @App
    TrippleAApplication trippleAApplication;

    @Inject
    EventUseCase eventUseCase;

    @FragmentArg
    Event event;

    @FragmentArg
    boolean edit;

    private GoogleMap map;

    private Context context;

    private FusedLocationProviderClient fusedLocationProviderClient;

    private Location currentLocation;

    private MapFragmentActionListener mapFragmentActionListener;

    @AfterViews
    void init() {
        trippleAApplication.getDiComponent().inject(this);

        context = getContext();
        if (context != null) {
            fusedLocationProviderClient = new FusedLocationProviderClient(context);
        }
        this.getMapAsync(this);

        if (event != null) {
            eventUseCase.read(event.getId()).observeOn(AndroidSchedulers.mainThread()).subscribe(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        if (event != null) {
            showEventLocation();
        } else {
            getCurrentLocation();
        }

        if (event == null || edit) {
            map.setOnMapClickListener(latLng -> {
                currentLocation = new Location(LocationManager.GPS_PROVIDER);
                currentLocation.setLatitude(latLng.latitude);
                currentLocation.setLongitude(latLng.longitude);

                final MarkerOptions marker = new MarkerOptions().position(latLng).title(getGeoLocation(currentLocation));
                map.clear();
                map.addMarker(marker);
            });
        }
    }

    private void showEventLocation() {
        map.clear();
        final LatLng latLng = new LatLng(event.getLocation().getLatitude(), event.getLocation().getLongitude());
        final MarkerOptions marker = new MarkerOptions().position(latLng).title(event.getTitle());
        currentLocation = new Location(LocationManager.GPS_PROVIDER);
        currentLocation.setLatitude(event.getLocation().getLatitude());
        currentLocation.setLongitude(event.getLocation().getLongitude());
        map.addMarker(marker);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18.0f));
    }

    public void getCurrentLocation() {
        if (!checkLocationPermission()) {
            getLocationPermission();
        } else {
            displayLocationSettingsRequest();
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
                currentLocation = task.getResult();
                if (currentLocation == null) {
                    getLastLocation();
                } else {
                    showCurrentLocationOnMap();
                }
            });
        }
    }

    private void getLastLocation() {
        if (checkLocationPermission()) {
            final LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(LOCATION_REQUEST_INTERVAL);
            locationRequest.setFastestInterval(LOCATION_REQUEST_FASTEST_INTERVAL);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationCallback locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {
                            currentLocation = location;
                            showCurrentLocationOnMap();
                            return;
                        }
                    }
                }
            };

            LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(locationRequest, locationCallback, null);
        }
    }

    private void showCurrentLocationOnMap() {
        final MarkerOptions marker = new MarkerOptions().position(
                new LatLng(currentLocation.getLatitude(),
                        currentLocation.getLongitude()))
                .title(getGeoLocation(currentLocation));

        map.addMarker(marker);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), 18.0f));
    }

    private void getLocationPermission() {
        // Checks to see if the user has granted permissions for ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION
        // If they are granted, we can continue with location access, if not, user shall be prompted for their activation
        if (ContextCompat.checkSelfPermission(context.getApplicationContext(), ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(context.getApplicationContext(), ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Check to see if user has granted ACCESS_FINE_LOCATION, if not request permission for it
        if (ContextCompat.checkSelfPermission(context.getApplicationContext(), ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission for ACCESS_FINE_LOCATION
            requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }

        // Check to see if user has granted ACCESS_COARSE_LOCATION, if not request permission for it
        if (ContextCompat.checkSelfPermission(context.getApplicationContext(), ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission for ACCESS_COARSE_LOCATION
            requestPermissions(new String[]{ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                } else {
                    mapFragmentActionListener.permissionDenied();
                }
                break;
            }
            case PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                } else {
                    mapFragmentActionListener.permissionDenied();
                }
            }
        }
    }

    private String getGeoLocation(Location location) {
        final String cityName = "Current location";
        final Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            return addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
            return cityName;
        }
    }

    private void displayLocationSettingsRequest() {
        final GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        final LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(LOCATION_REQUEST_SHORT_INTERVAL);
        locationRequest.setFastestInterval(LOCATION_REQUEST_FASTEST_INTERVAL);

        final LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        final PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(result1 -> {
            final Status status = result1.getStatus();
            if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                try {
                    status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean checkLocationPermission() {
        return ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    public Location getLocation() {
        return currentLocation;
    }

    public void setMapFragmentActionListener(MapFragmentActionListener mapFragmentActionListener) {
        this.mapFragmentActionListener = mapFragmentActionListener;
    }

    @IgnoreWhen(IgnoreWhen.State.VIEW_DESTROYED)
    @Override
    public void accept(Event event) {
        this.event = event;
        showEventLocation();
    }

    public interface MapFragmentActionListener {
        void permissionDenied();
    }
}
