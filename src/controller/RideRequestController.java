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
import javafx.scene.control.Label;
import model.StringFormat;
import model.schedule.Drive;
import model.schedule.Location;
import model.schedule.Request;
import model.schedule.Ride;
import model.schedule.Schedulable;
import model.schedule.ScheduleViewer;
import model.schedule.Scheduler;
import model.schedule.SchedulingContext;
import model.schedule.Request.TimeType;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the RideRequestScene
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones, David Lerner
*/
public class RideRequestController extends Controller{
	
    private SchedulingContext sc;
    private Location pickup;
    private Location destination;
    private LocalDate date;
    private GregorianCalendar pickupHourTime, destinationHourTime;
    private GregorianCalendar pickupSelectedDateTime, destinationSelectedDateTime;
    private Integer pickupMinuteTime, destinationMinuteTime;
    private TimeType pickupTimeType, destinationTimeType;
    private Request request;
    private Drive selectedRide;
    private ObservableList<GregorianCalendar> hours = FXCollections.observableArrayList();;
    private ObservableList<Location> locations = FXCollections.observableArrayList();
    private ObservableList<Integer> minutes = FXCollections.observableArrayList();
    private ObservableList<Object> rideChoices = FXCollections.observableArrayList();
    
    @FXML
    private Label pickupHourLabel;
    @FXML
    private Label destinationHourLabel;
    @FXML
    private Label pickupMinuteLabel;
    @FXML
    private Label destinationMinuteLabel;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<Location> pickupLocation;
    @FXML
    private ComboBox<Location> destinationLocation;
    @FXML
    private ComboBox<GregorianCalendar> pickupHourCombo;
    @FXML
    private ComboBox<GregorianCalendar> destinationHourCombo;
    @FXML
    private ComboBox<Integer> pickupMinuteCombo;
    @FXML
    private ComboBox<Integer> destinationMinuteCombo;
    @FXML
    private ComboBox<TimeType> pickupTimeTypeCombo;
    @FXML
    private ComboBox<TimeType> destinationTimeTypeCombo;
    @FXML
    private ComboBox<Object> pickRideCombo;
    
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
        pickupHourCombo.setItems(hours);
        destinationHourCombo.setItems(hours);
        ArrayList<Integer> mins = new ArrayList<>(Arrays.asList(0, 10, 20, 30, 40, 50));
        minutes.addAll(mins);
        pickupMinuteCombo.setItems(minutes);
        destinationMinuteCombo.setItems(minutes);
        
