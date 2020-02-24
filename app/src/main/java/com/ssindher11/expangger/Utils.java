package com.ssindher11.expangger;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static void makeToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void makeSnackbar(View parent, String msg) {
        Snackbar.make(parent, msg, Snackbar.LENGTH_SHORT).show();
    }

    public static void showErrorSnackbar(View parent, String message) {
        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.colorErrorSnackbar);
        snackbar.show();
    }

    public static long getTimeInMillis(String date){
        Calendar c = Calendar.getInstance();
        c.set(Integer.parseInt(date.split("-")[2]),Integer.parseInt(date.split("-")[1])-1,Integer.parseInt(date.split("-")[0]));
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE,1);
        c.set(Calendar.SECOND,1);
        c.set(Calendar.MILLISECOND,0);
        return c.getTimeInMillis();
    }

    public static String getFormattedDateToday(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return sdf.format(c.getTimeInMillis());
    }

    public static int getMonthFromString(String s) {
        try {
            Date d = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(s);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            return cal.get(Calendar.MONTH);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
