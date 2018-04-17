package com.ftn.trippleaproject.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.system.PrefManager_;

@EFragment(R.layout.fragment_settings)
public class SettingsFragment extends Fragment {

    private SettingsNavigationSelected settingsNavigationSelected;

    @ViewById
    CheckBox checkBox;

    @Pref
    PrefManager_ prefManager;

    private AlertDialog tosDialog;

    private AlertDialog aboutDialog;

    @AfterViews
    void init() {

        checkBox.setChecked(prefManager.startWithEvents().get());
        buildTosDialog();
        buildAboutDialog();
    }

    @Click
    void newsFeedCardView() {
        if (settingsNavigationSelected != null) {
            settingsNavigationSelected.newsFeedSelected();
        }
    }

    @Click
    void eventsCardView() {
        if (settingsNavigationSelected != null) {
            settingsNavigationSelected.eventsSelected();
        }
    }

    @Click
    void appModeCardView() {
        checkBox.setChecked(!checkBox.isChecked());
        setAppMode();
    }

    @Click
    void checkBox() {
        checkBox.setChecked(checkBox.isChecked());
        setAppMode();
    }

    @Click
    void tosCardView() {
        if (tosDialog != null) {
            tosDialog.show();
        }
    }

    @Click
    void aboutCardView() {
        if (aboutDialog != null) {
            aboutDialog.show();
        }
    }

    void setAppMode() {
        prefManager.startWithEvents().put(checkBox.isChecked());
    }

    public void setSettingsNavigationSelected(SettingsNavigationSelected settingsNavigationSelected) {
        this.settingsNavigationSelected = settingsNavigationSelected;
    }

    public interface SettingsNavigationSelected {
        void newsFeedSelected();
        void eventsSelected();
    }

    private void buildTosDialog() {
        Context context = getContext();
        if (context == null) {
            return;
        }

        final String chars = getContext().getResources().getString(R.string.terms_of_service);
        final SpannableString str = new SpannableString(chars);
        str.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, chars.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(str);

        final RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.dialog_terms_of_service, null);
        final Button ok = relativeLayout.findViewById(R.id.ok);
        final TextView email = relativeLayout.findViewById(R.id.email);
        final TextView phone = relativeLayout.findViewById(R.id.phone);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tosDialog.dismiss();
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEMail(email.getText().toString());
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(phone.getText().toString());
            }
        });

        dialogBuilder.setView(relativeLayout);
        tosDialog = dialogBuilder.create();

        int textColorId = getResources().getIdentifier("alertMessage", "id", "android");
        final TextView textColor = tosDialog.findViewById(textColorId);
        if (textColor != null) {
            textColor.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        }

        final Window window = tosDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.round_button);
        }
    }

    private void buildAboutDialog() {
        Context context = getContext();
        if (context == null) {
            return;
        }

        final String chars = getContext().getResources().getString(R.string.about);
        final SpannableString str = new SpannableString(chars);
        str.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, chars.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(str);

        final RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.dialog_terms_of_service, null);
        final Button ok = relativeLayout.findViewById(R.id.ok);
        final TextView email = relativeLayout.findViewById(R.id.email);
        final TextView phone = relativeLayout.findViewById(R.id.phone);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aboutDialog.dismiss();
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEMail(email.getText().toString());
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPhone(phone.getText().toString());
            }
        });

        dialogBuilder.setView(relativeLayout);
        aboutDialog = dialogBuilder.create();

        int textColorId = getResources().getIdentifier("alertMessage", "id", "android");
        final TextView textColor = aboutDialog.findViewById(textColorId);
        if (textColor != null) {
            textColor.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        }

        final Window window = aboutDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.round_button);
        }
    }

    private void sendEMail(String mail) {
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        final String[] to = {mail};
        intent.putExtra(Intent.EXTRA_EMAIL, to);
        intent.setType("message/rfc822");
        Intent chooser = Intent.createChooser(intent, "Send E-Mail");
        this.startActivity(chooser);
    }

    private void callPhone(String phone) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        this.startActivity(intent);
    }
}