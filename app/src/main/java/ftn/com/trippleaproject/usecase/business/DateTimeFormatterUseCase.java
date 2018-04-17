package ftn.com.trippleaproject.usecase.business;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static ftn.com.trippleaproject.ui.constatns.DateTimeFormatConstants.DATE_FORMAT;
import static ftn.com.trippleaproject.ui.constatns.DateTimeFormatConstants.DATE_TIME_FORMAT;
import static ftn.com.trippleaproject.ui.constatns.DateTimeFormatConstants.TIME_FORMAT;

public class DateTimeFormatterUseCase {

    public SimpleDateFormat dateFormat() {
        return new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    }

    public SimpleDateFormat timeFormat() {
        return new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
    }

    public SimpleDateFormat dateTimeFormat() {
        return new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());
    }
}
