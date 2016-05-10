package main;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import model.Context;
import model.DataHandler;
import model.schedule.Drive;
import model.member.*;
import model.schedule.Location;
import model.LocationMap;
import model.LoginHandler;
import model.member.LoginInformation;
import model.member.Member;
import model.member.MemberBuilder;
import model.Notification;
import model.NotificationSender;
import model.PasswordSender;
import model.StringFormat;
import model.member.Passenger;
import model.schedule.Ride;
import model.member.Vehicle;
import model.member.Vehicle.VehicleStyle;
import model.payment.BankAccount;
import model.payment.BankAccountInfo;
import model.payment.CreditCard;
import model.payment.CreditCardInfo;
import model.payment.Reward;
import model.schedule.Request;
import model.schedule.Schedulable;
import model.schedule.ScheduleViewer;
import model.schedule.Scheduler;
import model.schedule.SchedulingContext;
import model.schedule.WeeklySchedule;
import model.schedule.WeeklyScheduler;

/**
 *
 * @author David
 */
public class CLIMain {

    private static Context context;
    private static DataHandler data;
    private static LocationMap map;
    private static List<Location> locations;
    private static LoginHandler loginHandler;
    private static Scanner in;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        context = Context.getInstance();
        data = context.getDataHandler();
        map = context.getMap();
        locations = map.getLocations();
        loginHandler = context.getLogin();
        in = new Scanner(System.in);
        start();
    }
    
    private static void start() {
        boolean exit = false;
        while (! exit) {
            System.out.println("Welcome to Spartan Pool.");
            System.out.println("Get started by entering one of the following numbers:");
            System.out.println("0: Exit");
            System.out.println("1: Login");
            System.out.println("2: Create new account");
            System.out.println("3: Forgot Password");
            System.out.println("4: Cheat Login");
            int option = getOptionIntFromInput(5);
            switch (option) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    login();
                    break;
                case 2:
                    createAccount();
                    break;
                case 3:
                    forgotPassword();
                    break;
                case 4:
                    cheatLogin();
                    break;
                default:
                    break;
            }
        }
        System.exit(0);
    }
    private static void login() {
        System.out.println("Enter email");
        String email = in.nextLine();
        System.out.println("Enter password");
        String password = in.nextLine();
        if (loginHandler.isLoggedIn())
            loginHandler.handleLogout();
        System.out.println(loginHandler.handleLogin(new LoginInformation(email, password)));
        if (loginHandler.isLoggedIn()) {
            menu();
            return;
        }
        System.out.println("Press Enter to continue...");
        in.nextLine();
    }
    
    private static void createAccount() {
        MemberBuilder mb = new MemberBuilder();
        System.out.println("Enter email");
        String email = in.nextLine();
        mb.setFirstName(email);
        System.out.println("Enter password");
        String pw = in.nextLine();
        mb.setLoginInfo(new LoginInformation(email, pw));
        System.out.println("Driver:");
        System.out.println("0: Yes");
        System.out.println("1: No");
        int option = getOptionIntFromInput(2);
        if (option == 0) 
            mb.setDrivingType(new Driver("", new Vehicle(2000, "", "", "", "", null, 4), null));
        else
            mb.setDrivingType(new Passenger());
        
        if (mb.build() == -1)
            System.out.println("Failed to create new account");
        else
            System.out.println("New Account created!");
        System.out.println("Press Enter to continue...");
        in.nextLine();
        if (loginHandler.isLoggedIn())
            loginHandler.handleLogout();
        System.out.println(loginHandler.handleLogin(new LoginInformation(email, pw)));
        if (loginHandler.isLoggedIn())
            menu();
    }

    private static void forgotPassword() {
        System.out.println("Enter email to send password to:");
        (new PasswordSender()).sendPassword(in.nextLine());
        System.out.println("Email sent");
        System.out.println("Press Enter to continue...");
        in.nextLine();
    }
    
    private static void cheatLogin() {
        if (loginHandler.handleLogin(selectMember().getLoginInfo()).equals(""))
            menu();
    }
    
    private static void menu() {
        boolean exit = false;
        while (! exit) {
            System.out.println("Welcome, "+context.getMember());
            System.out.println("Main Menu:");
            System.out.println("0: Logout");
            System.out.println("1: Update Account");
            System.out.println("2: Set a Drive");
            System.out.println("3: Request a Ride");
            System.out.println("4: Set Weekly Schedule");
            System.out.println("5: View Schedule");
            System.out.println("6: View Map");
            System.out.println("7: Notifications");
            System.out.println("8: Payments");
            int option = getOptionIntFromInput(9);
            switch (option) {
                case 0:
                    loginHandler.handleLogout();
                    exit = true;
                    break;
                case 1:
                    updateAccount();
                    break;
                case 2:
                    setDrive();
                    break;
                case 3:
                    viewRideRequest();
                    break;
                case 4:
                    setWeekly();
                    break;
                case 5:
                    viewSchedule();
                    break;
                case 6:
                    viewMap();
                    break;
                case 7:
                    notifications();
                    break;
                case 8:
                    payments();
                    break;
                default:
                    break;
            }
        }
        //back to start()
    }

    private static void updateAccount() {
        Member member = context.getMember();
        boolean exit = false;
        while (!exit) {
        	showMemberInfo(member);
            System.out.println("Select the info you wish to modify");
            System.out.println("0: Return to menu");
            System.out.println("1: Login Information");
            System.out.println("2: Name");
            System.out.println("3: Address");
            System.out.println("4: Phone Number");
            System.out.println("5: Driving Type");
            System.out.println("6: Member Type");
            System.out.println("7: Payment Information");
            int option = getOptionIntFromInput(8);
            switch (option) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    updateLoginInfo(member);
                    member.setChanged();
                    member.notifyObservers();
                    break;
                case 2:
                    updateName(member);
                    member.setChanged();
                    member.notifyObservers();
                    break;
                case 3:
                    updateAddress(member);
                    member.setChanged();
                    member.notifyObservers();
                    break;
                case 4:
                    updatePhone(member);
                    member.setChanged();
                    member.notifyObservers();
                    break;
                case 5:
                    updateDrivingType(member);
                    member.setChanged();
                    member.notifyObservers();
                    break;
                case 6:
                    updateMemberType(member);
                    member.setChanged();
                    member.notifyObservers();
                    break;
                case 7:
                    updatePaymentInfo(member);
                    member.setChanged();
                    member.notifyObservers();
                    break;
                default:
                    break;
            }
        }
        //member.setChanged();
        //member.notifyObservers();
        //back to menu
    }
    
    private static void setDrive() {
        Member member = context.getMember();
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
            System.out.println("Enter destination time mm/dd/yyyy/hh:mm");
            byStartTime = false;
        }
        else {
            System.out.println("Enter departure time mm/dd/yyyy/hh:mm");
            byStartTime = true;
        }
        GregorianCalendar time = getTimeDateFromInput();
        
        Request r = new Request(member, time, startLocation, endLocation, Request.TimeType.Near, byStartTime);
        SchedulingContext sc = new SchedulingContext();
        String fail = sc.schedule(r, null);
        if (fail.equals(Scheduler.SUCCESS)) {
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
            System.out.println(Integer.toString(drive.getNumSeats()) + " seats available");
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
    }

    private static void viewRideRequest() {        
        Member member = context.getMember();
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
            System.out.println("Enter departure time mm/dd/yyyy/hh:mm");    
            startTime = getTimeDateFromInput();
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
            System.out.println("Enter destination time mm/dd/yyyy/hh:mm");   
            endTime = getTimeDateFromInput();
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
                sc.addRideToRequests(request, name);
            }            
        }
        else {
            String fail = sc.schedule(request, drives.get(index-1));
            if (fail.equals(Scheduler.SUCCESS)) {
                System.out.println("New Ride Scheduled!");
                Ride ride = request.getMember().getRides().get(request.getMember().getRides().size()-1);
                List<Location> stops = ride.getRoute().getStops();
                System.out.println(stops.get(0)+" at "+getTimeFromCalendar(ride.getStartTime())+" to "+stops.get(stops.size()-1)+" at "+getTimeFromCalendar(ride.getEndTime()) +" on "+getDateFromCalendar(ride.getEndTime()));
                Drive drive = (new ScheduleViewer()).getDriveById(ride.getDriveId());
                System.out.println("Passenger in "+drive.getMemberName()+"'s vehicle");

            }
            else
                System.out.println(fail);
        }
        System.out.println("Press Enter to continue...");
        in.nextLine();
    }
    
    private static void setWeekly() {
        Member member = context.getMember();
        WeeklySchedule ws = member.getWeeklySchedule();
        System.out.println("Enter start date for weekly schedule (mm/dd/yyyy)):");
        ws.setStartTime(getDateFromInput());
        System.out.println("Enter end date for weekly schedule (mm/dd/yyyy)):");
        ws.setEndTime(getDateFromInput());
        System.out.println("Choose starting location for traveling to and from "+context.getCentral());
        for (int i = 0; i < locations.size(); i++)
            System.out.println(i + ": " + locations.get(i));
        ws.setPickupLocation(locations.get(getOptionIntFromInput(locations.size())));
        boolean exit = false;
        while (! exit) {
            System.out.println("Enter daily schedules:");
            System.out.println("0: Save weekly schedule & Return");
            System.out.println("1: Monday");
            System.out.println("2: Tuesday");
            System.out.println("3: Wednesday");
            System.out.println("4: Thursday");
            System.out.println("5: Friday");
            int option = getOptionIntFromInput(6);
            switch (option) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    setDailySchedule(GregorianCalendar.MONDAY, ws);
                    break;
                case 2:
                    setDailySchedule(GregorianCalendar.TUESDAY, ws);
                    break;
                case 3:
                    setDailySchedule(GregorianCalendar.WEDNESDAY, ws);
                    break;
                case 4:
                    setDailySchedule(GregorianCalendar.THURSDAY, ws);
                    break;
                case 5:
                    setDailySchedule(GregorianCalendar.FRIDAY, ws);
                    break;
                default:
                    break;
            }
        }
        WeeklyScheduler scheduler = new WeeklyScheduler();
        String fail = scheduler.schedule(new Request(member, new GregorianCalendar(), context.getCentral()), null);
        if (fail.equals(Scheduler.SUCCESS)) 
            System.out.println("Weekly Schedule Saved!");
        else
            System.out.println(fail);
        System.out.println("Press Enter to continue...");
        in.nextLine();
    }

    private static void setDailySchedule(int day, WeeklySchedule ws) {
        System.out.println("Enter arrival time at "+context.getCentral()+" from "+ws.getPickupLocation()+" (hh:mm):");
        ws.setArrive(day, getTimeFromInput());
        System.out.println("Enter departure time from "+context.getCentral()+" to "+ws.getPickupLocation()+" (hh:mm):");
        ws.setDepart(day, getTimeFromInput());
        System.out.println("Available to drive?");
        System.out.println("0: Yes");
        System.out.println("1: No");
        if (getOptionIntFromInput(2) == 0)
            ws.setDrive(day, true);
        else
            ws.setDrive(day, false);
    }
    
    private static void viewSchedule() {
        Member member = context.getMember();
        System.out.print((new ScheduleViewer()).getScheduleText(member));
        System.out.println("Press Enter to continue...");
        in.nextLine();
    }
    
    private static void viewMap() {
        System.out.println(map);
        System.out.println("Press Enter to continue...");
        in.nextLine();
    }
    
    private static void notifications() {
        Member member = context.getMember();
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
    }
    
    private static void sendNotification(Member member) {
        System.out.println("Select member to send to:");
        Member toMember = selectMember();
        System.out.println("Enter notification message");
        String message = in.nextLine();
        NotificationSender ns = new NotificationSender(member);
        ns.send(toMember.getLoginInfo().getEmail(), message);
        System.out.println("Notification Sent!");
        System.out.println("Press Enter to continue...");
        in.nextLine();
    }
    
    private static void payments() {
        System.out.println("Manage Payments:");
        System.out.println("0: Return");
        System.out.println("1: View payments you owe");
        System.out.println("2: View payments you are owed");
        int option = getOptionIntFromInput(3);
        if (option == 1)
            payer();
        else if (option == 2)
            payee();
    }

    private static void payer() {
        ScheduleViewer sv = new ScheduleViewer();
        Member member = context.getMember();
        boolean exit = false;
        while (! exit) {
            List<Ride> rides = sv.getRidesToPay(member);
            System.out.println("Select a ride to pay for:");
            System.out.println("0: Return to menu");
            for (int i = 0; i < rides.size(); i++) {
                System.out.println(Integer.toString(i+1)+": Ride on "+getDateFromCalendar(rides.get(i).getEndTime()));
            }
            int option = getOptionIntFromInput(rides.size()+1);
            if (option == 0)
                exit = true;
            else
                payForRide(rides.get(option-1));
        }       
    }
    
    private static void payForRide(Ride r) {
        Member member = context.getMember();
        StringBuilder sb = new StringBuilder();
        String nl = System.lineSeparator();
        ScheduleViewer sv = new ScheduleViewer();
        
        sb.append("Ride details:").append(nl);
        List<Location> stops = r.getStops();
        sb.append("  ").append(stops.get(0)).append(" at ").append(getTimeFromCalendar(r.getStartTime())).append(" to ");
        sb.append(stops.get(stops.size()-1)).append(" at ").append(getTimeFromCalendar(r.getEndTime())).append(" on ").append(getDateFromCalendar(r.getStartTime())).append(nl);
        sb.append("Amount you owe: ");
        System.out.print(sb.toString());
        
        Member driver = data.getMember(sv.getDriveById(r.getDriveId()).getMemberId());
        if (!driver.getDrivingType().isDriver()) {
            System.out.println("$0, not a driver");
            System.out.println("Press Enter to continue...");
            in.nextLine();
            return;
        }
        Driver d = (Driver) driver.getDrivingType();
        Reward reward = new CreditCard(null, d.getPayBy());
        double amount = (Double)reward.findReward(driver, r);
        System.out.printf("$%.2f%n", amount);
        System.out.println("0: Return");
        System.out.println("1: Pay by Credit Card");
        System.out.println("2: Pay by Bank Account");
        System.out.println("3: Send message to driver");
        int option = getOptionIntFromInput(4);
        if (option == 1) {           
            reward = new CreditCard(member.getCreditCardInfo(), d.getPayBy());
            if (reward.payReward(driver, r, amount))
                System.out.println("Payment accepted!");
            else
                System.out.println("Payment failed!");
            System.out.println("Press Enter to continue...");
            in.nextLine();
        }
        else if (option == 2) {
            reward = new BankAccount(member.getBankAccountInfo(), d.getPayBy());
            if (reward.payReward(driver, r, amount))
                System.out.println("Payment accepted!");
            else
                System.out.println("Payment failed!");
            System.out.println("Press Enter to continue...");
            in.nextLine();
        }
        else if (option == 3) {
            NotificationSender ns = new NotificationSender(member);
            System.out.println("Enter message to send:");
            ns.send(driver.getLoginInfo().getEmail(), in.nextLine());
            System.out.println("Message sent");
            System.out.println("Press Enter to continue...");
            in.nextLine();
        }
    }
    
    private static void payee() {
        ScheduleViewer sv = new ScheduleViewer();
        Member member = context.getMember();
        boolean exit = false;
        while (! exit) {
            List<Ride> rides = sv.getRidesToBePaid(member);
            System.out.println("Select a ride:");
            System.out.println("0: Return to menu");
            for (int i = 0; i < rides.size(); i++) {
                System.out.println(Integer.toString(i+1)+": Ride on "+getDateFromCalendar(rides.get(i).getEndTime()));
            }
            int option = getOptionIntFromInput(rides.size()+1);
            if (option == 0)
                exit = true;
            else
                paidForRide(rides.get(option-1));
        }       
    }
    
    private static void paidForRide(Ride r) {
        Member member = context.getMember();
        
        if (!member.getDrivingType().isDriver()) {
            System.out.println("You are not a driver");
        }
            
        StringBuilder sb = new StringBuilder();
        String nl = System.lineSeparator();
        ScheduleViewer sv = new ScheduleViewer();
        
        sb.append("Ride details:").append(nl);
        List<Location> stops = r.getStops();
        sb.append("  ").append(stops.get(0)).append(" at ").append(getTimeFromCalendar(r.getStartTime())).append(" to ");
        sb.append(stops.get(stops.size()-1)).append(" at ").append(getTimeFromCalendar(r.getEndTime())).append(" on ").append(getDateFromCalendar(r.getStartTime())).append(nl);
        sb.append("Amount owed to you: ");
        System.out.print(sb.toString());
        
        Driver d = (Driver) member.getDrivingType();
        Reward reward = new CreditCard(null, d.getPayBy());
        double amount = (Double)reward.findReward(member, r);
        System.out.printf("$%.2f%n", amount);
        System.out.println("0: Return");
        System.out.println("1: Waive payment");
        System.out.println("2: Send message to rider");
        int option = getOptionIntFromInput(3);
        if (option == 1) {
            reward.waiveReward(r);
            System.out.println("Payment waived");
            System.out.println("Press Enter to continue...");
            in.nextLine();
        }
        else if (option == 2) {
            NotificationSender ns = new NotificationSender(member);
            System.out.println("Enter message to send:");
            ns.send(data.getMember(r.getMemberId()).getLoginInfo().getEmail(), in.nextLine());
            System.out.println("Message sent");
            System.out.println("Press Enter to continue...");
            in.nextLine();
        }
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
        return StringFormat.getDateFromCalendar(gc);
    }
    
    private static String getTimeFromCalendar(GregorianCalendar gc) {
        return StringFormat.getTimeFromCalendar(gc);
    }
    
    private static int getOptionIntFromInput(int lessThan) {
        int option = 0;
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
    
    private static GregorianCalendar getTimeDateFromInput() {
        GregorianCalendar time = null;
        boolean valid = false;
        while (!valid) {
            String input = in.nextLine();    
            try {
                int month = Integer.parseInt(input.substring(0, 2))-1;
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

        private static GregorianCalendar getTimeFromInput() {
        GregorianCalendar time = null;
        boolean valid = false;
        while (!valid) {
            String input = in.nextLine();    
            try {
                int hour = Integer.parseInt(input.substring(0, 2));
                int minute = Integer.parseInt(input.substring(3, 5));
                time = new GregorianCalendar();
                time = new GregorianCalendar(time.get(GregorianCalendar.YEAR), time.get(GregorianCalendar.MONTH), 
                time.get(GregorianCalendar.DAY_OF_MONTH), hour, minute);
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid Input. try again");
            }         
        }
        return time;
    }
        
    private static GregorianCalendar getDateFromInput() {
        GregorianCalendar time = null;
        boolean valid = false;
        while (!valid) {
            String input = in.nextLine();    
            try {
                int month = Integer.parseInt(input.substring(0, 2))-1;
                int day = Integer.parseInt(input.substring(3, 5));
                int year = Integer.parseInt(input.substring(6, 10));
                time = new GregorianCalendar(year, month, day);
                valid = true;
            } catch (Exception e) {
                System.out.println("Invalid Input. try again");
            }         
        }
        return time;
    } 
    
    private static void updateLoginInfo(Member member) {
        System.out.print("Enter the Email address: ");
        String email, password1, password2;
    	int i = 0;
        email = in.nextLine();
        do{
        
        	if (i > 0)
        		System.out.println("Password did not match.");
        	System.out.print("Enter a new password: ");
            password1 = in.nextLine();
            System.out.print("Enter the new password again: ");
            password2 = in.nextLine();
            i++;
        }while(!password1.equals(password2));
        
        member.setLoginInfo(new LoginInformation(email, password1));
    }

    private static void updateName(Member member) {
    	System.out.print("Enter the first name: ");
    	member.setFirstName(in.nextLine());
    	System.out.print("Enter the last name: ");
    	member.setLastName(in.nextLine());
    }

    private static void updateAddress(Member member) {
    	System.out.print("Enter the street address (ex:320 turk st): ");
    	member.getAddress().setStreet1(in.nextLine());
    	System.out.print("Enter the unit number(if applicable): ");
    	member.getAddress().setStreet2(in.nextLine());
    	System.out.print("Enter the city: ");
    	member.getAddress().setCity(in.nextLine());
    	System.out.print("Enter the state: ");
    	member.getAddress().setState(in.nextLine());
    	System.out.print("Enter the zip code: ");
    	member.getAddress().setZipCode(in.nextLine());
    }

    private static void updatePhone(Member member) {
    	System.out.println("Enter the phone number: ");
    	member.setPhoneNumber(in.nextLine());
    }

    private static void updateDrivingType(Member member) {
    	System.out.print("Are you going to drive?(0) or ride(1)? ");
    	int type = getOptionIntFromInput(2);
    	if(type == 0){
    		System.out.print("Enter your driver License number: ");
    		String licenseNumber = in.nextLine();
    		Vehicle vehicle = updateVehicle();
    		member.setDrivingType(new Driver(licenseNumber, vehicle, null));
    	}
    	else
    		member.setDrivingType(new Passenger());
    }

    private static void updateMemberType(Member member) {
    	System.out.print("Are you a staff(0), faculty(1), or student(2)?");
    	int type = getOptionIntFromInput(3);
    	if(type == 0)
    		member.setMemberType(new Staff());
    	else if (type == 1)
    		member.setMemberType(new Faculty());
    	else
    		member.setMemberType(new Student());
    }
    
    private static Vehicle updateVehicle(){
    	Vehicle vehicle = new Vehicle();
    	
    	System.out.println("Enter the information of your car.");
    	
    	System.out.print("Enter the year your car manufactured: ");
    	vehicle.setYear(in.nextInt());
    	
    	System.out.print("Enter the manufacturer: ");
    	vehicle.setManufacturer(in.nextLine());
    	
    	System.out.print("Enter the car model: ");
    	vehicle.setModel(in.nextLine());
    	
    	System.out.print("Enter the color: ");
    	vehicle.setColor(in.nextLine());
    	
    	System.out.print("Enter the plate number: ");
    	vehicle.setPlateNumber(in.nextLine());
    	
    	System.out.println("Choose the Type of your car: ");
    	System.out.print("Sedan(0), TwoDoor(1), Wagon(2), SUV(3), Van(4), Truck(5): ");
    	int type = getOptionIntFromInput(6);
    	switch(type){
    	case 0:
    		vehicle.setStyle(VehicleStyle.Sedan);
    		break;
    	case 1:
    		vehicle.setStyle(VehicleStyle.TwoDoor);
    		break;
    	case 2:
    		vehicle.setStyle(VehicleStyle.Wagon);
    		break;
    	case 3:
    		vehicle.setStyle(VehicleStyle.SUV);
    		break;
    	case 4:
    		vehicle.setStyle(VehicleStyle.Van);
    		break;
    	case 5:
    		vehicle.setStyle(VehicleStyle.Truck);
    		break;
    	}
    	
    	System.out.println("Enter the capacity of your car: ");
    	vehicle.setCapacity(in.nextInt());
    	
    	return vehicle;
    }
    private static void showMemberInfo(Member member){
    	System.out.printf("\nName: %s %s\n", member.getFirstName(), member.getLastName());
    	
    	LoginInformation login = member.getLoginInfo();
    	System.out.printf("Email: %s\nPassword: %s\n", login.getEmail(), login.getPassword());
    	
    	Address address = member.getAddress();
    	System.out.printf("Address: %s %s, %s, %s, %s.\n", address.getStreet1(), address.getStreet2()
    			, address.getCity(), address.getState(), address.getZipCode());
    	
    	System.out.printf("Phone Number: %s\n", member.getPhoneNumber());
    	
    	if(member.getDrivingType().isDriver())
    		System.out.println("Driving Type: Driver");
    	else
    		System.out.println("Driving Type: Passenger");
        System.out.println("Press Enter to continue...");
        in.nextLine();
    }

    private static void updatePaymentInfo(Member member) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Payment Information");
            System.out.println("0: Return");
            System.out.println("1: Update Credit Card Information");
            System.out.println("2: Update Bank Account Information");
            int option = getOptionIntFromInput(3);
            if (option == 0)
                exit = true;
            else if (option == 1) {
                System.out.println("Select Credit Card type:");
                CreditCardInfo.CardType[] types = CreditCardInfo.CardType.values();
                for (int i = 0; i < types.length; i++)
                    System.out.println(i+": "+types[i].name());
                int index = getOptionIntFromInput(types.length);
                System.out.println("Enter name on card:");
                String name = in.nextLine();
                System.out.println("Enter card number:");
                String number = in.nextLine();
                System.out.println("Enter security code:");
                String code = in.nextLine();
                System.out.println("Enter expiration date (mm/yy):");
                String expr = in.nextLine();
                int mon;
                int year;
                try {
                    mon = Integer.parseInt(expr.substring(0, 2));
                    year = Integer.parseInt(expr.substring(3));
                } catch (Exception e) {
                    mon = 0;
                    year = 0;
                }

                member.setCreditCardInfo(new CreditCardInfo(name, types[index], number, code, mon, year));
                System.out.println("Credit Card Info:");
                System.out.println("Name on card: "+name);
                System.out.println("Card Type: "+types[index].name());
                System.out.println("Credit card number: "+number);
                System.out.println("Security code: "+code);
                System.out.println("Expiration date: "+mon+"/"+year);
                System.out.println();
                System.out.println("Press Enter to continue...");
                in.nextLine();
            }
            else if (option == 2) {
                System.out.println("Enter name on Account:");
                String name = in.nextLine();
                System.out.println("Enter the name of the bank");
                String bank = in.nextLine();
                System.out.println("Enter your bank account number:");
                String number = in.nextLine();
                System.out.println("Enter your routing number:");
                String routing = in.nextLine();

                member.setBankAccountInfo(new BankAccountInfo(name, bank, number, routing));
                System.out.println("Bank Account Info:");
                System.out.println("Name on account: "+name);
                System.out.println("Name of the bank: "+bank);
                System.out.println("Bank account number: "+number);
                System.out.println("Routing number: "+routing);
                System.out.println();
                System.out.println("Press Enter to continue...");
                in.nextLine();
            }
        }
    }

}
