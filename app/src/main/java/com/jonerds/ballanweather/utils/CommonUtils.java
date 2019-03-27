package com.jonerds.ballanweather.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.jonerds.ballanweather.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

    public static int getHourAsInt(String time){

        try {
            SimpleDateFormat hourFormat = new SimpleDateFormat("hh:mm aa");
            Date date = null;
            date = hourFormat.parse(time);
            return date.getHours() * 100;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public static int getHoursAsInt(Date date){
        return date.getHours() * 100;
    }

    public static int getHoursAsInt(){
        Date date = new Date();
        return date.getHours() * 100;
    }

    public static long getMidnightDateAsLong(Date date){
        return date.getTime() - date.getTime() % (24 * 60 * 60 * 1000);
    }

    public static long getMidnightDateAsLong(){
        Date date = new Date();
        return date.getTime() - date.getTime() % (24 * 60 * 60 * 1000);
    }

    public static long getDateAsLong(String time){

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
