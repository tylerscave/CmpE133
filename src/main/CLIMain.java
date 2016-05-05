package main;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import model.Context;
import model.DataHandler;
import model.schedule.Drive;
import model.schedule.DriveChoice;
import model.member.Driver;
import model.schedule.Location;
import model.LocationMap;
import model.member.LoginInformation;
import model.member.Member;
import model.member.MemberBuilder;
import model.Notification;
import model.NotificationSender;
import model.member.Passenger;
import model.schedule.Ride;
import model.member.Vehicle;
import model.schedule.Request;
import model.schedule.Schedulable;
import model.schedule.ScheduleViewer;
import model.schedule.SchedulingContext;

/**
 *
 * @author David
 */
public class CLIMain {

    private static Context context;
    private static DataHandler data;
    private static LocationMap map;
    private static List<Location> locations;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        context = Context.getInstance();
        data = context.getDataHandler();
        map = context.getMap();
        locations = map.getLocations();
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
            System.out.println("6: Notifications");
            System.out.println("0: Exit");
            int option = getOptionIntFromInput(7);
            switch (option) {
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
                case 6:
                    Notifications();
                    break;
                default:
                    break;
            }
        }
        System.exit(0);
    }

    private static void createAccount() {
        Scanner in = new Scanner(System.in);
        MemberBuilder mb = new MemberBuilder();
        System.out.println("Enter first name");
        String fn = in.nextLine();
        mb.setFirstName(fn);
        System.out.println("Enter last name");
        String ln = in.nextLine();
        mb.setLastName(ln);
        System.out.println("Driver:");
        System.out.println("0: Yes");
        System.out.println("1: No");
        int option = getOptionIntFromInput(2);
        if (option == 0) 
            mb.setDrivingType(new Driver("", new Vehicle(2000, "", "", "", "", null, 4), null));
        else
            mb.setDrivingType(new Passenger(null, null));
        mb.setLoginInfo(new LoginInformation(fn, ln));
        
        if (mb.build() == -1)
            System.out.println("Failed to create new account");
        else
            System.out.println("New Account created!");
        System.out.println("Press Enter to continue...");
        in.nextLine();
    }

    private static void setDrive() {
        Scanner in = new Scanner(System.in);
        Member member = selectMember();
        member.addObserver(data);
        System.out.println("Choose departure location");
        for (int i = 0; i < locations.size(); i++)
            System.out.println(i + ": " + locations.get(i));
        Location startLocation = locations.get(getOptionIntFromInput(locations.size()));
        System.out.println("Choose destination location");
        Location endLocation = locations.get(getOptionIntFromInput(locations.size()));
        System.out.println("Set by:");
        System.out.println("0: Departure time");
        System.out.println("1: Destination time");
        int option = getOptionIntFromInput(2);
        boolean byStartTime;
        if (option == 1) {
            System.out.println("Enter destination time mm/dd/yyyy/xx:xx");
            byStartTime = false;
        }
        else {
            System.out.println("Enter departure time mm/dd/yyyy/xx:xx");
            byStartTime = true;
        }
        GregorianCalendar time = getTimeFromInput();
        
        Request r = new Request(member, time, startLocation, endLocation, Request.TimeType.Near, byStartTime);
        SchedulingContext sc = new SchedulingContext();
        String fail = sc.schedule(r, null);
        if (fail.equals("Success")) {
            System.out.println("New Drive Scheduled!");
            Drive drive = member.getDrives().get(member.getDrives().size()-1);
            List<Location> stops = drive.getRoute().getStops();
            System.out.println(stops.get(0)+" at "+getTimeFromCalendar(drive.getStartTime())+" to "+stops.get(stops.size()-1)+" at "+getTimeFromCalendar(drive.getEndTime())+" on "+getDateFromCalendar(drive.getEndTime()));
            if (stops.size() > 2) {
                System.out.print("Stops at: ");
                for (int j = 1; j < stops.size()-1; j++) {
                    System.out.print(stops.get(j) + ", ");
                }
                System.out.println();
            }
            System.out.println(Integer.toString(drive.getNumSeats()-drive.numberOfRides()) + " seats available");
            System.out.println("Passengers:");
            if (drive.numberOfRides() == 0)
                System.out.println("  None");
            for (int i = 0; i < drive.numberOfRides(); i++) {
                ScheduleViewer sv = new ScheduleViewer();
                Ride ride = sv.getRideById(drive.getRideId(i));
                List<Location> rideStops = ride.getRoute().getStops();
                System.out.print("  "+ride.getMemberName() + ": ");
                System.out.println(rideStops.get(0)+" at "+getTimeFromCalendar(ride.getStartTime())+" to "+rideStops.get(rideStops.size()-1)+" at "+getTimeFromCalendar(ride.getEndTime()));
            }
        }
        else
            System.out.println(fail);
        System.out.println("Press Enter to continue...");
        in.nextLine();
        member.deleteObservers();
    }

    private static void viewRideRequest() {        
        Member member = selectMember();
        member.addObserver(data);
        System.out.println("Request a Ride:");
        System.out.println("0: New Ride Request");
        List<Request> requests = member.getRequests();
        for (int i = 0; i < requests.size(); i++) {
            System.out.println(Integer.toString(i+1)+": "+requests.get(i).getName());
        }
        int option = getOptionIntFromInput(requests.size()+1);
        if (option == 0)
            setRideRequest(member);
        else
            rideRequestResults(requests.get(option-1), member);
    }
    
    private static void setRideRequest(Member member) {
        System.out.println("Choose departure location");
        for (int i = 0; i < locations.size(); i++)
            System.out.println(i + ": " + locations.get(i));
        int startLocation = getOptionIntFromInput(locations.size());
        System.out.println("Choose how to use a departure time");
        Request.TimeType[] types = Request.TimeType.values();
        for (int i = 0; i < types.length; i++) {
            System.out.print(i+": "+types[i].name());
            if (types[i] != Request.TimeType.Anytime)
                System.out.print(" a time");
            System.out.println();
        }
        int index = getOptionIntFromInput(types.length);
        Request.TimeType startType = types[index];
        GregorianCalendar startTime = new GregorianCalendar();
        if (startType != Request.TimeType.Anytime) {
            System.out.println("Enter departure time mm/dd/yyyy/xx:xx");    
            startTime = getTimeFromInput();
        }
        System.out.println("Choose destination location");
        for (int i = 0; i < locations.size(); i++)
            System.out.println(i + ": " + locations.get(i));
        int endLocation = getOptionIntFromInput(locations.size());
        System.out.println("Choose how to use a destination time");
        for (int i = 0; i < types.length; i++) {
            System.out.print(i+": "+types[i].name());
            if (types[i] != Request.TimeType.Anytime)
                System.out.print(" a time");
            System.out.println();
        }
        index = getOptionIntFromInput(types.length);
        Request.TimeType EndType = types[index];
        GregorianCalendar endTime = new GregorianCalendar();
        if (EndType != Request.TimeType.Anytime) {
            System.out.println("Enter destination time mm/dd/yyyy/xx:xx");   
            endTime = getTimeFromInput();
        }
        
        Request request = new Request(member, startTime, endTime, locations.get(startLocation), locations.get(endLocation), startType, EndType);
        rideRequestResults(request, member);
    }
    
    private static void rideRequestResults(Request request, Member member) {
        String name = request.getName();
        if (name == null)
            name = "New Ride Request";
        System.out.println(name+" details:");
        if (request.getStartType() == Request.TimeType.Anytime)
            System.out.println("Depart from "+request.getStartLocation()+" at AnyTime");
        else
            System.out.println("Depart from "+request.getStartLocation()+" "+request.getStartType().name()+" "+getTimeFromCalendar(request.getStartTime())+" "+getDateFromCalendar(request.getStartTime()));
        if (request.getEndType() == Request.TimeType.Anytime)
            System.out.println("Arrive at "+request.getEndLocation()+" at AnyTime");
        else
            System.out.println("Arrive at "+request.getEndLocation()+" "+request.getEndType().name()+" "+getTimeFromCalendar(request.getEndTime())+" "+getDateFromCalendar(request.getEndTime()));
        Scanner in = new Scanner(System.in);
        in.nextLine();

        SchedulingContext sc = new SchedulingContext();
        List<Schedulable> drives = sc.getAvailable(request);
        System.out.println("Choose an Available Drive: ("+drives.size()+")");
        System.out.println("0: Save Ride Request and return");
        for (int i = 0; i < drives.size(); i++) {
            Drive drive = (Drive) drives.get(i);
            System.out.println(Integer.toString(i+1)+": "+drive.getMemberName()+". "+drive.getNumSeats()+" seats available. "+getTimeFromCalendar(drive.getStartTime())+" to "+getTimeFromCalendar(drive.getEndTime()));
        }
        int index = getOptionIntFromInput(drives.size()+1);
        if (index == 0) {
            if (request.getName() == null) {
                System.out.println("Enter a name for the Ride Request: ");
                name = in.nextLine();
            }
            sc.addToRequests(request, name);
        }
        else {
            String fail = sc.schedule(request, drives.get(index-1));
            if (fail.equals("Success")) {
                System.out.println("New Ride Scheduled!");
                Ride ride = request.getMember().getRides().get(request.getMember().getRides().size()-1);
                List<Location> stops = ride.getRoute().getStops();
                System.out.println(stops.get(0)+" at "+getTimeFromCalendar(ride.getStartTime())+" to "+stops.get(stops.size()-1)+" at "+getTimeFromCalendar(ride.getEndTime()) +" on "+getDateFromCalendar(ride.getEndTime()));
                Drive drive = (Drive) data.getSchedulable(ride.getDriveId());
                System.out.println("Passenger in "+drive.getMemberName()+"'s vehicle");

            }
            else
                System.out.println(fail);
        }
        System.out.println("Press Enter to continue...");
        in.nextLine();
        member.deleteObservers();
    }

    private static void viewSchedule() {
        Scanner in = new Scanner(System.in);
        Member member = selectMember();
        ScheduleViewer sv = new ScheduleViewer();
        System.out.print(sv.getScheduleText(member));
        System.out.println("Press Enter to continue...");
        in.nextLine();
    }
    
    private static void viewMap() {
        System.out.println(map);
        Scanner in = new Scanner(System.in);
        System.out.println("Press Enter to continue...");
        in.nextLine();
    }
    
    private static void Notifications() {
        Scanner in = new Scanner(System.in);
        Member member = selectMember();
        List<Notification> notifications = null;
        System.out.println("Notifications:");
        System.out.println("0: Return to menu");
        System.out.println("1: View new notifications: (" +member.getNumberOfNewNotifications()+ ")");
        System.out.println("2: View old notifications: (" +member.getNumberOfOldNotifications()+ ")");
        System.out.println("3: Send a notification");
        int option = getOptionIntFromInput(4);
        if (option == 0) {
            return;
        }
        else if (option == 1) {
            member.addObserver(data);
            notifications = member.readNewNotifications();  
        }
        else if (option == 2) {
            notifications = member.getOldNotifications();
        }
        else if (option == 3) {
            sendNotification(member);
            return;
        }
        for (int i = 0; i < notifications.size(); i++) {
            System.out.println("Notification "+Integer.toString(i+1)+": Created "+getTimeFromCalendar(notifications.get(i).getTime())+" "+getDateFromCalendar(notifications.get(i).getTime()));
            System.out.println(notifications.get(i).getMessage());
            System.out.println();
        }
        System.out.println("Press Enter to continue...");
        in.nextLine();
        member.deleteObservers();
    }
    
    private static void sendNotification(Member member) {
        Scanner in = new Scanner(System.in);
        System.out.println("Select member to send to:");
        Member toMember = selectMember();
        System.out.println("Enter notification message");
        String message = in.nextLine();
        NotificationSender ns = new NotificationSender(member);
        ns.send(toMember.getIdNumber(), message);
        System.out.println("Notification Sent!");
        System.out.println("Press Enter to continue...");
        in.nextLine();
        member.deleteObservers();
    }
    
    private static Member selectMember() {
        System.out.println("Select Member");
        List<Member> members = data.getMembers();
        for (int i = 0; i < members.size(); i++) {
            System.out.println(i + ": " + members.get(i).getFirstName() + " " + members.get(i).getLastName());
        }
        int option = getOptionIntFromInput(members.size());
        return members.get(option);
    }
    
    private static String getDateFromCalendar(GregorianCalendar gc) {
        return gc.get(GregorianCalendar.MONTH)+"/"+gc.get(GregorianCalendar.DATE)+"/"+gc.get(GregorianCalendar.YEAR);
    }
    
    private static String getTimeFromCalendar(GregorianCalendar gc) {
        String ampm[] = new String[2];
        ampm[0] = " AM";
        ampm[1] = " PM";
        String minute = Integer.toString(gc.get(GregorianCalendar.MINUTE));
        if (minute.length() == 1)
            minute = "0"+minute;
        return gc.get(GregorianCalendar.HOUR)+":"+minute+ampm[gc.get(GregorianCalendar.AM_PM)];
    }
    
    private static int getOptionIntFromInput(int lessThan) {
        int option = 0;
        Scanner in = new Scanner(System.in);
        boolean valid = false;
        while (!valid) {
            String input = in.nextLine();
            if (lessThan == 1) {
                valid = true;
                option = 0;
            }
            else {
                try {
                    option = Integer.parseInt(input);
                    if (option >= lessThan)
                        throw new Exception();
                    valid = true;
                } catch (Exception e) {
                    System.out.println("Invalid Input. try again");
                }
            }            
        }
        return option;
    } 
    
    private static GregorianCalendar getTimeFromInput() {
        GregorianCalendar time = null;
        Scanner in = new Scanner(System.in);
        boolean valid = false;
        while (!valid) {
            String input = in.nextLine();    
            try {
                int month = Integer.parseInt(input.substring(0, 2));
                int day = Integer.parseInt(input.substring(3, 5));
                int year = Integer.parseInt(input.substring(6, 10));
                int hour = Integer.parseInt(input.substring(11, 13));
                int minute = Integer.parseInt(input.substring(14));
                time = new GregorianCalendar(year, month, day, hour, minute);
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid Input. try again");
            }         
        }
        return time;
    } 

}
