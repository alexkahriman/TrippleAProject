package com.ftn.trippleaproject.usecase.business;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DateTimeFormatterUseCaseTest {

    private final DateTimeFormatterUseCase dateTimeFormatterUseCase = new DateTimeFormatterUseCase();

    private Date date = new Date();

    @Before
    public void setUp() {
        // Arrange
        String string = "Sun, Jun 3, 2018 12:20";
        DateFormat format = new SimpleDateFormat("EEE, MMMM d, yyyy hh:mm", Locale.getDefault());
        date = new Date();
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDateFormat() {
        // Acts
        final String dateText = dateTimeFormatterUseCase.dateFormat(date);

        // Assert
        assertThat(dateText, equalTo("03 Jun 2018"));
    }

    @Test
    public void testTimeFormat() {
        // Acts
        final String timeText = dateTimeFormatterUseCase.timeFormat(date);

        // Assert
        assertThat(timeText, equalTo("12:20"));
    }

    @Test
    public void testEventStartMonthFormat() {
        // Acts
        final String eventStartMonthText = dateTimeFormatterUseCase.eventStartMonthFormat(date);

        // Assert
        assertThat(eventStartMonthText, equalTo("JUN"));
    }

    @Test
    public void testEventStarTDayFormat() {
        // Acts
        final String eventStartDayText = dateTimeFormatterUseCase.eventStartDayFormat(date);

        // Assert
        assertThat(eventStartDayText, equalTo("03"));
    }

    @Test
    public void testDateTimeFormat() {
        // Acts
        final String dateTimeText = dateTimeFormatterUseCase.dateTimeFormat(date);

        // Assert
        assertThat(dateTimeText, equalTo("Sun, 03 Jun 2018 12:20"));
    }
}
