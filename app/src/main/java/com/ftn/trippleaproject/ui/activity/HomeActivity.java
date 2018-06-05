package com.ftn.trippleaproject.ui.activity;

import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.system.DeleteDataJobService;
import com.ftn.trippleaproject.system.PrefManager_;
import com.ftn.trippleaproject.ui.adapter.HomeTabAdapter;
import com.ftn.trippleaproject.ui.fragment.SettingsFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity implements SettingsFragment.SettingsNavigationSelected {

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 11;

    private static final int PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 12;

    @ViewById
    TabLayout tabLayout;

    @ViewById
    ViewPager pager;

    @Bean
    HomeTabAdapter homeTabAdapter;

    @Pref
    PrefManager_ prefManager;

    private final int[] tabIcons = {
            R.drawable.ic_news,
            R.drawable.ic_calendar,
            R.drawable.ic_settings
    };

    @AfterViews
    void init() {
        DeleteDataJobService.scheduleDeleteDataJobService(this);

        homeTabAdapter.setSettingsNavigationSelected(this);
        pager.setAdapter(homeTabAdapter);
        pager.setOffscreenPageLimit(homeTabAdapter.getCount());
        tabLayout.setupWithViewPager(pager);
        setTabIcons();

        if (prefManager.startWithEvents().get()) {
            pager.setCurrentItem(1);
        }

        checkPermissions();
    }

    private void setTabIcons() {
        Drawable drawable;
        TabLayout.Tab tab;
        for (int i = 0; i < tabIcons.length; i++) {
            drawable = this.getDrawable(tabIcons[i]);
            if (drawable != null) {
                drawable.setTintList(getResources().getColorStateList(R.color.tab_icon));

                tab = tabLayout.getTabAt(i);

                if (tab != null) {
                    tab.setIcon(drawable);
                }
            }
        }
    }

    public void checkPermissions() {
        if (!checkLocationPermission()) {
            getLocationPermission();
        }
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
                    finish();
                }
                break;
            }
            case PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                } else {
                    finish();
                }
            }
        }
    }

    @Override
    public void newsFeedSelected() {
        pager.setCurrentItem(0, true);
    }

    @Override
    public void eventsSelected() {
        pager.setCurrentItem(1, true);
    }

}
