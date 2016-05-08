package controller;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Context;
import model.member.Driver;
import model.member.Member;
import model.member.Vehicle;
import model.member.Vehicle.VehicleStyle;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the vehicle information form.  
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones,
*/
public class VehicleInfoController implements Initializable {

    private Context context;
    private Member member;
    private int vehicleYear;
    private VehicleStyle vehicleStyle;
    private int vehicleSeats;
    private ObservableList<Integer> years = FXCollections.observableArrayList();
    private ObservableList<Integer> seats = FXCollections.observableArrayList();
    @FXML
    private TextField makeField;
    @FXML
    private TextField modelField;
    @FXML
    private TextField colorField;
    @FXML
    private TextField plateNumberField;
    @FXML
    private ComboBox<Integer> yearCombo; 
    @FXML
    private ComboBox<VehicleStyle> styleCombo;
    @FXML
    private ComboBox<Integer> seatsCombo;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
        context = Context.getInstance();
        member = context.getMember();
        
        //setup year comboBox
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int minYear = 1960;
        for (int i = currentYear; i >= minYear ; i--) {
        	years.add(i);
        }
        yearCombo.setItems(years);
        
        //setup vehicle style ComboBox
        styleCombo.getItems().setAll(VehicleStyle.values());
        
        //setup seats comboBox
        ArrayList<Integer> seatsList = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7));
        seats.addAll(seatsList);
        seatsCombo.setItems(seats);		
	}
	
	@FXML
	private void handleYearCombo(ActionEvent event) {
		vehicleYear = (int) yearCombo.getSelectionModel().getSelectedItem();
	}
	
	@FXML
	private void handleStyleCombo(ActionEvent event) {
		vehicleStyle = (VehicleStyle) styleCombo.getSelectionModel().getSelectedItem();
	}
	
	@FXML
	private void handleSeatsCombo(ActionEvent event) {
		vehicleSeats = (int) seatsCombo.getSelectionModel().getSelectedItem();
	}
	
	@FXML
    private void handleCancelButton(ActionEvent event) {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MemberInfoScene.fxml"));
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
    	Vehicle vehicle = new Vehicle(vehicleYear, makeField.getText(), modelField.getText(),
    			colorField.getText(), plateNumberField.getText(), vehicleStyle, vehicleSeats);
    	if (member.getDrivingType().isDriver()); {
            Driver d = (Driver) member.getDrivingType();
            d.setVehicle(vehicle);
    }
    	
    	
    	//JUST TESTING FOR NOW REMOVE PRINTS LATER
    	System.out.println("year = " + vehicleYear);
    	System.out.println("make = " + makeField.getText());
    	System.out.println("model = " + modelField.getText());
    	System.out.println("color = " + colorField.getText());
    	System.out.println("plate number = " + plateNumberField.getText());
    	System.out.println("style = " + vehicleStyle);
    	System.out.println("available seats = " + vehicleSeats);
    	
    	handleCancelButton(event);
    }
}
