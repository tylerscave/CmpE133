package model.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import model.member.Member;

/**
 * Weekly scheduler.
 * @author David Lerner
 */
public class WeeklyScheduler extends Scheduler{

    @Override
    public String schedule(Request r, Schedulable s) {
        String fail = correctData(r);
        //make sure data is correct
        if (!fail.equals(SUCCESS))
            return fail;
        Member member = r.getMember();
        //schedule one time drive/ride/parks based on weekly schedule
        WeeklySchedule ws = member.getWeeklySchedule();
        if (!ws.isSet())
            return "Failure: No weekly schedule";
        Location location = r.getStartLocation();
        
        List<GregorianCalendar> startList = new ArrayList<>();
        GregorianCalendar currentTime = new GregorianCalendar();
        startList.add(currentTime);
        startList.add(r.getStartTime());
        startList.add(ws.getStartTime());
        //start is the max of current time, starting date, and the last time the weekly rides were scheduled 
        GregorianCalendar startTime = Collections.max(startList);
        
        GregorianCalendar endTime = new GregorianCalendar();
        endTime.setTime(currentTime.getTime());
        //set final date to current+7
        endTime.add(GregorianCalendar.DATE, 7);
        //clear out time component
        endTime = new GregorianCalendar(endTime.get(GregorianCalendar.YEAR), 
                endTime.get(GregorianCalendar.MONTH), endTime.get(GregorianCalendar.DATE));
        //make final scheduled date inclusive 
        GregorianCalendar ret = new GregorianCalendar();
        ret.setTime(ws.getEndTime().getTime());
        ret.add(GregorianCalendar.DATE, 1);
        //final is min of the 2 dates
        if (ret.before(endTime))
            endTime = ws.getEndTime();
        //set last time scheduled to new endtime
        ws.setLastUpdate(endTime);
        
        GregorianCalendar scheduleTime = new GregorianCalendar(startTime.get(GregorianCalendar.YEAR), 
                startTime.get(GregorianCalendar.MONTH), startTime.get(GregorianCalendar.DAY_OF_MONTH));
        //preset loop counter
        scheduleTime.add(GregorianCalendar.DATE, -1);
        for (int i = 0; i < 7; i++) {
            //increment time for loop
            scheduleTime.add(GregorianCalendar.DATE, 1);
            //end when reached endtime
            if (!scheduleTime.before(endTime))
                break;
            //skip if nothing scheduled
            if (!ws.isValid(scheduleTime.get(GregorianCalendar.DAY_OF_WEEK)))
                continue;
            GregorianCalendar arrive = ws.getArrive(scheduleTime.get(GregorianCalendar.DAY_OF_WEEK));
            //find arrival time at location
            GregorianCalendar locationTime = new GregorianCalendar();
            locationTime.setTime(scheduleTime.getTime());
            locationTime.add(GregorianCalendar.HOUR_OF_DAY, arrive.get(GregorianCalendar.HOUR_OF_DAY));
            locationTime.add(GregorianCalendar.MINUTE, arrive.get(GregorianCalendar.MINUTE));
            //create ride request
            Request request = new Request(member, locationTime, locationTime, ws.getPickupLocation(), location, Request.TimeType.Anytime, Request.TimeType.Near);
            RideScheduler rs = new RideScheduler();
            List<Schedulable> drives = rs.getAvailable(request);
            //schedule ride in another' vehicle if available
            if (drives.size() > 0) {
                //choose random driver to be fair
                Collections.shuffle(drives);
                if (!rs.schedule(request, drives.get(0)).equals(Scheduler.SUCCESS)) {
                    saveRequest(request, "Weekly ride request");
                }
                scheduleRideBack(scheduleTime, member, location);
            }            
            else if (r.getMember().getDrivingType().isDriver() && ws.isDrive(scheduleTime.get(GregorianCalendar.DAY_OF_WEEK))){
                //schedule a drive if available to drive and a member is a driver
                DriveScheduler ds = new DriveScheduler();
                request = new Request(member, locationTime, ws.getPickupLocation(), location, Request.TimeType.Near, false);
                ds.schedule(request, s);
                
                //schedule drive for departure as well
                GregorianCalendar depart = ws.getDepart(scheduleTime.get(GregorianCalendar.DAY_OF_WEEK));
                GregorianCalendar locationTime2 = new GregorianCalendar();
                locationTime2.setTime(scheduleTime.getTime());
                locationTime2.add(GregorianCalendar.HOUR_OF_DAY, depart.get(GregorianCalendar.HOUR_OF_DAY));
                locationTime2.add(GregorianCalendar.MINUTE, depart.get(GregorianCalendar.MINUTE));
                request = new Request(member, locationTime2, location, ws.getPickupLocation(), Request.TimeType.Near, true);
                ds.schedule(request, s);
                
                //schedule parking as well
                ParkingScheduler ps = new ParkingScheduler();
                request = new Request(member, locationTime, locationTime2, location);
                List<Schedulable> parks = ps.getAvailable(request);
                //choose random available parking spot
                Collections.shuffle(parks);
                if (parks.size() > 0)
                    ps.schedule(request, parks.get(0));
            }
            else {
                //save ride requests otherwise
                saveRequest(request, "Weekly ride request");
                scheduleRideBack(scheduleTime, member, location);
            }
        }
        
        return SUCCESS;
    }

    //for scheduling ride back from central location
    private void scheduleRideBack(GregorianCalendar scheduleTime, Member member, Location location) {
        WeeklySchedule ws = member.getWeeklySchedule();
        RideScheduler rs = new RideScheduler();        
        GregorianCalendar depart = ws.getDepart(scheduleTime.get(GregorianCalendar.DAY_OF_WEEK));
        //find departure time at location
        GregorianCalendar time = new GregorianCalendar();
        time.setTime(scheduleTime.getTime());
        time.add(GregorianCalendar.HOUR_OF_DAY, depart.get(GregorianCalendar.HOUR_OF_DAY));
        time.add(GregorianCalendar.MINUTE, depart.get(GregorianCalendar.MINUTE));
        //create ride request and schedule if possible, save request otherwise
        Request request = new Request(member, time, time, location, ws.getPickupLocation(), Request.TimeType.Near, Request.TimeType.Anytime);
        List<Schedulable> drives = rs.getAvailable(request);
        if (drives.size() > 0) {
            //choose random
            Collections.shuffle(drives);
            if (!rs.schedule(request, drives.get(0)).equals(Scheduler.SUCCESS)) {
                saveRequest(request, "Weekly ride request");
            } 
        }
        else {
            saveRequest(request, "Weekly ride request");
        }           
    }
    
    //save ride request
    private void saveRequest(Request request, String name) {
        SchedulingContext sc = new SchedulingContext();
        sc.addRideToRequests(request, name);
    }
    
    //return empty list
    @Override
    public List<Schedulable> getAvailable(Request r) {
        return new ArrayList<>();
    }

}
