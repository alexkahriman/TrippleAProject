package com.ftn.trippleaproject.ui.activity;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
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

@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity implements SettingsFragment.SettingsNavigationSelected {

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

    @Override
    public void newsFeedSelected() {
        pager.setCurrentItem(0, true);
    }

    @Override
    public void eventsSelected() {
        pager.setCurrentItem(1, true);
    }

}
