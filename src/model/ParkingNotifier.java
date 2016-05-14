package model;

import model.member.Member;
import model.schedule.Location;


/**
 * A class representing a way to receive parking notifications must extend this class. 
 * @author David Lerner
 */
public abstract class ParkingNotifier {

    /**
     * Returns a list of new parking notifications as Strings.
     * @param location The location that has the parking lot to get notifications from.
     * @param member The member who will be receiving the notifications
     * @return a list of new parking notifications as Strings
     */
    public abstract String[] getParkingMessages(Location location, Member member);
    
    /**
     * Adds new parking notifications to a member.
     * @param location The location that has the parking lot to get notifications from.
     * @param member The member who will be receiving the notifications 
     */
    public void addNewParkingNotifcations(Location location, Member member) {
        for (String s : getParkingMessages(location, member))
            member.addNewNotification(new Notification("Parking Notification: "+s));
    }
}
