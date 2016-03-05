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
import model.Location;
import model.Member;
import model.MemberSchedule;
public class MemberOneTimeScheduleSceneController implements Initializable{

    private MemberSchedule memberSchedule = new MemberSchedule();
    private Context context;
    private Member member;
    private boolean drive;
    private Location selectedLocation;
    private GregorianCalendar selectedDate, arriveTime, departTime, day;
    //private LocalDate date = LocalDate.now();
    private LocalDate date;
    private ObservableList<Location> locations = FXCollections.observableArrayList();
    private ObservableList<GregorianCalendar> times;
	
    @FXML
    private RadioButton rideRadio;
    @FXML
    private RadioButton driveRadio;
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
        
        //set up the location ComboBox
        locations.add(new Location("location1"));
        locations.add(new Location("location2"));
        locations.add(new Location("location3"));
        locations.add(new Location("location4"));
        locations.add(new Location("location5"));
        locationCombo.setItems(locations);
        
        //setup Arrival and Departure ComboBoxes
        //times = timesMaker(selectedDate);
        //times = timesMaker(date);
        //arriveCombo.setItems(times);
        //departCombo.setItems(times);
	}
	
    @FXML
    private void handleRidePrefRadios(ActionEvent event) {
    	final ToggleGroup group = new ToggleGroup();
    	rideRadio = new RadioButton();
    	driveRadio = new RadioButton();
    	rideRadio.setToggleGroup(group);
    	driveRadio.setToggleGroup(group);
    	group.getSelectedToggle();
    	if (driveRadio.isSelected()) {
    		drive = true;
    	} else {
    		drive = false;
    	}
    }
	
    @FXML
    private void handleLocationCombo(ActionEvent event) {
    	selectedLocation = locationCombo.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleDatePicker(ActionEvent event) {
        date = datePicker.getValue();
        times = timesMaker(date);
        //selectedDate = new GregorianCalendar();
        // GregorianCalendar Sunday = 1, LocalDate Monday = 1
        //int weekDay = date.getDayOfWeek().getValue() + 1;
        
        //selectedDate.set(GregorianCalendar.DAY_OF_WEEK, weekDay);
        //selectedDate.set(GregorianCalendar.DAY_OF_MONTH, date.getDayOfMonth());
        //selectedDate.set(GregorianCalendar.DAY_OF_MONTH, date.getMonthValue());
        //selectedDate.set(GregorianCalendar.YEAR, date.getYear());
    }
    
    @FXML
    private void handleArriveCombo(ActionEvent event) {
    	arriveTime = arriveCombo.getSelectionModel().getSelectedItem();
    	System.out.println("date picked = " + arriveTime.get(GregorianCalendar.DAY_OF_MONTH));
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
    
    @FXML
    private void handleSubmitButton(ActionEvent event) {

    	handleCancelButton(event);
    }
	
    /**
     * timesMaker is a helper method to add GregorianCalendar objects (representing week days 
     * with specific times) to the comboboxes with appropriate Strings printed in the dropdown list
     * @param weekDay the int for that day of the week, Sunday=1,Saturday=7
     * @param day the gregorian calendar week day that you are adding times to
     * @return list of times for the Comboboxes with reference to their weekday
     */
	private ObservableList<GregorianCalendar> timesMaker(LocalDate date) {
        day = new GregorianCalendar();
        // GregorianCalendar Sunday = 1, LocalDate Monday = 1
        int weekDay = date.getDayOfWeek().getValue() + 1;
        
        day.set(GregorianCalendar.DAY_OF_WEEK, weekDay);
        day.set(GregorianCalendar.DAY_OF_MONTH, date.getDayOfMonth());
        day.set(GregorianCalendar.DAY_OF_MONTH, date.getMonthValue());
        day.set(GregorianCalendar.YEAR, date.getYear());
		ObservableList<GregorianCalendar> times = FXCollections.observableArrayList();
		// get times for 6AM to 11AM
		for (int i = 6; i <= 11; i++) {
			day = new GregorianCalendar() {
	            public String toString() {
	                return Integer.toString(this.get(GregorianCalendar.HOUR))
	                		+ this.getDisplayName(GregorianCalendar.AM_PM, 
	    							GregorianCalendar.LONG, Locale.getDefault());
	            	
	            }
	        };
			//day.set(GregorianCalendar.DAY_OF_WEEK, weekDay);
			day.set(GregorianCalendar.AM_PM, GregorianCalendar.AM);
			day.set(GregorianCalendar.HOUR, i);
			day.clear(GregorianCalendar.MINUTE);
			day.clear(GregorianCalendar.SECOND);
			day.clear(GregorianCalendar.MILLISECOND);
			times.add(day);
		}
		
		// get times for noon to 10pm
		for (int i = 12; i <= 22; i++) {
			day = new GregorianCalendar() {
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
			//day.set(GregorianCalendar.DAY_OF_WEEK, weekDay);
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
