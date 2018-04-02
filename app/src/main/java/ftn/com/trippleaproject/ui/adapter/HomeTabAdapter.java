package ftn.com.trippleaproject.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

import ftn.com.trippleaproject.ui.fragment.EventsFragment_;
import ftn.com.trippleaproject.ui.fragment.NewsFeedFragment_;
import ftn.com.trippleaproject.ui.fragment.SettingsFragment_;

@EBean
public class HomeTabAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragments = new ArrayList<>();

    HomeTabAdapter(Context context) {

        super(((AppCompatActivity) context).getSupportFragmentManager());

        fragments.add(NewsFeedFragment_.builder().build());

        fragments.add(EventsFragment_.builder().build());

        fragments.add(SettingsFragment_.builder().build());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return  null;
    }
}
