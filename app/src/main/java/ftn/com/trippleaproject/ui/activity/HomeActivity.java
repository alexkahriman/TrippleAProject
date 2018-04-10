package ftn.com.trippleaproject.ui.activity;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import ftn.com.trippleaproject.R;
import ftn.com.trippleaproject.ui.adapter.HomeTabAdapter;

@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity {

    @ViewById
    TabLayout tabLayout;

    @ViewById
    ViewPager pager;

    @Bean
    HomeTabAdapter homeTabAdapter;

    private final int[] tabIcons = {
            R.drawable.ic_news,
            R.drawable.ic_calendar,
            R.drawable.ic_settings
    };

    @AfterViews
    void init() {
        pager.setAdapter(homeTabAdapter);
        tabLayout.setupWithViewPager(pager);
        setTabIcons();
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
}
