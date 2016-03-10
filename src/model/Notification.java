package model;

import java.util.GregorianCalendar;

/**
 *
 * @author David
 */
public class Notification {
    private String message;
    private GregorianCalendar time;

    public Notification(String message) {
        this.message = message;
        this.time = new GregorianCalendar();
    }

    public String getMessage() {
        return message;
    }

    public GregorianCalendar getTime() {
        return time;
    }
    
}
