package ftn.com.trippleaproject.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DateSetActionListener dateSetActionListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void setDateSetActionListener(DateSetActionListener dateSetActionListener) {
        this.dateSetActionListener = dateSetActionListener;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {


        if (dateSetActionListener == null) {
            return;
        }

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        dateSetActionListener.dateSet(calendar);
    }

    public interface DateSetActionListener {
        void dateSet(Calendar date);
    }
}