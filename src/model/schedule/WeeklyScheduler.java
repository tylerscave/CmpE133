package model.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import model.member.Member;

/**
 *
 * @author David
 */
public class WeeklyScheduler extends Scheduler{

    @Override
    public String schedule(Request r, Schedulable s) {
        String fail = correctData(r);
        if (!fail.equals(SUCCESS))
            return fail;
        Member member = r.getMember();
        WeeklySchedule ws = member.getWeeklySchedule();
        if (ws.getStartTime() == null)
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
            if (!ws.isDrive(scheduleTime.get(GregorianCalendar.DAY_OF_WEEK)))
                continue;
            GregorianCalendar arrive = ws.getArrive(scheduleTime.get(GregorianCalendar.DAY_OF_WEEK));
            
            GregorianCalendar locationTime = new GregorianCalendar();
            locationTime.setTime(scheduleTime.getTime());
            locationTime.add(GregorianCalendar.HOUR, arrive.get(GregorianCalendar.HOUR));
            locationTime.add(GregorianCalendar.MINUTE, arrive.get(GregorianCalendar.MINUTE));
            Request request = new Request(member, locationTime, locationTime, ws.getPickupLocation(), location, Request.TimeType.Anytime, Request.TimeType.Near);
            RideScheduler rs = new RideScheduler();
            List<Schedulable> drives = rs.getAvailable(request);
            if (drives.size() > 0) {
                //choose random driver to be fair
                Collections.shuffle(drives);
                if (!rs.schedule(request, drives.get(0)).equals(Scheduler.SUCCESS)) {
                    saveRequest(request, "Weekly ride request");
                }
                scheduleRideBack(scheduleTime, member, location);
            }
            else if (!r.getMember().getDrivingType().isDriver()) {
                saveRequest(request, "Weekly ride request");
                scheduleRideBack(scheduleTime, member, location);
            }
            else {
                DriveScheduler ds = new DriveScheduler();
                request = new Request(member, locationTime, ws.getPickupLocation(), location, Request.TimeType.Near, false);
                ds.schedule(request, s);
                
                GregorianCalendar depart = ws.getDepart(scheduleTime.get(GregorianCalendar.DAY_OF_WEEK));
                GregorianCalendar locationTime2 = new GregorianCalendar();
                locationTime2.setTime(scheduleTime.getTime());
                locationTime2.add(GregorianCalendar.HOUR, depart.get(GregorianCalendar.HOUR));
                locationTime2.add(GregorianCalendar.MINUTE, depart.get(GregorianCalendar.MINUTE));
                request = new Request(member, locationTime2, location, ws.getPickupLocation(), Request.TimeType.Near, true);
                ds.schedule(request, s);
                
                ParkingScheduler ps = new ParkingScheduler();
                request = new Request(member, locationTime, locationTime2, location);
                List<Schedulable> parks = ps.getAvailable(request);
                Collections.shuffle(parks);
                if (parks.size() > 0)
                    ps.schedule(request, parks.get(0));
            }
        }
        
        return SUCCESS;
    }

    private void scheduleRideBack(GregorianCalendar scheduleTime, Member member, Location location) {
        WeeklySchedule ws = member.getWeeklySchedule();
        RideScheduler rs = new RideScheduler();        
        GregorianCalendar depart = ws.getDepart(scheduleTime.get(GregorianCalendar.DAY_OF_WEEK));
        
        GregorianCalendar time = new GregorianCalendar();
        time.setTime(scheduleTime.getTime());
        time.add(GregorianCalendar.HOUR, depart.get(GregorianCalendar.HOUR));
        time.add(GregorianCalendar.MINUTE, depart.get(GregorianCalendar.MINUTE));
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
    
    private void saveRequest(Request request, String name) {
        SchedulingContext sc = new SchedulingContext();
        sc.addToRequests(request, name);
    }
    
    @Override
    public List<Schedulable> getAvailable(Request r) {
        return new ArrayList<>();
    }

}
