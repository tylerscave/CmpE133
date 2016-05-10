package controller;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Context;
import model.StringFormat;
import model.schedule.Drive;
import model.schedule.Location;
import model.schedule.Request;
import model.schedule.Ride;
import model.member.Member;
import model.member.Passenger;
import model.schedule.Schedulable;
import model.schedule.ScheduleViewer;
import model.schedule.Scheduler;
import model.schedule.SchedulingContext;
import model.schedule.Request.TimeType;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the RideRequestScene
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones,
*/
public class RideRequestController implements Initializable {
	
    private Context context;
    private SchedulingContext sc;
    private Member member;
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
        context = Context.getInstance();
        member = context.getMember();
        
        member.setDrivingType(new Passenger());
        
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
		destinationTimeType = destinationTimeTypeCombo.getSelectionModel().getSelectedItem();
		if(checkData()) {
			makeRideRequest();
		}
	}
	
	@FXML
	private void handlePickRideCombo(ActionEvent event) {
		if(pickRideCombo.getSelectionModel().getSelectedItem() instanceof Request) {
			selectedRide = (Drive)pickRideCombo.getSelectionModel().getSelectedItem();
		} else {
			selectedRide = null;
		}
	}

    @FXML
    private void handleCancelButton(ActionEvent event) {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScene.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void handleSubmitButton(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setResizable(true);
    	alert.getDialogPane().setPrefSize(500, 250);
    	if(selectedRide == null) {
    		//make a new RideRequest with given data from form
    		sc.addRideToRequests(request, member.getFirstName());
    		String requestAlert = "From "+pickup+ " ";
    		requestAlert = requestAlert+pickupSelectedDateTime.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
    				GregorianCalendar.LONG, Locale.getDefault()) + " " +
    				(pickupSelectedDateTime.get(GregorianCalendar.MONTH)+1) + "/" + //GregorianCalendar Jan=0
    				pickupSelectedDateTime.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
    				pickupSelectedDateTime.get(GregorianCalendar.YEAR) + " " +
    				pickupSelectedDateTime.get(GregorianCalendar.HOUR) + ":" +
    				pickupSelectedDateTime.get(GregorianCalendar.MINUTE) + " " +
    				pickupSelectedDateTime.getDisplayName(GregorianCalendar.AM_PM, 
    						GregorianCalendar.LONG, Locale.getDefault());
    		requestAlert = requestAlert+"\nTo "+destination;
    		requestAlert = requestAlert+destinationSelectedDateTime.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
    				GregorianCalendar.LONG, Locale.getDefault()) + " " +
    				(destinationSelectedDateTime.get(GregorianCalendar.MONTH)+1) + "/" + //GregorianCalendar Jan=0
    				destinationSelectedDateTime.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
    				destinationSelectedDateTime.get(GregorianCalendar.YEAR) + " " +
    				destinationSelectedDateTime.get(GregorianCalendar.HOUR) + ":" +
    				destinationSelectedDateTime.get(GregorianCalendar.MINUTE) + " " +
    				destinationSelectedDateTime.getDisplayName(GregorianCalendar.AM_PM, 
    						GregorianCalendar.LONG, Locale.getDefault());
        	alert.setTitle("Schedule Information");
        	alert.setHeaderText("New Ride Request Made!");
        	alert.setContentText(requestAlert);
        	alert.showAndWait();
    	} else {
            //get selected drive
     		String fail = sc.schedule(request, selectedRide);
            if (fail.equals(Scheduler.SUCCESS)) {
                Ride ride = request.getMember().getRides().get(request.getMember().getRides().size()-1);
                List<Location> stops = ride.getRoute().getStops();
                Drive drive = (new ScheduleViewer()).getDriveById(ride.getDriveId());
            	String alertMsg = "on "+StringFormat.getDateFromCalendar(ride.getEndTime())+
            			"\nFrom "+stops.get(0)+" at "+StringFormat.getTimeFromCalendar(ride.getStartTime())+
            			"\nTo "+stops.get(stops.size()-1)+" at "+StringFormat.getTimeFromCalendar(ride.getEndTime())+    			
            			"\nYour Driver is "+drive.getMemberName();
            	alert.setTitle("Schedule Information");
            	alert.setHeaderText("New Ride Scheduled!");
            	alert.setContentText(alertMsg);
            	alert.showAndWait();
            } else {
            	Alert errorAlert = new Alert(AlertType.ERROR);
            	errorAlert.setTitle("Schedule Information");
            	errorAlert.setHeaderText(null);
            	errorAlert.setContentText(fail);
            	errorAlert.showAndWait();
            }
    	}
    	handleCancelButton(event);
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
		GregorianCalendar startTime = new GregorianCalendar();
		GregorianCalendar endTime = new GregorianCalendar();
		pickupHourTime.set(GregorianCalendar.MINUTE, pickupMinuteTime);
		destinationHourTime.set(GregorianCalendar.MINUTE, destinationMinuteTime);
		TimeType startType = pickupTimeType;
		TimeType endType = destinationTimeType;
		pickupSelectedDateTime = pickupHourTime;
		destinationSelectedDateTime = destinationHourTime;
		startTime = pickupSelectedDateTime;
		endTime = destinationSelectedDateTime;
 
        //setup dropdown of available rides
        request = new Request(member, startTime, endTime, pickup, destination, startType, endType);
        sc = new SchedulingContext();
        List<Schedulable> drives = sc.getAvailable(request);   
        rideChoices.addAll(drives);
        rideChoices.add("Don't see the ride you want? Make a New Ride Request");
        pickRideCombo.setItems(rideChoices);		
	}

    private boolean checkData() {
    	return (pickup != null && destination != null && pickupHourTime != null && destinationHourTime != null &&
    			pickupMinuteTime != null && destinationMinuteTime != null && pickupTimeType != null && destinationTimeType != null);

    }
}
