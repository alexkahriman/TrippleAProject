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
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ftn.trippleaproject.R;
import com.ftn.trippleaproject.TrippleAApplication;
import com.ftn.trippleaproject.system.PrefManager_;
import com.ftn.trippleaproject.ui.activity.LoginActivity_;
import com.ftn.trippleaproject.usecase.repository.AuthenticationUseCase;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import javax.inject.Inject;

@EFragment(R.layout.fragment_settings)
public class SettingsFragment extends Fragment {

    @App
    TrippleAApplication application;

    @ViewById
    CheckBox checkBox;

    @Pref
    PrefManager_ prefManager;

    private SettingsNavigationSelected settingsNavigationSelected;

    private Context context;

    private AlertDialog tosDialog;

    private AlertDialog aboutDialog;

    @Inject
    AuthenticationUseCase authenticationUseCase;

    @AfterViews
    void init() {
        application.getDiComponent().inject(this);
        checkBox.setChecked(prefManager.startWithEvents().get());
        this.context = getContext();
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
    void logoutCardView() {
        authenticationUseCase.deleteToken();
        LoginActivity_.intent(this)
                .flags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                .start();
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
        if (context == null) {
            return;
        }

        final String chars = getString(R.string.terms_of_service);
        final SpannableString str = new SpannableString(chars);
        str.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, chars.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(str);

        final RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.dialog_terms_of_service, null);
        final Button ok = relativeLayout.findViewById(R.id.ok);
        final TextView email = relativeLayout.findViewById(R.id.email);
        final TextView phone = relativeLayout.findViewById(R.id.phone);

        ok.setOnClickListener(view -> tosDialog.dismiss());

        email.setOnClickListener(view -> sendEMail(email.getText().toString()));

        phone.setOnClickListener(view -> callPhone(phone.getText().toString()));

        dialogBuilder.setView(relativeLayout);
        tosDialog = dialogBuilder.create();

        final int textColorId = getResources().getIdentifier("alertMessage", "id", "android");
        final TextView textColor = tosDialog.findViewById(textColorId);
        if (textColor != null) {
            textColor.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }

        final Window window = tosDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.round_dialog);
        }
    }

    private void buildAboutDialog() {
        if (context == null) {
            return;
        }

        final String chars = getString(R.string.about);
        final SpannableString str = new SpannableString(chars);
        str.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), 0, chars.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(str);

        final RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.dialog_terms_of_service, null);
        final Button ok = relativeLayout.findViewById(R.id.ok);
        final TextView email = relativeLayout.findViewById(R.id.email);
        final TextView phone = relativeLayout.findViewById(R.id.phone);

        ok.setOnClickListener(view -> aboutDialog.dismiss());

        email.setOnClickListener(view -> sendEMail(email.getText().toString()));

        phone.setOnClickListener(view -> callPhone(phone.getText().toString()));

        dialogBuilder.setView(relativeLayout);
        aboutDialog = dialogBuilder.create();

        final int textColorId = getResources().getIdentifier("alertMessage", "id", "android");
        final TextView textColor = aboutDialog.findViewById(textColorId);
        if (textColor != null) {
            textColor.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }

        final Window window = aboutDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawableResource(R.drawable.round_dialog);
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