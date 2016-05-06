package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Context;
import model.schedule.Location;
import model.member.Member;
import model.schedule.DriveChoice;
import model.schedule.WeeklySchedule;
import model.schedule.RideRequest;
import model.schedule.RideRequest.TimeType;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the PickDriverScheduleScene
 * Solves CmpE133 Carpool System
 * @author Tyler Jones,
*/

public class PickRiderScheduleSceneController implements Initializable {
    private Context context;
    private Member member;
    private WeeklySchedule memberSchedule;
    private Location pickup, destination;
    private LocalDate date;
    private GregorianCalendar hourTime, selectedDateTime;
    private int minuteTime;
    private boolean byStartTime;
    private TimeType timeType;
//    private DriveChoice selectedRider;	//NEED TO CHANGE THIS 
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
    private ToggleGroup group;
    @FXML
    private ComboBox<GregorianCalendar> hourCombo;
    @FXML
    private ComboBox<Integer> minuteCombo;
    @FXML
    private ComboBox<TimeType> timeTypeCombo;
    @FXML
    private ComboBox<DriveChoice> pickRiderCombo;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
        context = Context.getInstance();
        member = context.getMember();
        memberSchedule = member.getWeeklySchedule();
        
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
        
        //setup timeType ComboBox
        timeTypeCombo.getItems().setAll(TimeType.values());
		
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
		// after all needed data is collected make the ride request to populate the pickRideCombo box
		makeDriveRequest();
	}
	
	@FXML
	private void handleTimeTypeCombo(ActionEvent event) {
		timeType = timeTypeCombo.getSelectionModel().getSelectedItem();
	}
	
	@FXML
	private void handlePickRiderCombo(ActionEvent event) {
//		selectedRider = pickRiderCombo.getSelectionModel().getSelectedItem();
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
    	//store the selected ride somewhere like Ride?
    	
    	//TEST OUTPUT - REMOVE LATER
    	System.out.println("selected date = " +  selectedDateTime.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
				GregorianCalendar.LONG, Locale.getDefault()) + " " +
				(selectedDateTime.get(GregorianCalendar.MONTH)+1) + "/" + //GregorianCalendar Jan=0
				selectedDateTime.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
				selectedDateTime.get(GregorianCalendar.YEAR) + " " +
				selectedDateTime.get(GregorianCalendar.HOUR) + ":" +
				selectedDateTime.get(GregorianCalendar.MINUTE) + " " +
				selectedDateTime.getDisplayName(GregorianCalendar.AM_PM, 
						GregorianCalendar.LONG, Locale.getDefault()));
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
	 * makeDriveRequest is called after all data needed has been collected. This method
	 * makes a request and populates the pickRiderCombo box with a list of available 
	 * drives by rider that can then be selected
	 */
	private void makeDriveRequest() {
		GregorianCalendar startTime = new GregorianCalendar();
		GregorianCalendar endTime = new GregorianCalendar();;
		TimeType startType = null;
		TimeType endType =null;
		hourTime.set(GregorianCalendar.MINUTE, minuteTime);
		selectedDateTime = hourTime;
		if(byStartTime) {
			startTime = selectedDateTime;
			startType = timeType;
		} else {
			endTime = selectedDateTime;
			endType = timeType;
		}
		
		
	}

}
