package com.ftn.trippleaproject.repository.remote.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtility {

    private static final String DATE_TIME_REMOTE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public Date convertMongoDate(String val) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(DATE_TIME_REMOTE_FORMAT, Locale.getDefault());
        try {
            return inputFormat.parse(val);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public String convertToMongoDate(Date date) {
        SimpleDateFormat outputForm = new SimpleDateFormat(DATE_TIME_REMOTE_FORMAT, Locale.getDefault());
        return outputForm.format(date);
    }
}
