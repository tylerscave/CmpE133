package model;

import java.util.GregorianCalendar;

/**
 * The notifications a member can have. They can be sent by both the system and users.
 * @author David Lerner
 */
public class Notification {
    private String message;
    private GregorianCalendar time;

    public Notification(String message) {
        this.message = message;
        this.time = new GregorianCalendar();
    }

    /**
     *
     * @return the notification message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @return the time the notification was created
     */
    public GregorianCalendar getTime() {
        return time;
    }
    
}
