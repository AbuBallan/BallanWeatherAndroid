package com.jonerds.ballanweather;

import com.google.gson.JsonObject;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_date() throws ParseException {
        SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm aa");
        SimpleDateFormat outputHourFormat = new SimpleDateFormat("HH");

        int time = 0;

        Date date = hourFormat.parse("03:22 PM");
        time = date.getHours() * 100;
        assertTrue(time == 1500);
    }
}