package com.ftn.trippleaproject.usecase.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeFormatterUseCase {

    private static final String DATE_TIME_FORMAT = "EEE, dd MMM yyyy HH:mm";

    private static final String DATE_FORMAT = "dd MMM yyyy";

    private static final String TIME_FORMAT = "HH:mm";

    public String dateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public String timeFormat(Date time) {
        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
        return simpleDateFormat.format(time);
    }

    public String dateTimeFormat(Date dateTime) {
        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());
        return simpleDateFormat.format(dateTime);
    }
}
