package model.schedule;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import model.Context;
import model.DataHandler;
import model.StringFormat;
import model.member.Driver;
import model.member.Member;
import model.payment.CreditCard;
import model.payment.Reward;

/**
 * Helpful class for getting schedule info from the dataHandler. 
 * @author David Lerner
 */
public class ScheduleViewer {
    private DataHandler data;

    public ScheduleViewer() {
        data = Context.getInstance().getDataHandler();
    }
    
    /**
     * Returns a String containing information about m's schedule.
     * @param m 
     * @return information about m's schedule
     */
    public String getScheduleText(Member m) {
        //get latest info
        Member member = data.getMember(m.getIdNumber());
        String nl = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        //print weekly schedule
        sb.append("Weekly Schedule:").append(nl);
        String[] weekNames = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        Location central = Context.getInstance().getCentral(); 
        WeeklySchedule ws = member.getWeeklySchedule();
        if (!ws.isSet())
            sb.append("None").append(nl);
        else {
            for (int i = 1; i <= 7; i++) {
                if (ws.isValid(i)) {
                    sb.append(weekNames[i-1]).append(":").append(nl);
                    sb.append("  Arrive at ").append(central).append(" from ").append(ws.getPickupLocation()).append(" at ").append(getTimeFromCalendar(ws.getArrive(i))).append(nl);
                    sb.append("  Depart from ").append(central).append(" to ").append(ws.getPickupLocation()).append(" at ").append(getTimeFromCalendar(ws.getDepart(i))).append(nl);
                    if (!ws.isDrive(i))
                        sb.append("  Not");
                    sb.append("  Available to drive").append(nl);
                }
            }
        }
        sb.append(nl);
        //print drives
        sb.append("Scheduled Drives:").append(nl);
        if (member.getDrives().isEmpty())
            sb.append("None").append(nl);
        for (int i = 0; i < member.getDrives().size(); i++) {
            Drive d = member.getDrives().get(i);
            sb.append("Drive details:").append(nl);
            List<Location> stops = d.getStops();
            sb.append("  ").append(stops.get(0)).append(" at ").append(getTimeFromCalendar(d.getStartTime())).append(" to ").append(stops.get(stops.size()-1)).append(" at ").append(getTimeFromCalendar(d.getEndTime())).append(" on ").append(getDateFromCalendar(d.getStartTime())).append(nl);
            if (stops.size() > 2) {
                sb.append("  Stops at: ");
                for (int j = 1; j < stops.size()-1; j++) {
                    sb.append(stops.get(j)).append(", ");
                }
                sb.append(nl);
            }
            sb.append("  ").append(d.getNumSeats()).append(" seats available").append(nl);
            sb.append("  Passengers:").append(nl);
            if (d.numberOfRides() == 0)
                sb.append("    None").append(nl);
            for (int j = 0; j < d.numberOfRides(); j++) {
                Ride ride = (Ride) data.getSchedulable(d.getRideId(j));
                List<Location> rideStops = ride.getStops();
                sb.append("    ").append(ride.getMemberName()).append(": ");
                sb.append(rideStops.get(0)).append(" at ").append(getTimeFromCalendar(ride.getStartTime())).append(" to ");
                sb.append(rideStops.get(rideStops.size()-1)).append(" at ").append(getTimeFromCalendar(ride.getEndTime())).append(nl);
                sb.append("      Ride Status: ").append(ride.getRideStatus().getStatus()).append(nl);
            }
        }
        sb.append(nl);
        //print parking reservations
        sb.append("Scheduled Parking:").append(nl);
        if (member.getParkingTimes().isEmpty())
            sb.append("None").append(nl);
        for (ParkingTime p : member.getParkingTimes()) {
            sb.append("Reserved Parking: ").append(p.getLocation()).append(", ").append(p.getParkingSpot()).append(nl);
            sb.append("  ").append(getTimeFromCalendar(p.getStartTime())).append(" to ").append(getTimeFromCalendar(p.getEndTime())).append(" on ").append(getDateFromCalendar(p.getStartTime())).append(nl);
        }
        sb.append(nl);
        
        sb.append("Scheduled Rides:").append(nl);
        if (member.getRides().isEmpty())
            sb.append("None").append(nl);
        for (Ride r : member.getRides()) {
            sb.append("Ride details:").append(nl);
            List<Location> stops = r.getStops();
            sb.append("  ").append(stops.get(0)).append(" at ").append(getTimeFromCalendar(r.getStartTime())).append(" to ");
            sb.append(stops.get(stops.size()-1)).append(" at ").append(getTimeFromCalendar(r.getEndTime())).append(" on ").append(getDateFromCalendar(r.getStartTime())).append(nl);
            Drive drive = (Drive) data.getSchedulable(r.getDriveId());
            sb.append("  Passenger in ").append(drive.getMemberName()).append("'s vehicle").append(nl);
            sb.append("  Ride Status: ").append(r.getRideStatus().getStatus()).append(nl);
        }
        sb.append(nl);
        //print rides
        sb.append("Ride Requests:").append(nl);
        if (member.getRequests().isEmpty())
            sb.append("None").append(nl);
        for (Request r : member.getRequests()) {
            if (r.getRequestType() != Request.RequestType.RIDE)
                continue;
            sb.append("Ride request details:").append(nl);
            if (r.getStartType() == Request.TimeType.Anytime)
                sb.append("  Depart from ").append(r.getStartLocation()).append(" at AnyTime").append(nl);
            else
                sb.append("  Depart from ").append(r.getStartLocation()).append(" ").append(r.getStartType().name()).append(" ").append(getTimeFromCalendar(r.getStartTime())).append(" ").append(getDateFromCalendar(r.getStartTime())).append(nl);
            if (r.getEndType() == Request.TimeType.Anytime)
                sb.append("  Arrive at ").append(r.getEndLocation()).append(" at AnyTime").append(nl);
            else
                sb.append("  Arrive at ").append(r.getEndLocation()).append(" ").append(r.getEndType().name()).append(" ").append(getTimeFromCalendar(r.getEndTime())).append(" ").append(getDateFromCalendar(r.getEndTime())).append(nl);
        }
        sb.append(nl);
        
        return sb.toString();
    }
    
