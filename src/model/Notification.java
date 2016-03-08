package model;

import java.util.GregorianCalendar;

/**
 *
 * @author David
 */
public class Notification {
    private String message;
    private boolean read;
    private GregorianCalendar time;

    public Notification(String message) {
        this.message = message;
        this.read = false;
        this.time = new GregorianCalendar();
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public GregorianCalendar getTime() {
        return time;
    }
    
}
