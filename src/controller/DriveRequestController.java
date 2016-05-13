package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import model.StringFormat;
import model.schedule.Location;
import model.schedule.Request;
import model.schedule.Ride;
import model.schedule.Drive;
import model.schedule.ScheduleViewer;
import model.schedule.Scheduler;
import model.schedule.SchedulingContext;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the DriveRequestScene
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones, David Lerner
*/
public class DriveRequestController extends Controller{
    private Location pickup;
    private Location destination;
    private LocalDate date;
    private GregorianCalendar hourTime; 
    private Integer minuteTime;
    private boolean byStartTime;
    private ObservableList<GregorianCalendar> hours = FXCollections.observableArrayList();;
    private ObservableList<Location> locations = FXCollections.observableArrayList();
    private ObservableList<Integer> minutes = FXCollections.observableArrayList();
	
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Location> pickupLocation;
    @FXML
    private ComboBox<Location> destinationLocation;
    @FXML
    private RadioButton arrivalRadio;
    @FXML
    private RadioButton departureRadio;
    @FXML
    private ComboBox<GregorianCalendar> hourCombo;
    @FXML
    private ComboBox<Integer> minuteCombo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        //set up the location ComboBox
        for (Location l : context.getMap().getLocations())
            locations.add(l);
        pickupLocation.setItems(locations);
        destinationLocation.setItems(locations);

        
        //setup Arrival and Departure ComboBoxes
        //they are set up for todays date until a new date is picked from datePicker
        date = LocalDate.now();
        hours = hoursMaker(date);   
        hourCombo.setItems(hours);
        ArrayList<Integer> mins = new ArrayList<>(Arrays.asList(0, 10, 20, 30, 40, 50));
        minutes.addAll(mins);
        minuteCombo.setItems(minutes);        		
    }
	
    @FXML
    private void handleDatePicker(ActionEvent event) {
        date = datePicker.getValue();
        
        //update the arrive and depart boxes with correct date from datePicker
		hours.clear();		
        hours = hoursMaker(date);     
        hourCombo.setItems(hours);     
    }
	
    @FXML
    private void handleLocationCombos(ActionEvent event) {
        pickup = pickupLocation.getSelectionModel().getSelectedItem();
        destination = destinationLocation.getSelectionModel().getSelectedItem();
    }
	
    @FXML
    private void handleRadios(ActionEvent event) {
    	RadioButton radio = (RadioButton) event.getSource();
    	if (radio == arrivalRadio) {
            byStartTime = false;
    	} else if(radio == departureRadio){
    		byStartTime = true;
    	}
    }
	
    @FXML
    private void handleHourCombo(ActionEvent event) {
        hourTime = hourCombo.getSelectionModel().getSelectedItem();
    }
	
    @FXML
    private void handleMinuteCombo(ActionEvent event) {
        minuteTime = minuteCombo.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
    	changeScenePop(event);
    }
    
    @FXML
    private void handleSubmitButton(ActionEvent event) {
        //check if member is a driver
        if (!member.getDrivingType().isDriver()) {
            Alerts.showError("You are not a driver!");
            return;
        }
        //check if all fields are filled
        if (hourTime == null || minuteTime == null || pickup == null || destination == null || date == null
                || (!arrivalRadio.isSelected() && !departureRadio.isSelected())) {
            Alerts.showError("Not all fields are filled");
            return;
        }
        
    	//add minutes to hour
    	hourTime.set(GregorianCalendar.MINUTE, minuteTime);
        //create drive request
        Request request = new Request(member, hourTime, pickup, destination, Request.TimeType.Near, byStartTime);
        SchedulingContext sc = new SchedulingContext();
        //schedule drive
    	String fail = sc.schedule(request, null);
        if (fail.equals(Scheduler.SUCCESS)) {
            Drive drive = member.getDrives().get(member.getDrives().size()-1);
            List<Location> stops = drive.getRoute().getStops();
            //create alert text
            StringBuilder sb = new StringBuilder();
            String nl = System.lineSeparator();
            sb.append("On ").append(sb).append(StringFormat.getDateFromCalendar(drive.getEndTime())).append(nl);
            sb.append("From ").append(stops.get(0)).append(" at ").append(StringFormat.getTimeFromCalendar(drive.getStartTime())).append(nl);
            sb.append("To ").append(stops.get(stops.size()-1)).append(" at ").append(StringFormat.getTimeFromCalendar(drive.getEndTime())).append(nl);
            if (stops.size() > 2) {
                sb.append("Stops at: ");
                for (int j = 1; j < stops.size()-1; j++) {
                    sb.append(stops.get(j)).append(", ");
                }
            }
            sb.append(nl).append(drive.getNumSeats()).append(" seats available ").append(nl).append("Passengers: ").append(nl);               
            if (drive.numberOfRides() == 0) {
                sb.append("None");
            }
            for (int i = 0; i < drive.numberOfRides(); i++) {
                ScheduleViewer sv = new ScheduleViewer();
                Ride ride = sv.getRideById(drive.getRideId(i));
                List<Location> rideStops = ride.getRoute().getStops();
                sb.append("  ").append(ride.getMemberName()).append(": From ").append(rideStops.get(0)).append(" at ").append(StringFormat.getTimeFromCalendar(ride.getStartTime()));
                sb.append("to ").append(rideStops.get(rideStops.size()-1)).append("at ").append(StringFormat.getTimeFromCalendar(ride.getEndTime()));
            }
            Alerts.showInfo("Schedule Information", "New Drive Scheduled!", sb.toString());
        } else {
            Alerts.showError(fail);
        }

    	changeScenePop(event);
    }
    
    /**
     * hoursMaker is a helper method to add GregorianCalendar objects (representing week days 
     * with specific hours) to the comboboxes with appropriate Strings printed in the dropdown list
     * @param date the LocalDate from datePicker to be converted to a GregorianCalendar object
     * @return list of hours for the Comboboxes with reference to their weekday
     */
    private ObservableList<GregorianCalendar> hoursMaker(LocalDate date) {
        hours = FXCollections.observableArrayList();
        // get times for 6AM to 11AM
        for (int i = 6; i <= 11; i++) {
            GregorianCalendar day = new GregorianCalendar() {
                public String toString() {
                    return Integer.toString(this.get(GregorianCalendar.HOUR))
                            + this.getDisplayName(GregorianCalendar.AM_PM, 
                                    GregorianCalendar.LONG, Locale.getDefault());
                    
                }
            };
            day.set((GregorianCalendar.DAY_OF_YEAR), date.getDayOfYear());
            day.set(GregorianCalendar.AM_PM, GregorianCalendar.AM);
            day.set(GregorianCalendar.HOUR, i);
            day.clear(GregorianCalendar.MINUTE);
            day.clear(GregorianCalendar.SECOND);
            day.clear(GregorianCalendar.MILLISECOND);
            hours.add(day);
        }
        
        // get times for noon to 10pm
        for (int i = 12; i <= 22; i++) {
            GregorianCalendar day = new GregorianCalendar() {
                public String toString() {
                    if (this.get(GregorianCalendar.HOUR) == 0) {
                        return 12 + this.getDisplayName(GregorianCalendar.AM_PM, 
                                        GregorianCalendar.LONG, Locale.getDefault());
                    } else {
                        return Integer.toString(this.get(GregorianCalendar.HOUR))
                                + this.getDisplayName(GregorianCalendar.AM_PM, 
                                        GregorianCalendar.LONG, Locale.getDefault());
                    }
                }
            };
            day.set((GregorianCalendar.DAY_OF_YEAR), date.getDayOfYear());            
            day.set(GregorianCalendar.AM_PM, GregorianCalendar.PM);
            day.set(GregorianCalendar.HOUR_OF_DAY, i);
            day.clear(GregorianCalendar.MINUTE);
            day.clear(GregorianCalendar.SECOND);
            day.clear(GregorianCalendar.MILLISECOND);
            hours.add(day);
        }
        return hours;
    }

}
