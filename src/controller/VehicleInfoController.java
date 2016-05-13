package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.member.Driver;
import model.member.Vehicle;
import model.member.Vehicle.VehicleStyle;
import model.payment.PayByDistCalculator;
import model.payment.PayByTimeCalculator;
import model.payment.PayFlatCalculator;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the vehicle information form.  
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones,
*/
public class VehicleInfoController extends Controller{

    private int vehicleYear;
    private VehicleStyle vehicleStyle;
    private int vehicleSeats;
    private ObservableList<Integer> years = FXCollections.observableArrayList();
    private ObservableList<Integer> seats = FXCollections.observableArrayList();
    private ObservableList<String> payBys = FXCollections.observableArrayList();
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
    @FXML
    private ComboBox<String> payCombo;
    @FXML
    private Text money;
    
    HashMap<String, String> details;
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
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
        ArrayList<Integer> seatsList = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7));
        seats.addAll(seatsList);
        seatsCombo.setItems(seats);
        
        //poorly designed payment combobox
        ArrayList<String> paycalculators = new ArrayList<>();
        details = new HashMap<>();
        paycalculators.add("Free");
        details.put("Free", String.format("$%.2f", 0.0));
        paycalculators.add("By distance");
        details.put("By distance", String.format("$%.2f per mile default", Driver.PER_MILE_RATE));
        paycalculators.add("Hourly");
        details.put("Hourly", String.format("$%.2f per hour default", Driver.HOURLY_RATE));
        paycalculators.add("Flat");
        details.put("Flat", String.format("$%.2f flat default", Driver.FLAT_RATE));
        payBys.addAll(paycalculators);
        payCombo.setItems(payBys);
        
        //set current
        Driver d ;
        Vehicle v;
        if (member.getDrivingType().isDriver()) {
            d = (Driver) member.getDrivingType();
            v = d.getVehicle();
            makeField.setText(v.getManufacturer());
            modelField.setText(v.getModel());
            colorField.setText(v.getColor());
            plateNumberField.setText(v.getPlateNumber());
            vehicleYear = v.getYear();
            yearCombo.setValue(vehicleYear);
            vehicleStyle = v.getStyle();
            styleCombo.setValue(vehicleStyle);
            vehicleSeats = v.getCapacity();
            seatsCombo.setValue(vehicleSeats);
            
            if (d.getPayBy() instanceof PayByDistCalculator)
                payCombo.setValue("By distance");
            else if (d.getPayBy() instanceof PayByTimeCalculator)
                payCombo.setValue("Hourly");
            else if (d.getPayBy() instanceof PayFlatCalculator)
                payCombo.setValue("Flat");
            else if (d.getPayBy() instanceof PayFlatCalculator && d.getFlatrate() == 0)
                payCombo.setValue("Free");
            
            money.setText(details.get((String)payCombo.getSelectionModel().getSelectedItem()));
        }
    }
	
    @FXML
    private void handleYearCombo(ActionEvent event) {
        vehicleYear = yearCombo.getSelectionModel().getSelectedItem();
    }
	
    @FXML
    private void handleStyleCombo(ActionEvent event) {
        vehicleStyle = styleCombo.getSelectionModel().getSelectedItem();
    }
	
    @FXML
    private void handleSeatsCombo(ActionEvent event) {
        vehicleSeats = seatsCombo.getSelectionModel().getSelectedItem();
    }
	
    @FXML
    private void handleCancelButton(ActionEvent event) {
        changeScenePop(event);
    }
	
    @FXML
    private void handleSubmitButton(ActionEvent event) {
    	Vehicle vehicle = new Vehicle(vehicleYear, makeField.getText(), modelField.getText(),
    			colorField.getText(), plateNumberField.getText(), vehicleStyle, vehicleSeats);
    	if (member.getDrivingType().isDriver()) {
            Driver d = (Driver) member.getDrivingType();
            d.setVehicle(vehicle);
            String payment = (String)payCombo.getSelectionModel().getSelectedItem();
            if (payment.equals("By distance")) {
                d.setPayBy(new PayByDistCalculator());
                d.setPerMileRate(Driver.PER_MILE_RATE);
            }
            else if (payment.equals("Hourly")) {
                d.setPayBy(new PayByTimeCalculator());
                d.setHourlyRate(Driver.HOURLY_RATE);
            }
            else if (payment.equals("Flat")) {
                d.setPayBy(new PayFlatCalculator());
                d.setFlatrate(Driver.FLAT_RATE);
            }
            else if (payment.equals("Free")) {
                d.setPayBy(new PayFlatCalculator());
                d.setFlatrate(0);
            }
    	} 	
        member.setChanged();
        member.notifyObservers();
    	changeScenePop(event);
    }
    
    @FXML
    private void handlePayCombo(ActionEvent event) {
        money.setText(details.get((String)payCombo.getSelectionModel().getSelectedItem()));
    }
}
