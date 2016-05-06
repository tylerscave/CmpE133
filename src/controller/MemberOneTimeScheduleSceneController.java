package controller;
/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the member Member One Time Schedule Scene
 * Solves CmpE133 Assignment 2
 * @author Tyler Jones,
*/

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.Temporal;
import java.util.Date;
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
import model.schedule.WeeklySchedule;
public class MemberOneTimeScheduleSceneController implements Initializable{

    private Context context;
    private Member member;
    private WeeklySchedule memberSchedule;
    private boolean drive;
    private Location selectedLocation;
    private GregorianCalendar arriveTime, departTime;
    private LocalDate date = LocalDate.now();
    private ObservableList<Location> locations = FXCollections.observableArrayList();
    private ObservableList<GregorianCalendar> times;
	
    @FXML
    private RadioButton rideRadio;
    @FXML
    private RadioButton driveRadio;
    @FXML
    private ToggleGroup group;
    @FXML
    private ComboBox<Location> locationCombo;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<GregorianCalendar> arriveCombo;
    @FXML
    private ComboBox<GregorianCalendar> departCombo;
    
	@Override
	public void initialize(URL url, ResourceBundle resources) {
        context = Context.getInstance();
        member = context.getMember();
        memberSchedule = member.getWeeklySchedule();
        
        
        //set up the location ComboBox
        for (Location l : context.getMap().getLocations())
            locations.add(l);
        locationCombo.setItems(locations);
	    locationCombo.setDisable(true);
	    locationCombo.setStyle("-fx-opacity: 0;");
        
        //setup Arrival and Departure ComboBoxes
        //they are set up for todays date until a new date is picked from datePicker
        times = timesMaker(date);
        arriveCombo.setItems(times);
        departCombo.setItems(times);
	}
	
    @FXML
    private void handleRidePrefRadios(ActionEvent event) {
    	RadioButton radio = (RadioButton) event.getSource();
    	if (radio == driveRadio) {
    		drive = true;
    	    locationCombo.setDisable(true);
    	    locationCombo.setStyle("-fx-opacity: 0;");
    	} else {
    		drive = false;
    		locationCombo.setDisable(false);
    		locationCombo.setStyle("-fx-opacity: 1;");
    	}
    }
	
    @FXML
    private void handleLocationCombo(ActionEvent event) {
    	selectedLocation = locationCombo.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleDatePicker(ActionEvent event) {
        date = datePicker.getValue();
        
        //update the arrive and depart boxes with correct date from datePicker
		times.clear();
        times = timesMaker(date);
        arriveCombo.setItems(times);
        departCombo.setItems(times);
    }
    
    @FXML
    private void handleArriveCombo(ActionEvent event) {
    	arriveTime = arriveCombo.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleDepartCombo(ActionEvent event) {
    	departTime = departCombo.getSelectionModel().getSelectedItem();
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
    
    //NEED TO ADD ERROR CHECKING TO MAKE SURE ALL FIELDS ARE FILLED IN
    @FXML
    private void handleSubmitButton(ActionEvent event) {    	
    	int weekDay = arriveTime.get(GregorianCalendar.DAY_OF_WEEK);
    	if (weekDay == 2) {
    		memberSchedule.setMonArrive(arriveTime);
    		memberSchedule.setMonDepart(departTime);
    		memberSchedule.setMonDrive(drive);
    	} else if (weekDay == 3) {
    		memberSchedule.setTuesArrive(arriveTime);
    		memberSchedule.setTuesDepart(departTime);
    		memberSchedule.setTuesDrive(drive);
    	} else if (weekDay == 4) {
    		memberSchedule.setWedArrive(arriveTime);
    		memberSchedule.setWedDepart(departTime);
    		memberSchedule.setWedDrive(drive);
    	} else if (weekDay == 5) {
    		memberSchedule.setThursArrive(arriveTime);
    		memberSchedule.setThursDepart(departTime);
    		memberSchedule.setThursDrive(drive);
    	} else if (weekDay == 6) {
    		memberSchedule.setFriArrive(arriveTime);
    		memberSchedule.setFriDepart(departTime);
    		memberSchedule.setFriDrive(drive);
    	} else {
    		System.err.println("Carpool only available on weekdays!");
    	}
    	
    	if (!drive) {
    		memberSchedule.setPickupLocation(selectedLocation);
    	}
    	handleCancelButton(event);
    	
    	//TEST OUTPUT - ERASE LATER
    	memberSchedule.test();
    	System.out.println("arrivalTime picked = " +  arriveTime.getDisplayName(GregorianCalendar.DAY_OF_WEEK, 
    					GregorianCalendar.LONG, Locale.getDefault()) + " " +
    					(arriveTime.get(GregorianCalendar.MONTH)+1) + "/" + //GregorianCalendar Jan=0
    					arriveTime.get(GregorianCalendar.DAY_OF_MONTH) + "/" +
    					arriveTime.get(GregorianCalendar.YEAR) + " " +
    					arriveTime.get(GregorianCalendar.HOUR) + 
    					arriveTime.getDisplayName(GregorianCalendar.AM_PM, 
    							GregorianCalendar.LONG, Locale.getDefault()));
    }
	
    /**
     * timesMaker is a helper method to add GregorianCalendar objects (representing week days 
     * with specific times) to the comboboxes with appropriate Strings printed in the dropdown list
     * @param date the LocalDate from datePicker to be converted to a GregorianCalendar object
     * @return list of times for the Comboboxes with reference to their weekday
     */
	private ObservableList<GregorianCalendar> timesMaker(LocalDate date) {
		times = FXCollections.observableArrayList();
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
			times.add(day);
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
        	times.add(day);
		}
		return times;
	}
}
