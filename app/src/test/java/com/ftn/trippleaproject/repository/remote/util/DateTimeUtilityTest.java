package com.ftn.trippleaproject.repository.remote.util;

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
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class DateTimeUtilityTest {

    private final DateTimeUtility dateTimeUtility = new DateTimeUtility();

    private Date date = new Date();

    private static final String dateText = "2018-06-03T12:05:05Z";

    @Before
    public void setUp() {
        // Arrange
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        date = new Date();
        try {
            date = format.parse(dateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testConvertDate() {
        //Act
        final Date date = dateTimeUtility.convertDate(dateText);

        // Assert
        assertThat(date, equalTo(this.date));
    }

    @Test
    public void testConvertDateFail() {
        //Act
        final Date date = dateTimeUtility.convertDate("Testing to fail");

        // Assert
        assertThat(date, not(this.date));
    }

    @Test
    public void testConvertToDate() {
        //Act
        final String dateText = dateTimeUtility.convertToDate(date);

        // Assert
        assertThat(dateText, equalTo(DateTimeUtilityTest.dateText));
    }
}