        //setup timeType ComboBox
        pickupTimeTypeCombo.getItems().setAll(TimeType.values());
        destinationTimeTypeCombo.getItems().setAll(TimeType.values());
    }
	
    @FXML
    private void handleDatePicker(ActionEvent event) {
        date = datePicker.getValue();
        
        //update the arrive and depart boxes with correct date from datePicker
        hours.clear();		
        hours = hoursMaker(date);     
        pickupHourCombo.setItems(hours);
        destinationHourCombo.setItems(hours);
    }
	
    @FXML
    private void handleLocationCombos(ActionEvent event) {
        pickup = pickupLocation.getSelectionModel().getSelectedItem();
        destination = destinationLocation.getSelectionModel().getSelectedItem();
        if(checkData()) {
            makeRideRequest();
        }
    }
	
    @FXML
    private void handleHourCombos(ActionEvent event) {
        pickupHourTime = pickupHourCombo.getSelectionModel().getSelectedItem();
        destinationHourTime = destinationHourCombo.getSelectionModel().getSelectedItem();
        if(checkData()) {
            makeRideRequest();
        }
    }
	
    @FXML
    private void handleMinuteCombos(ActionEvent event) {
        pickupMinuteTime = pickupMinuteCombo.getSelectionModel().getSelectedItem();
        destinationMinuteTime = destinationMinuteCombo.getSelectionModel().getSelectedItem();
        if(checkData()) {
            makeRideRequest();
        }
    }
	
    @FXML
    private void handleTimeTypeCombos(ActionEvent event) {
        pickupTimeType = pickupTimeTypeCombo.getSelectionModel().getSelectedItem();
        //time not needed for anytime
        if (pickupTimeType == Request.TimeType.Anytime) {
            pickupHourCombo.setVisible(false);
            pickupMinuteCombo.setVisible(false);
            pickupHourLabel.setVisible(false);
            pickupMinuteLabel.setVisible(false);            
        }
            else {
            pickupHourCombo.setVisible(true);
            pickupMinuteCombo.setVisible(true);
            pickupHourLabel.setVisible(true);
            pickupMinuteLabel.setVisible(true); 
        }
        destinationTimeType = destinationTimeTypeCombo.getSelectionModel().getSelectedItem();
        //time not needed for anytime
        if (destinationTimeType == Request.TimeType.Anytime) {
            destinationHourCombo.setVisible(false);
            destinationMinuteCombo.setVisible(false);
            destinationHourLabel.setVisible(false);
            destinationMinuteLabel.setVisible(false);
        }
        else {
            destinationHourCombo.setVisible(true);
            destinationMinuteCombo.setVisible(true);
            destinationHourLabel.setVisible(true);
            destinationMinuteLabel.setVisible(true);
        }
            
        if(checkData()) {
            makeRideRequest();
        }
    }
	
    @FXML
    private void handlePickRideCombo(ActionEvent event) {
        if(pickRideCombo.getSelectionModel().getSelectedItem() instanceof Drive) {
            selectedRide = (Drive)pickRideCombo.getSelectionModel().getSelectedItem();
        } else {
            selectedRide = null;
        }
    }

    @FXML
    private void handleCancelButton(ActionEvent event) {
    	changeScenePop(event);
    }
    
    @FXML
    private void handleSubmitButton(ActionEvent event) {
        if (!checkData() || request == null) {
            Alerts.showError("Not all fields are filled");
            return;
        }
    	if(selectedRide == null) {
            //make a new RideRequest with given data from form
            sc.addRideToRequests(request, member.getFirstName());
            //create message text
            String nl = System.lineSeparator();
            StringBuilder sb = new StringBuilder();
            if (request.getStartType() == Request.TimeType.Anytime)
                sb.append("Depart from ").append(pickup).append(" at AnyTime").append(nl);
            else {
                sb.append("Depart from ").append(pickup).append(" ").append(request.getStartType().name()).append(" ").append(StringFormat.getTimeFromCalendar(pickupSelectedDateTime)).append(" on ");
                sb.append(pickupSelectedDateTime.getDisplayName(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.LONG, Locale.getDefault())).append(", ").append(StringFormat.getDateFromCalendar(pickupSelectedDateTime)).append(nl);
            }
            if (request.getEndType() == Request.TimeType.Anytime)
                sb.append("Arrive at ").append(destination).append(" at AnyTime").append(nl);
            else
                {
                sb.append("Arrive at ").append(destination).append(" ").append(request.getEndType().name()).append(" ").append(StringFormat.getTimeFromCalendar(destinationSelectedDateTime)).append(" on ");
                sb.append(destinationSelectedDateTime.getDisplayName(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.LONG, Locale.getDefault())).append(", ").append(StringFormat.getDateFromCalendar(destinationSelectedDateTime));
            }
            Alerts.showInfo("Schedule Information", "New Ride Request Made!", sb.toString());
            changeScenePop(event);
    	} else {
            //get selected drive and schedule a ride
            String fail = sc.schedule(request, selectedRide);
            //if successful
            if (fail.equals(Scheduler.SUCCESS)) {
                //get a reference to the new ride
                Ride ride = request.getMember().getRides().get(request.getMember().getRides().size()-1);
                List<Location> stops = ride.getRoute().getStops();
                Drive drive = (new ScheduleViewer()).getDriveById(ride.getDriveId());
                //create info message
                StringBuilder sb = new StringBuilder();
                String nl = System.lineSeparator();
                sb.append("on ").append(StringFormat.getDateFromCalendar(ride.getEndTime())).append(nl);
                sb.append("From ").append(stops.get(0)).append(" at ").append(StringFormat.getTimeFromCalendar(ride.getStartTime())).append(nl);
                sb.append("To ").append(stops.get(stops.size()-1)).append(" at ").append(StringFormat.getTimeFromCalendar(ride.getEndTime())).append(nl);    			
                sb.append("Your Driver is ").append(drive.getMemberName());
                Alerts.showInfo("Schedule Information", "New Ride Scheduled!", sb.toString());
                changeScenePop(event);
            //if not
            } else {
                Alerts.showError(fail);
            }
    	}
    	
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
    
    /**
     * makeRideRequest is called after all data needed has been collected. This method
     * makes a request and populates the pickRideCombo box with a list of available 
     * rides that can then be selected
     */
    private void makeRideRequest() {
        //in case anytime was picked
        if (pickupTimeType != TimeType.Anytime) {
            pickupHourTime.set(GregorianCalendar.MINUTE, pickupMinuteTime);
            pickupSelectedDateTime = pickupHourTime;
        } else {
            pickupSelectedDateTime = new GregorianCalendar();
        }    
        
        if (destinationTimeType != TimeType.Anytime) {
            destinationHourTime.set(GregorianCalendar.MINUTE, destinationMinuteTime);
            destinationSelectedDateTime = destinationHourTime;
        } else {
            destinationSelectedDateTime = new GregorianCalendar();
        }
        
        //setup dropdown of available rides
        request = new Request(member, pickupSelectedDateTime, destinationSelectedDateTime, pickup, destination, pickupTimeType, destinationTimeType);
        sc = new SchedulingContext();
        List<Schedulable> drives = sc.getAvailable(request);
        rideChoices.clear();
        rideChoices.addAll(drives);
        rideChoices.add("Don't see the ride you want? Make a New Ride Request");
        pickRideCombo.setItems(rideChoices);        
    }

    //checks if all appropriate fields are filled
    private boolean checkData() {
        return (pickup != null && destination != null && ((pickupHourTime != null && pickupMinuteTime != null) || pickupTimeType == TimeType.Anytime) && 
            ((destinationHourTime != null && destinationMinuteTime != null) || destinationTimeType == TimeType.Anytime) && pickupTimeType != null && destinationTimeType != null);
    }
}
