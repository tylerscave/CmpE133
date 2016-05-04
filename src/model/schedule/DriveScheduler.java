package model.schedule;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import model.Driver;
import model.LocationMap;
import model.Member;
import model.Notification;

/**
 *
 * @author David
 */
public class DriveScheduler extends Scheduler{
    
    @Override
    public String schedule(Request r, Schedulable s) {
        Request request = r;
        String fail = correctData(request);
        if (!fail.equals(""))
            return fail;
        Member member = r.getMember();
        if (!member.getDrivingType().isDriver())
            return "Failure: You are not a driver";
        GregorianCalendar time;
        if (r.getStartType() == Request.TimeType.Anytime) {
            LocationMap map = context.getMap();
            time = map.getStartTime(request.getEndTime(), request.getStartLocation(), request.getEndLocation());
        }
        else {
            time = request.getStartTime();
        }
        return generateDrive(time, request);    
    }

    @Override
    public List<Schedulable> getAllAvailable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAvailable(Request r, Schedulable s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String generateDrive(GregorianCalendar time, Request request) {
        Route route = new Route(time, request.getStartLocation(), request.getEndLocation());
        Member member = request.getMember();
        for (Drive d : member.getDrives()) {
            if (d.getRoute().conflicts(route))
                return "Failure: Conflict with prior schedule";
        }
        for (Ride r : member.getRides()) {
            if (r.getRoute().conflicts(route))
                return "Failure: Conflict with prior schedule";
        }
        
        //already checked type in calling method
        Driver driver = (Driver) member.getDrivingType();
        Drive drive = new Drive(driver.getVehicle().getCapacity(), member);
        drive.setRoute(route);
        drive.setIdNumber(data.getNewSchedulableId());
        
        member.getDrives().add(drive);
        //toggle on/off with auto schedule code below
        List<Member> changed = new ArrayList<>();
        /*
        if (name != null)
            member.getRideRequests().remove(this);
        List<Member> changed = new ArrayList<>();
        List<Member> members = data.getMembers();
        for (Member m : members) {
            for (RideRequest r : m.getRideRequests()) {
                Ride ride = generateRide(drive);
                if (ride != null) {
                    drive.addRide(ride);
                    m.getRides().add(ride);
                    m.getRideRequests().remove(this);
                    m.addNewNotification(new Notification("You have a new driver!"));
                    changed.add(m);
                }
            }
        }
        */
        member.setChanged();
        member.notifyObservers(changed);
        
        return "Success";
    }
    
}
