package ftn.com.trippleaproject.ui.fragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private TimeSetActionListener timeSetActionListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void setTimeSetActionListener(TimeSetActionListener timeSetActionListener) {
        this.timeSetActionListener = timeSetActionListener;
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        if (timeSetActionListener == null) {
            return;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        timeSetActionListener.setTime(calendar);
    }

    public interface TimeSetActionListener {
        void setTime(Calendar time);
    }
}
