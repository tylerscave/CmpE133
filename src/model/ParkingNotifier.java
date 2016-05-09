package model;

import model.member.Member;
import model.schedule.Location;


/**
 *
 * @author David
 */
public abstract class ParkingNotifier {
    public abstract String[] getParkingMessages(Location location, Member member);
    
    public void addNewParkingNotifcations(Location location, Member member) {
        for (String s : getParkingMessages(location, member))
            member.addNewNotification(new Notification("Parking Notification: "+s));
    }
}