    private String getDateFromCalendar(GregorianCalendar gc) {
        return StringFormat.getDateFromCalendar(gc);
    }
    
    private String getTimeFromCalendar(GregorianCalendar gc) {
        return StringFormat.getTimeFromCalendar(gc);
    }
    
    /**
     * Get a ride by it's id.
     * @param id a unique schedulable id
     * @return a ride by it's id
     */
    public Ride getRideById(int id) {
        return (Ride)data.getSchedulable(id);
    }
    
    /**
     * Get a drive by it's id.
     * @param id a unique schedulable id
     * @return a drive by it's id
     */
    public Drive getDriveById(int id) {
        return (Drive)data.getSchedulable(id);
    }
    
    /**
     * Get a parking reservation by it's id.
     * @param id a unique schedulable id
     * @return a parking reservation by it's id
     */
    public ParkingTime getParkById(int id) {
        return (ParkingTime)data.getSchedulable(id);
    }
    
    /**
     * Returns a list of rides m needs to pay for.
     * @param m
     * @return a list of rides m needs to pay for
     */
    public List<Ride> getRidesToPay(Member m) {
        Member member = data.getMember(m.getIdNumber());
        List<Ride> rides = new ArrayList<>();
        for (Ride r : member.getRides()) {
            if (!r.getRideStatus().isUnpaid())
                continue;
            Member driver = data.getMember(getDriveById(r.getDriveId()).getMemberId());
            if (!driver.getDrivingType().isDriver())
                continue;
            Driver d = (Driver) driver.getDrivingType();
            Reward reward = new CreditCard(null, d.getPayBy());
            double amount = (Double)reward.findReward(driver, r);
            r.setDescription(String.format("Amount to pay: $%.2f", amount));
            rides.add(r);
            }
        return rides;
    }
    
    /**
     * Returns a list of rides m needs to be paid for.
     * @param m
     * @return a list of rides m needs to be paid for
     */
    public List<Ride> getRidesToBePaid(Member m) {
        Member member = data.getMember(m.getIdNumber());
        List<Ride> rides = new ArrayList<>();
        if (!member.getDrivingType().isDriver())
            return rides;
        Driver driver = (Driver) member.getDrivingType();
        Reward reward = new CreditCard(null, driver.getPayBy());
        for (Drive d : member.getDrives()) {
            for (int i = 0; i < d.numberOfRides(); i++) {
                Ride ride = (Ride) data.getSchedulable(d.getRideId(i));
                if (ride.getRideStatus().isUnpaid()) {
                    double amount = (Double)reward.findReward(member, ride);
                    ride.setDescription(String.format("Amount you are owed: $%.2f", amount));
                    rides.add(ride);
                }
            }
        }
        return rides;
    }
}
