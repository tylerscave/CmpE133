package main;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import model.Context;
import model.Data;
import model.Drive;
import model.DriveChoice;
import model.Driver;
import model.GraphMap;
import model.Location;
import model.Member;
import model.Passenger;
import model.Ride;
import model.RideRequest;
import model.Route;
import model.Student;
import model.Vehicle;

/**
 *
 * @author David
 */
public class ScheduleTester {

    private static Context context;
    private static Data data;
    private static List<Member> members;
    private static GraphMap map;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        context = Context.getInstance();
        data = Data.getInstance();
        members = data.getMembers();
        map = (GraphMap)context.getMap();
        menu();
    }
    
    private static void menu() {
        boolean exit = false;
        while (! exit) {
            System.out.println("Schedule Tester");
            System.out.println("Get started by entering one of the following commands:");
            System.out.println("1: Create new account");
            System.out.println("2: Set a Drive");
            System.out.println("3: Request a Ride");
            System.out.println("4: View Schedule");
            System.out.println("5: View Map");
            System.out.println("0: Exit");
            Scanner in = new Scanner(System.in);
            int input = in.nextInt();
            switch (input) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    createAccount();
                    break;
                case 2:
                    setDrive();
                    break;
                case 3:
                    viewRideRequest();
                    break;
                case 4:
                    viewSchedule();
                    break;
                case 5:
                    viewMap();
                    break;
                default:
                    break;
            }
        }
        System.exit(0);
    }

    private static void createAccount() {
        System.out.println("Enter first name");
        Scanner in = new Scanner(System.in);
        String firstName = in.nextLine();
        System.out.println("Enter last name");
        String lastName = in.nextLine();
        System.out.println("Driver [y/n]");
        String input = in.nextLine();
        if (input.equals("y")) {
            members.add(new Student(null, lastName, firstName, null, lastName, new Driver("", new Vehicle("", 4), null), null));
        }
        else {
            members.add(new Student(null, lastName, firstName, null, lastName, new Passenger(null, null), null));
        }
        System.out.println("New Account created!");
        
        in.nextLine();
    }

    private static void setDrive() {
        Scanner in = new Scanner(System.in);
        Member member = selectMember();
        System.out.println("Choose departure location rhgada");
        for (int i = 0; i < map.getLocations().size(); i++)
            System.out.println(i + ": " + map.getLocations().get(i));
        int startLocation = in.nextInt();
        System.out.println("Choose destination location");
        int endLocation = in.nextInt();
        System.out.println("Set by:");
        System.out.println("0: Departure time");
        System.out.println("1: Destination time");
        String input = in.nextLine();
        boolean byStartTime;
        if (input.equals("1")) {
            System.out.println("Enter destination time mm/dd/yyyy/xx:xx");
            byStartTime = false;
        }
        else {
            System.out.println("Enter departure time mm/dd/yyyy/xx:xx");
            byStartTime = true;
        }
        input = in.nextLine();
        int month = Integer.parseInt(input.substring(0, 2));
        int day = Integer.parseInt(input.substring(3, 5));
        int year = Integer.parseInt(input.substring(6, 10));
        int hour = Integer.parseInt(input.substring(11, 13));
        int minute = Integer.parseInt(input.substring(14));
        GregorianCalendar time = new GregorianCalendar(year, month, day, hour, minute);
        RideRequest rideRequest = new RideRequest(member, time, time, map.getLocationFromIndex(startLocation), map.getLocationFromIndex(endLocation), RideRequest.TimeType.AnyTime, RideRequest.TimeType.AnyTime);
        boolean success;
        if (byStartTime)
            success = rideRequest.generateDriveByStartTime();
        else
            success = rideRequest.generateDriveByEndTime();
        if (success)
            System.out.println("New Drive Scheduled!");
        else
            System.out.println("Drive Scheduling failed");
        
        in.nextLine();
    }

    private static void viewRideRequest() {
        Scanner in = new Scanner(System.in);        
        Member member = selectMember();
        System.out.println("Request a Ride:");
        System.out.println("0: New Ride Request");
        List<RideRequest> rideRequests = member.getRideRequests();
        for (int i = 0; i < rideRequests.size(); i++) {
            System.out.println(Integer.toString(i+1)+": "+rideRequests.get(i).getName());
        }
        int index = in.nextInt();
        if (index == 0)
            setRideRequest(member);
        else
            rideRequestResults(rideRequests.get(index-1));
    }
    
    private static void setRideRequest(Member member) {
        Scanner in = new Scanner(System.in);
        System.out.println("Choose departure location");
        for (int i = 0; i < map.getLocations().size(); i++)
            System.out.println(i + ": " + map.getLocations().get(i).toString());
        int startLocation = in.nextInt();
        System.out.println("Choose how to use a departure time");
        RideRequest.TimeType StartType = RideRequest.TimeType.AnyTime;
        RideRequest.TimeType[] types = RideRequest.TimeType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.print(i+": "+types[i].name());
            if (types[i] != RideRequest.TimeType.AnyTime)
                System.out.print(" a time");
            System.out.println();
        }
        int index = in.nextInt();
        for (int i = 0; i < types.length; i++) {
            if (i == index)
                StartType = types[i];
        }
        GregorianCalendar startTime = new GregorianCalendar();
        if (StartType != RideRequest.TimeType.AnyTime) {
            System.out.println("Enter departure time mm/dd/yyyy/xx:xx");    
            String input = in.nextLine();
            int month = Integer.parseInt(input.substring(0, 2));
            int day = Integer.parseInt(input.substring(3, 5));
            int year = Integer.parseInt(input.substring(6, 10));
            int hour = Integer.parseInt(input.substring(11, 13));
            int minute = Integer.parseInt(input.substring(14));
            startTime = new GregorianCalendar(year, month, day, hour, minute);
        }
        System.out.println("Choose destination location");
        for (int i = 0; i < map.getLocations().size(); i++)
            System.out.println(i + ": " + map.getLocations().toString());
        int endLocation = in.nextInt();
        System.out.println("Choose how to use a destination time");
        RideRequest.TimeType EndType = RideRequest.TimeType.AnyTime;
        for (int i = 0; i < types.length; i++) {
            System.out.print(i+": "+types[i].name());
            if (types[i] != RideRequest.TimeType.AnyTime)
                System.out.print(" a time");
            System.out.println();
        }
        index = in.nextInt();
        for (int i = 0; i < types.length; i++) {
            if (i == index)
                EndType = types[i];
        }
        GregorianCalendar endTime = new GregorianCalendar();
        if (StartType != RideRequest.TimeType.AnyTime) {
            System.out.println("Enter departure time mm/dd/yyyy/xx:xx");    
            String input = in.nextLine();
            int month = Integer.parseInt(input.substring(0, 2));
            int day = Integer.parseInt(input.substring(3, 5));
            int year = Integer.parseInt(input.substring(6, 10));
            int hour = Integer.parseInt(input.substring(11, 13));
            int minute = Integer.parseInt(input.substring(14));
            startTime = new GregorianCalendar(year, month, day, hour, minute);
        }
        
        RideRequest rideRequest = new RideRequest(member, startTime, endTime, map.getLocationFromIndex(startLocation), map.getLocationFromIndex(endLocation), StartType, EndType);
        rideRequestResults(rideRequest);
    }
    
    private static void rideRequestResults(RideRequest rideRequest) {
        String name = rideRequest.getName();
        if (name == null)
            name = "New Ride Request";
        System.out.println(name+" details:");
        if (rideRequest.getStartType() == RideRequest.TimeType.AnyTime)
            System.out.println("Must depart from "+rideRequest.getStartLocation().toString()+" at AnyTime");
        else
            System.out.println("Must depart from "+rideRequest.getStartLocation().toString()+" "+rideRequest.getStartType().name()+" "+rideRequest.getStartTime().toString());
        if (rideRequest.getEndType() == RideRequest.TimeType.AnyTime)
            System.out.println("Must arrive at "+rideRequest.getEndLocation().toString()+" at AnyTime");
        else
            System.out.println("Must arrive at "+rideRequest.getEndLocation().toString()+" "+rideRequest.getEndType().name()+" "+rideRequest.getEndTime().toString());
        Scanner in = new Scanner(System.in);
        in.nextLine();
        
        List<DriveChoice> driveChoices = rideRequest.getAvailableDriveChoices();
        System.out.println("Choose an Available Drive: ("+driveChoices.size()+")");
        System.out.println("0: Save Ride Request and return");
        for (int i = 0; i < driveChoices.size(); i++) {
            Drive drive = driveChoices.get(i).getDrive();
            Route route = driveChoices.get(i).getRoute();
            System.out.println(Integer.toString(i+1)+": "+drive.getMember().getFirstName()+" "+drive.getMember().getLastName()+
                    ". "+drive.getNumSeats()+" seats available. "+route.getStartTime().get(GregorianCalendar.HOUR)+":"+route.getStartTime().get(GregorianCalendar.MINUTE)+
                    " to "+route.getStartTime().get(GregorianCalendar.HOUR)+":"+route.getStartTime().get(GregorianCalendar.MINUTE));
        }
        int index = in.nextInt();
        if (index == 0) {
            if (rideRequest.getName() == null) {
                System.out.println("Enter a name for the Ride Request: ");
                name = in.nextLine();
            }
            rideRequest.addToRequests(name);
        }
        else {
            boolean success = rideRequest.generateRide(driveChoices.get(index-1).getDrive());
            if (success)
                System.out.println("New Ride Scheduled!");
            else
                System.out.println("Ride Scheduling failed");
        }
    }

    private static void viewSchedule() {
        Scanner in = new Scanner(System.in);
        Member member = selectMember();
        System.out.println("Drives:");
        if (member.getDrives().isEmpty())
            System.out.println("None");
        for (Drive d : member.getDrives()) {
            System.out.println("Drive details:");
            Route route = d.getRoute();
            List<Location> stops = route.getStops();
            System.out.println(stops.get(0).toString()+" at "+route.getStartTime().toString()+" to "+stops.get(stops.size()-1).toString()+" at "+route.getEndTime().toString());
            if (stops.size() > 2) {
                System.out.print("Stops at: ");
                for (int i = 1; i < stops.size()-1; i++) {
                    System.out.print(stops.get(i).toString() + ", ");
                }
                System.out.println();
            }
            System.out.println(Integer.toString(d.getNumSeats()-d.numberOfRides()) + " seats available");
            System.out.println("Passengers:");
            if (d.numberOfRides() == 0)
                System.out.println("None");
            for (int i = 0; i < d.numberOfRides(); i++) {
                Ride ride = d.getRide(i);
                Route rideRoute = ride.getRoute();
                System.out.print(ride.getMember().getFirstName() + ride.getMember().getLastName() + ": ");
                System.out.println(rideRoute.getStops().get(0).toString()+" at "+rideRoute.getStartTime().toString()+" to "+rideRoute.getStops().get(stops.size()-1).toString()+" at "+rideRoute.getEndTime().toString());
            }
        }
        System.out.println();
        System.out.println("Rides:");
        if (member.getRides().isEmpty())
            System.out.println("None");
        for (Ride r : member.getRides()) {
            System.out.println("Ride details:");
            Route route = r.getRoute();
            List<Location> stops = route.getStops();
            System.out.println(stops.get(0).toString()+" at "+route.getStartTime().toString()+" to "+stops.get(stops.size()-1).toString()+" at "+route.getEndTime().toString());
            Drive drive = r.getDrive();
            System.out.println("Passenger in "+drive.getMember().getFirstName()+" "+drive.getMember().getLastName()+"'s vehicle");
        }
        System.out.println();
        System.out.println("Ride Requests:");
        if (member.getRideRequests().isEmpty())
            System.out.println("None");
        for (RideRequest rr : member.getRideRequests()) {
            System.out.println("Ride request details:");
            if (rr.getStartType() == RideRequest.TimeType.AnyTime)
                System.out.println("Must depart from "+rr.getStartLocation().toString()+" at AnyTime");
            else
                System.out.println("Must depart from "+rr.getStartLocation().toString()+" "+rr.getStartType().name()+" "+rr.getStartTime().toString());
            if (rr.getEndType() == RideRequest.TimeType.AnyTime)
                System.out.println("Must arrive at "+rr.getEndLocation().toString()+" at AnyTime");
            else
                System.out.println("Must arrive at "+rr.getEndLocation().toString()+" "+rr.getEndType().name()+" "+rr.getEndTime().toString());
        }
        System.out.println();
        
        in.nextLine();
    }
    
    private static void viewMap() {
        System.out.println(map.toString());
        Scanner in = new Scanner(System.in);
        
        in.nextLine();
    }
    
    private static Member selectMember() {
        System.out.println("Select Member");
        for (int i = 0; i < members.size(); i++) {
            System.out.println(i + ": " + members.get(i).getFirstName() + " " + members.get(i).getLastName());
        }
        Scanner in = new Scanner(System.in);
        int index = in.nextInt();
        return members.get(index);
    }
}
