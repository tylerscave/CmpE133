package controller;
/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the member information form 
 * Solves CmpE133 Assignment 2
 * @author Tyler Jones,
*/
import java.io.IOException;
import java.net.URL;
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
    private MemberSchedule memberSchedule = new MemberSchedule();
    private ObservableList<Location> locations = FXCollections.observableArrayList();
    private ObservableList<String> times = FXCollections.observableArrayList();
    @FXML
    private ComboBox<Location> location;
    @FXML
    private ComboBox<String> monArrive;
    @FXML
    private ComboBox<String> monDepart;
    @FXML
    private ComboBox<String> tuesArrive;
    @FXML
    private ComboBox<String> tuesDepart;
    @FXML
    private ComboBox<String> wedArrive;        
    @FXML
    private ComboBox<String> wedDepart;
    @FXML
    private ComboBox<String> thursArrive;
    @FXML
    private ComboBox<String> thursDepart;    
    @FXML
    private ComboBox<String> friArrive;
    @FXML
    private ComboBox<String> friDepart;
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
            //memberSchedule = context.getMemberSchedule();
            
            //set up the location ComboBox
            locations.add(new Location("location1"));
            locations.add(new Location("location2"));
            locations.add(new Location("location3"));
            locations.add(new Location("location4"));
            locations.add(new Location("location5"));
            location.setItems(locations);
            
            //setup Arrival and Departure ComboBoxes
            times.addAll("6AM", "7AM", "8AM", "9AM", "10AM", "11AM", "12PM", "1PM", 
            		"2PM", "3PM", "4PM", "5PM", "6PM", "7PM", "8PM", "9PM", "10PM");
            monArrive.setItems(times);
            tuesArrive.setItems(times);
            wedArrive.setItems(times);
            thursArrive.setItems(times);
            friArrive.setItems(times);
            monDepart.setItems(times);
            tuesDepart.setItems(times);
            wedDepart.setItems(times);
            thursDepart.setItems(times);
            friDepart.setItems(times);
	}
	
    @FXML
    private void handleLocationCombo(ActionEvent event) {
    	Location selectedLocation = location.getSelectionModel().getSelectedItem();
    	//Location.setLocation(selectedLocation);
    	//test - remove later
    	System.out.println(selectedLocation);
    }
   
    @FXML
    private void handleMonArriveCombo(ActionEvent event) {
    	String monArriveTime = monArrive.getSelectionModel().getSelectedItem();
    	memberSchedule.setMonArrive(monArriveTime);
    	//test - remove later
    	System.out.println(monArriveTime);
    }
    
    @FXML
    private void handleMonDepartCombo(ActionEvent event) {
    	String monDepartTime = monDepart.getSelectionModel().getSelectedItem();
    	memberSchedule.setMonDepart(monDepartTime);
    }
    
    @FXML
    private void handleTuesArriveCombo(ActionEvent event) {
    	String tuesArriveTime = tuesArrive.getSelectionModel().getSelectedItem();
    	memberSchedule.setTuesArrive(tuesArriveTime);
    }
    
    @FXML
    private void handleTuesDepartCombo(ActionEvent event) {
    	String tuesDepartTime = tuesDepart.getSelectionModel().getSelectedItem();
    	memberSchedule.setTuesDepart(tuesDepartTime);    }
    
    @FXML
    private void handleWedArriveCombo(ActionEvent event) {
    	String wedArriveTime = wedArrive.getSelectionModel().getSelectedItem();
    	memberSchedule.setWedArrive(wedArriveTime);
    }
    
    @FXML
    private void handleWedDepartCombo(ActionEvent event) {
    	String wedDepartTime = wedDepart.getSelectionModel().getSelectedItem();
    	memberSchedule.setWedDepart(wedDepartTime);
    }
    
    @FXML
    private void handleThursArriveCombo(ActionEvent event) {
    	String thursArriveTime = thursArrive.getSelectionModel().getSelectedItem();
    	memberSchedule.setThursArrive(thursArriveTime);
    }
    
    @FXML
    private void handleThursDepartCombo(ActionEvent event) {
    	String thursDepartTime = thursDepart.getSelectionModel().getSelectedItem();
    	memberSchedule.setThursDepart(thursDepartTime);
    }
    
    @FXML
    private void handleFriArriveCombo(ActionEvent event) {
    	String friArriveTime = friArrive.getSelectionModel().getSelectedItem();
    	memberSchedule.setFriArrive(friArriveTime);
    }
    
    @FXML
    private void handleFriDepartCombo(ActionEvent event) {
    	String friDepartTime = friDepart.getSelectionModel().getSelectedItem();
    	memberSchedule.setFriDepart(friDepartTime);
    }
     
    @FXML
    private void handleDriveChecks(ActionEvent event) {
    	if (monDriveCheck.isSelected()) {
    		memberSchedule.setMonDrive(true);
    	} else if (tuesDriveCheck.isSelected()) {
    		memberSchedule.setTuesDrive(true);
    	} else if (wedDriveCheck.isSelected()) {
    		memberSchedule.setWedDrive(true);
    	} else if (thursDriveCheck.isSelected()) {
    		memberSchedule.setThursDrive(true);
    	} else if (friDriveCheck.isSelected()) {
    		memberSchedule.setFriDrive(true);
    	}
    	System.out.println("A check box was selected");
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
    	System.out.println("Return Button Pressed");
    }

}
