package model.schedule;

import java.util.GregorianCalendar;
import java.util.List;
import model.Context;
import model.DataHandler;
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
    
    public String getScheduleText(Member member) {
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
        if (member.getRequests().isEmpty())
            sb.append("None").append(System.lineSeparator());
        for (Request r : member.getRequests()) {
            if (r.getRequestType() != Request.RequestType.RIDE)
                continue;
            sb.append("Ride request details:").append(System.lineSeparator());
            if (r.getStartType() == Request.TimeType.Anytime)
                sb.append("  Depart from ").append(r.getStartLocation()).append(" at AnyTime").append(System.lineSeparator());
            else
                sb.append("  Depart from ").append(r.getStartLocation()).append(" ").append(r.getStartType().name()).append(" ").append(getTimeFromCalendar(r.getStartTime())).append(" ").append(getDateFromCalendar(r.getStartTime())).append(System.lineSeparator());
            if (r.getEndType() == Request.TimeType.Anytime)
                sb.append("  Arrive at ").append(r.getEndLocation()).append(" at AnyTime").append(System.lineSeparator());
            else
                sb.append("  Arrive at ").append(r.getEndLocation()).append(" ").append(r.getEndType().name()).append(" ").append(getTimeFromCalendar(r.getEndTime())).append(" ").append(getDateFromCalendar(r.getEndTime())).append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }
    
    private String getDateFromCalendar(GregorianCalendar gc) {
        return gc.get(GregorianCalendar.MONTH)+"/"+gc.get(GregorianCalendar.DATE)+"/"+gc.get(GregorianCalendar.YEAR);
    }
    
    private String getTimeFromCalendar(GregorianCalendar gc) {
        String ampm[] = new String[2];
        ampm[0] = " AM";
        ampm[1] = " PM";
        String minute = Integer.toString(gc.get(GregorianCalendar.MINUTE));
        if (minute.length() == 1)
            minute = "0"+minute;
        return gc.get(GregorianCalendar.HOUR)+":"+minute+ampm[gc.get(GregorianCalendar.AM_PM)];
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
}
