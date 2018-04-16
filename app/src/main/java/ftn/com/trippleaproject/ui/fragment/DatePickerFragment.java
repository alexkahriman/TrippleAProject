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

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void setDateSetActionListener(DateSetActionListener dateSetActionListener) {
        this.dateSetActionListener = dateSetActionListener;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        if (dateSetActionListener != null) {
            final Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            dateSetActionListener.dateSet(calendar);
        }
    }

    public interface DateSetActionListener {
        void dateSet(Calendar date);
    }
}