package controller;
/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the Member Schedule Scene
 * Solves CmpE133 Assignment 2
 * @author Tyler Jones,
*/
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Context;
import model.Member;
import model.MemberSchedule;
import model.Location;

public class MemberScheduleSceneController implements Initializable{
	
    private Context context;
    private Member member;
    private MemberSchedule memberSchedule;
    private Location selectedLocation;
    private GregorianCalendar monTime, monArriveTime, monDepartTime;
    private GregorianCalendar tuesTime, tuesArriveTime, tuesDepartTime;
    private GregorianCalendar wedTime, wedArriveTime, wedDepartTime;
    private GregorianCalendar thursTime, thursArriveTime, thursDepartTime;
    private GregorianCalendar friTime, friArriveTime, friDepartTime;
    private boolean monDrive, tuesDrive, wedDrive, thursDrive, friDrive;
    private ObservableList<Location> locations = FXCollections.observableArrayList();
    private ObservableList<GregorianCalendar> monTimes;
    private ObservableList<GregorianCalendar> tuesTimes;
    private ObservableList<GregorianCalendar> wedTimes;
    private ObservableList<GregorianCalendar> thursTimes;
    private ObservableList<GregorianCalendar> friTimes;
    
    @FXML
    private ComboBox<Location> location;
    @FXML
    private ComboBox<GregorianCalendar> monArrive;
    @FXML
    private ComboBox<GregorianCalendar> monDepart;
    @FXML
    private ComboBox<GregorianCalendar> tuesArrive;
    @FXML
    private ComboBox<GregorianCalendar> tuesDepart;
    @FXML
    private ComboBox<GregorianCalendar> wedArrive;        
    @FXML
    private ComboBox<GregorianCalendar> wedDepart;
    @FXML
    private ComboBox<GregorianCalendar> thursArrive;
    @FXML
    private ComboBox<GregorianCalendar> thursDepart;    
    @FXML
    private ComboBox<GregorianCalendar> friArrive;
    @FXML
    private ComboBox<GregorianCalendar> friDepart;
    @FXML
    private CheckBox monDriveCheck;        
    @FXML
    private CheckBox tuesDriveCheck;        
    @FXML
    private CheckBox wedDriveCheck; 
    @FXML
    private CheckBox thursDriveCheck;        
    @FXML
    private CheckBox friDriveCheck;
    
	@Override
	public void initialize(URL url, ResourceBundle rb) {
            context = Context.getInstance();
            member = context.getMember();
            memberSchedule = member.getMemberSchedule();
            
            //set up the location ComboBox
            for (Location l : context.getMap().getLocations())
                locations.add(l);
            location.setItems(locations);
            
            //setup Arrival and Departure ComboBoxes
            monTimes = timesMaker(2, monTime);
            monArrive.setItems(monTimes);
            monDepart.setItems(monTimes);
            tuesTimes = timesMaker(3, tuesTime);
            tuesArrive.setItems(tuesTimes);
            tuesDepart.setItems(tuesTimes);
            wedTimes = timesMaker(4, wedTime);
            wedArrive.setItems(wedTimes);
            wedDepart.setItems(wedTimes);
            thursTimes = timesMaker(5, thursTime);
            thursArrive.setItems(thursTimes);
            thursDepart.setItems(thursTimes);
            friTimes = timesMaker(6, friTime);
            friArrive.setItems(friTimes);
            friDepart.setItems(friTimes);
	}
	
    @FXML
    private void handleLocationCombo(ActionEvent event) {
    	selectedLocation = location.getSelectionModel().getSelectedItem();
    }
   
    @FXML
    private void handleMonArriveCombo(ActionEvent event) {
    	monArriveTime = monArrive.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleMonDepartCombo(ActionEvent event) {
    	monDepartTime = monDepart.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleTuesArriveCombo(ActionEvent event) {
    	tuesArriveTime = tuesArrive.getSelectionModel().getSelectedItem();

    }
    
    @FXML
    private void handleTuesDepartCombo(ActionEvent event) {
    	tuesDepartTime = tuesDepart.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleWedArriveCombo(ActionEvent event) {
    	wedArriveTime = wedArrive.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleWedDepartCombo(ActionEvent event) {
    	wedDepartTime = wedDepart.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleThursArriveCombo(ActionEvent event) {
    	thursArriveTime = thursArrive.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleThursDepartCombo(ActionEvent event) {
    	thursDepartTime = thursDepart.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleFriArriveCombo(ActionEvent event) {
    	friArriveTime = friArrive.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleFriDepartCombo(ActionEvent event) {
    	friDepartTime = friDepart.getSelectionModel().getSelectedItem();
    }
     
    @FXML
    private void handleDriveChecks(ActionEvent event) {
    	monDrive = monDriveCheck.isSelected();
    	tuesDrive = tuesDriveCheck.isSelected();
    	wedDrive = wedDriveCheck.isSelected();
    	thursDrive = thursDriveCheck.isSelected();
    	friDrive = friDriveCheck.isSelected();
    }
    
    @FXML
    private void handleReturnButton(ActionEvent event) {
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
    
    //NEED TO ADD ERROR CHECKING TO MAKE SURE ALL FIELDS HAVE BEEN FILLED IN
    @FXML
    private void handleSubmitButton(ActionEvent event) {
    	// set pickup location
    	memberSchedule.setPickupLocation(selectedLocation);
    	
    	// set Arrival and Departure times
    	memberSchedule.setMonArrive(monArriveTime);
    	memberSchedule.setMonDepart(monDepartTime);
    	memberSchedule.setTuesArrive(tuesArriveTime);
    	memberSchedule.setTuesDepart(tuesDepartTime);
    	memberSchedule.setWedArrive(wedArriveTime);
    	memberSchedule.setWedDepart(wedDepartTime);
    	memberSchedule.setThursArrive(thursArriveTime);
    	memberSchedule.setThursDepart(thursDepartTime);
    	memberSchedule.setFriArrive(friArriveTime);
    	memberSchedule.setFriDepart(friDepartTime);
    	
    	// set drive preferences
    	memberSchedule.setMonDrive(monDrive);
    	memberSchedule.setTuesDrive(tuesDrive);
    	memberSchedule.setWedDrive(wedDrive);
    	memberSchedule.setThursDrive(thursDrive);
    	memberSchedule.setFriDrive(friDrive);
    	
    	//FOR TESTING REMOVE LATER
    	memberSchedule.test();
    	
    	//return to home screen
    	handleReturnButton(event);
    }
    
    /**
     * timesMaker is a helper method to add GregorianCalendar objects (representing week days 
     * with specific times) to the comboboxes with appropriate Strings printed in the dropdown list
     * @param weekDay the int for that day of the week, Sunday=1,Saturday=7
     * @param day the gregorian calendar week day that you are adding times to
     * @return list of times for the Comboboxes with reference to their weekday
     */
	private ObservableList<GregorianCalendar> timesMaker(int weekDay, GregorianCalendar day) {
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
			day.set(GregorianCalendar.DAY_OF_WEEK, weekDay);
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
			day.set(GregorianCalendar.DAY_OF_WEEK, weekDay);
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
