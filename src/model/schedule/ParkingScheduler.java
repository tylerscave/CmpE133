package model.schedule;

import java.util.ArrayList;
import java.util.List;
import model.Notification;
import model.member.Member;

/**
 * Scheduler for one-time parking.
 * @author David Lerner
 */
public class ParkingScheduler extends Scheduler {

    @Override
    public String schedule(Request request, Schedulable s) {
        String fail = correctData(request);
        //make sure data is correct
        if (!fail.equals(SUCCESS))
            return fail;
        if (!(s instanceof ParkingTime))
            return "Failure: No parking spot selected";
        ParkingTime park = (ParkingTime) s;
        
        //get latest member data
        Member member = data.getMember(s.getMemberId());
        List<ParkingTime> parkingTimes = data.getParkingTimes();
        //check if no conflicts
        if (!noParkingConflicts(park, parkingTimes))
            return "Failure: Parking spot already reserved";
        
        /*//may not want to check for this 
        for (Drive d : member.getDrives()) {
            if (d.conflicts(park))
                return "Failure: Conflict with prior schedule";
        }
        //may not want to check for this 
        for (Ride r : member.getRides()) {
            if (r.conflicts(park))
                return "Failure: Conflict with prior schedule";
        }
        for (ParkingTime p : member.getParkingTimes()) {
            if (p.conflicts(park))
                return "Failure: Conflict with prior schedule";
        }*/
        
        park.setIdNumber(data.getNewSchedulableId());
        member.getParkingTimes().add(park);
        
        //auto-send notification
        StringBuilder sb = new StringBuilder();
        sb.append("You have reserved a parking spot at ").append(park.getLocation()).append(" on ").append(getDateFromCalendar(park.getStartTime())).append(System.lineSeparator());
        sb.append("Details: ").append(park.getParkingSpot()).append(". From ").append(getTimeFromCalendar(park.getStartTime())).append(" to ").append(getTimeFromCalendar(park.getEndTime()));
        member.addNewNotification(new Notification(sb.toString()));

        //update & return
        member.setChanged();
        member.notifyObservers();
        
        return SUCCESS;
    }

    /**
     * Returns a list of available parking spots based on the parking request.
     * @param request
     * @return a list of available parking spots based on the parking request
     */
    @Override
    public List<Schedulable> getAvailable(Request request) {
        List<Schedulable> available = new ArrayList<>();
        String fail = correctData(request);
        //make sure data is correct
        if (!fail.equals(SUCCESS))
            return available;
        Member member = request.getMember();
        
        List<ParkingSpot> parkingSpots = request.getStartLocation().getParkingSpots();
        List<ParkingTime> parkingTimes = data.getParkingTimes();
        boolean timeConflictChecked = false;
        //make sure there are no conflicts
        for (ParkingSpot ps : parkingSpots) {
            ParkingTime park = new ParkingTime(member, request.getStartTime(), request.getEndTime(), request.getStartLocation(), ps);
            if (noParkingConflicts(park, parkingTimes))
                available.add(park);
            if (timeConflictChecked)
                continue;
            /*//may not want to check for this 
            for (Drive d : member.getDrives()) {
                if (d.conflicts(park))
                    return new ArrayList<>();
            }
            //may not want to check for this 
            for (Ride r : member.getRides()) {
                if (r.conflicts(park))
                    return new ArrayList<>();
            }
            
            for (ParkingTime p : member.getParkingTimes()) {
                if (p.conflicts(park))
                    return new ArrayList<>();
            }*/ 
            timeConflictChecked = true;
        }
        return available;
    }

    /**
     * Returns whether park has any time/space conflicts with pts.
     * @param park the parking time in question
     * @param pts a list of other parking times
     * @return whether park has any time/space conflicts with pts 
     */
    private boolean noParkingConflicts(ParkingTime park, List<ParkingTime> pts) {
        for (ParkingTime p : pts) {
            if (p.conflicts(park) && (p.getLocation().equals(park.getLocation()) && p.getParkingSpot().equals(park.getParkingSpot())))
                return false; 
        }    
        return true;
    }

}
