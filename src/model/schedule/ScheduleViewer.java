package model.schedule;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import model.Context;
import model.DataHandler;
import model.StringFormat;
import model.member.Member;

/**
 *
 * @author David
 */
public class ScheduleViewer {
    private DataHandler data;

    public ScheduleViewer() {
        data = Context.getInstance().getDataHandler();
    }
    
    //legacy
    public String getScheduleTextOld(Member member) {
        StringBuilder sb = new StringBuilder();
        sb.append("Drives:").append(System.lineSeparator());
        if (member.getDrives().isEmpty())
            sb.append("None").append(System.lineSeparator());
        for (int i = 0; i < member.getDrives().size(); i++) {
            Drive d = member.getDrives().get(i);
            sb.append("Drive details:").append(System.lineSeparator());
            Route route = d.getRoute();
            List<Location> stops = route.getStops();
            sb.append("  ").append(stops.get(0)).append(" at ").append(getTimeFromCalendar(route.getStartTime())).append(" to ").append(stops.get(stops.size()-1)).append(" at ").append(getTimeFromCalendar(route.getEndTime())).append(" on ").append(getDateFromCalendar(route.getEndTime())).append(System.lineSeparator());
            if (stops.size() > 2) {
                sb.append("  Stops at: ");
                for (int j = 1; j < stops.size()-1; j++) {
                    sb.append(stops.get(j)).append(", ");
                }
                sb.append(System.lineSeparator());
            }
            sb.append("  ").append(d.getNumSeats()).append(" seats available").append(System.lineSeparator());
            sb.append("  Passengers:").append(System.lineSeparator());
            if (d.numberOfRides() == 0)
                sb.append("    None").append(System.lineSeparator());
            for (int j = 0; j < d.numberOfRides(); j++) {
                Ride ride = (Ride) data.getSchedulable(d.getRideId(j));
                Route rideRoute = ride.getRoute();
                sb.append("    ").append(ride.getMemberName()).append(": ");
                sb.append(rideRoute.getStops().get(0)).append(" at ").append(getTimeFromCalendar(rideRoute.getStartTime())).append(" to ");
                sb.append(rideRoute.getStops().get(stops.size()-1)).append(" at ").append(getTimeFromCalendar(rideRoute.getEndTime())).append(System.lineSeparator());
            }
            Location status = route.getLocationAtTime(new GregorianCalendar());
            if (status == null)
                status = new Location("Not yet started");
            sb.append("  Current Status: ").append(status).append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        
        sb.append("Rides:").append(System.lineSeparator());
        if (member.getRides().isEmpty())
            sb.append("None").append(System.lineSeparator());
        for (Ride r : member.getRides()) {
            sb.append("Ride details:").append(System.lineSeparator());
            Route route = r.getRoute();
            List<Location> stops = route.getStops();
            sb.append("  ").append(stops.get(0)).append(" at ").append(getTimeFromCalendar(route.getStartTime())).append(" to ");
            sb.append(stops.get(stops.size()-1)).append(" at ").append(getTimeFromCalendar(route.getEndTime())).append(" on ").append(getDateFromCalendar(route.getEndTime())).append(System.lineSeparator());
            Drive drive = (Drive) data.getSchedulable(r.getDriveId());
            sb.append("  Passenger in ").append(drive.getMemberName()).append("'s vehicle").append(System.lineSeparator());
            Location status = route.getLocationAtTime(new GregorianCalendar());
            if (status == null)
                status = new Location("Not yet started");
            sb.append("  Current Status: ").append(status).append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        
        sb.append("Ride Requests:").append(System.lineSeparator());
        if (member.getRideRequests().isEmpty())
            sb.append("None").append(System.lineSeparator());
        for (RideRequest rr : member.getRideRequests()) {
            sb.append("Ride request details:").append(System.lineSeparator());
            if (rr.getStartType() == RideRequest.TimeType.AnyTime)
                sb.append("  Depart from ").append(rr.getStartLocation()).append(" at AnyTime").append(System.lineSeparator());
            else
                sb.append("  Depart from ").append(rr.getStartLocation()).append(" ").append(rr.getStartType().name()).append(" ").append(getTimeFromCalendar(rr.getStartTime())).append(" ").append(getDateFromCalendar(rr.getStartTime())).append(System.lineSeparator());
            if (rr.getEndType() == RideRequest.TimeType.AnyTime)
                sb.append("  Arrive at ").append(rr.getEndLocation()).append(" at AnyTime").append(System.lineSeparator());
            else
                sb.append("  Arrive at ").append(rr.getEndLocation()).append(" ").append(rr.getEndType().name()).append(" ").append(getTimeFromCalendar(rr.getEndTime())).append(" ").append(getDateFromCalendar(rr.getEndTime())).append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }
    
    public String getScheduleText(Member m) {
        Member member = data.getMember(m.getIdNumber());
        String nl = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        
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
    
    public Ride getRideById(int id) {
        return (Ride)data.getSchedulable(id);
    }
    
    public Drive getDriveById(int id) {
        return (Drive)data.getSchedulable(id);
    }
    
    public ParkingTime getParkById(int id) {
        return (ParkingTime)data.getSchedulable(id);
    }
    
    public List<Ride> getRidesToPay(Member m) {
        Member member = data.getMember(m.getIdNumber());
        List<Ride> rides = new ArrayList<>();
        for (Ride r : member.getRides()) {
            if (r.getRideStatus().isUnpaid())
                rides.add(r);
        }
        return rides;
    }
    
    public List<Ride> getRidesToBePaid(Member m) {
        Member member = data.getMember(m.getIdNumber());
        List<Ride> rides = new ArrayList<>();
        for (Drive d : member.getDrives()) {
            for (int i = 0; i < d.numberOfRides(); i++) {
                Ride ride = (Ride) data.getSchedulable(d.getRideId(i));
                if (ride.getRideStatus().isUnpaid())
                    rides.add(ride);
            }
        }
        return rides;
    }
}
