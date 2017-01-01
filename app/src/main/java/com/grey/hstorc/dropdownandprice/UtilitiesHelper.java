package com.grey.hstorc.dropdownandprice;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hstorc on 10/25/16.
 */
public class UtilitiesHelper {
    public final static String DbReadDateFormat="E MMM dd HH:mm:ss Z yyyy";

    public UtilitiesHelper(){

    }
    public static Date convertToDate(String val){
        DateFormat formatter = new SimpleDateFormat(UtilitiesHelper.myDateFormat, Locale.US);
        Date date = null;
        try {
            date = (Date)formatter.parse(val);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getTodayDate(){

        SimpleDateFormat formatter = new SimpleDateFormat(UtilitiesHelper.myDateFormat, Locale.US);
        //Calendar calander= new Calendar() {
        //}
        //Date _now = calander.getTime();

        return null;// date;
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat(UtilitiesHelper.myDateFormat);//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static final String myDateFormat="yyyy-MM-dd";

}
