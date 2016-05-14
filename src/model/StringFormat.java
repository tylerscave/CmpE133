package model;

import java.util.GregorianCalendar;

/**
 * Helper class for formatting dates/times as Strings
 * @author David Lerner
 */
public class StringFormat {

    /**
     * Returns the date as a String (mm/dd/yyyy). 
     * @param gc the Gregorian Calendar date/time to print
     * @return the date as a String (mm/dd/yyyy)
     */
    public static String getDateFromCalendar(GregorianCalendar gc) {
        return Integer.toString(gc.get(GregorianCalendar.MONTH)+1)+"/"+gc.get(GregorianCalendar.DATE)+"/"+gc.get(GregorianCalendar.YEAR);
    }
    
    /**
     * Returns the time as a String (hh:mm AM/PM).
     * @param gc the Gregorian Calendar date/time to print
     * @return the time as a String (hh:mm AM/PM)
     */
    public static String getTimeFromCalendar(GregorianCalendar gc) {
        String ampm[] = new String[2];
        ampm[0] = " AM";
        ampm[1] = " PM";
        int hour = gc.get(GregorianCalendar.HOUR);
        if (hour == 0)
            hour = 12;
        String minute = Integer.toString(gc.get(GregorianCalendar.MINUTE));
        if (minute.length() == 1)
            minute = "0"+minute;
        return hour+":"+minute+ampm[gc.get(GregorianCalendar.AM_PM)];
    }
    
}
