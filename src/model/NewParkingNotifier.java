package model;

import model.member.Member;
import model.schedule.Location;

/**
 *
 * @author David
 */
public class NewParkingNotifier extends ParkingNotifier{

    @Override
    public String[] getParkingMessages(Location location, Member member) {
        //stub
        return new String[0];
    }
    
}
