package model;

import java.util.GregorianCalendar;

/**
 *
 * @author David
 */
public class StringFormat {
    public static String getDateFromCalendar(GregorianCalendar gc) {
        return gc.get(GregorianCalendar.MONTH)+"/"+gc.get(GregorianCalendar.DATE)+"/"+gc.get(GregorianCalendar.YEAR);
    }
    
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
