package com.ftn.trippleaproject.ui.activity;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public abstract  class BaseActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 11;

    private static final int PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 12;

    public boolean checkPermissions() {
        if (!checkLocationPermission()) {
            getLocationPermission();
        }

        return true;
    }

    private void getLocationPermission() {
        // Checks to see if the user has granted permissions for ACCESS_FINE_LOCATION and ACCESS_COARSE_LOCATION
        // If they are granted, we can continue with location access, if not, user shall be prompted for their activation
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Check to see if user has granted ACCESS_FINE_LOCATION, if not request permission for it
        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission for ACCESS_FINE_LOCATION
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return;
        }

        // Check to see if user has granted ACCESS_COARSE_LOCATION, if not request permission for it
        if (ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission for ACCESS_COARSE_LOCATION
            ActivityCompat.requestPermissions(this, new String[]{ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        }
    }

    private boolean checkLocationPermission() {
        return ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else {
                    onPermissionDenied();
                }
                break;
            }
            case PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else {
                    onPermissionDenied();
                }
            }
        }
    }

    protected abstract void onPermissionDenied();
}
