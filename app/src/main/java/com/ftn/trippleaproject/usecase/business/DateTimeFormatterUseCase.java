package com.ftn.trippleaproject.usecase.business;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeFormatterUseCase {

    private static final String DATE_TIME_FORMAT = "EEE, dd MMM yyyy hh:mm";

    private static final String DATE_FORMAT = "dd MMM yyyy";

    private static final String TIME_FORMAT = "hh:mm";

    private static final String EVENT_START_MONTH_FORMAT = "MMM";

    private static final String EVENT_START_DAY_FORMAT = "dd";

    public String dateFormat(Date date) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return simpleDateFormat.format(date);
    }

    public String timeFormat(Date time) {
        final SimpleDateFormat simpleDateFormat =  new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());
        return simpleDateFormat.format(time);
    }

    public String dateTimeFormat(Date dateTime) {
        final SimpleDateFormat simpleDateFormat =  new SimpleDateFormat(DATE_TIME_FORMAT, Locale.getDefault());
        return simpleDateFormat.format(dateTime);
    }

    public String eventStartMonthFormat(Date date) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(EVENT_START_MONTH_FORMAT, Locale.getDefault());
        return simpleDateFormat.format(date).toUpperCase();
    }

    public String eventStartDayFormat(Date date) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(EVENT_START_DAY_FORMAT, Locale.getDefault());
        return simpleDateFormat.format(date).toUpperCase();
    }
}
