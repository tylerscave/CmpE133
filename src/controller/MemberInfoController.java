package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import model.member.Address;
import model.member.Driver;
import model.member.MemberType;
import model.member.Staff;
import model.member.Student;
import model.member.Faculty;
import model.member.Passenger;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the member information form 
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones, David Lerner
*/
public class MemberInfoController extends Controller{

    private MemberType memberType;
    
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField idNum;    
    @FXML
    private TextField phone;
    @FXML
    private TextField street;
    @FXML
    private TextField city;
    @FXML
    private TextField zipCode;
    @FXML
    private RadioButton student;        
    @FXML
    private RadioButton staff;        
    @FXML
    private RadioButton faculty;
    @FXML
    private RadioButton driver;        
    @FXML
    private RadioButton rider; 
    @FXML
    private Button vehicle;
    @FXML
    private Label typeLabel; 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        //fill fields with data
        firstName.setText(member.getFirstName());
        lastName.setText(member.getLastName());
        if(member.getMemberType() instanceof Staff) {
            staff.setSelected(true);
            idNum.setText(((Staff)member.getMemberType()).getSjsuID());
        } else if(member.getMemberType() instanceof Faculty) {
            faculty.setSelected(true);
            idNum.setText(((Faculty)member.getMemberType()).getSjsuID());
        } else {
            student.setSelected(true);
            idNum.setText(((Student)member.getMemberType()).getStudentID());
            memberType = new Student("000000000");
        }
        if(member.getDrivingType().isDriver()) {
            driver.setSelected(true);
            vehicle.setVisible(true);
            typeLabel.setVisible(true);
        } else {
            rider.setSelected(true);
            vehicle.setVisible(false);
            typeLabel.setVisible(false);
        } 
        phone.setText(member.getPhoneNumber());
        Address address = member.getAddress();
        street.setText(address.getStreet1());
        city.setText(address.getCity());
        zipCode.setText(address.getZipCode());
    }
        
    @FXML
    private void handleStatusRadio(ActionEvent event) {
        RadioButton radio = (RadioButton) event.getSource();
        if(radio == staff) {
            memberType = new Staff(idNum.getText());
        } else if(radio == faculty) {
            memberType = new Faculty(idNum.getText());
        } else {
            memberType = new Student(idNum.getText());
        }
    }
    
    @FXML
    private void handleDriveRadio(ActionEvent event) {
    	RadioButton radio = (RadioButton) event.getSource();
    	if(radio == rider) {
            vehicle.setVisible(false);
            typeLabel.setVisible(false);
    	} else {
            vehicle.setVisible(true);
            typeLabel.setVisible(true);
	}
    }
    
    @FXML
    private void handleVehicleButton(ActionEvent event) {
        maintainMemberInfo();
        changeScenePush(event, "/view/VehicleInfoScene.fxml");
    }
    
    @FXML
    private void handlePaymentButton(ActionEvent event) {
        maintainMemberInfo();
        changeScenePush(event, "/view/PaymentMenuScene.fxml");
    }
    
    @FXML
    private void handleUpdatePassord(ActionEvent event) {
        maintainMemberInfo();
        changeScenePush(event, "/view/ChangePasswordScene.fxml");
    }
    
    @FXML
    private void handleUpdateEmail(ActionEvent event) {
        maintainMemberInfo();
        changeScenePush(event, "/view/ChangeEmailScene.fxml");
    }
    
    @FXML
    private void handleReturnButton(ActionEvent event) {
        //check to make sure fields are filled
        boolean filled = true;
        if (firstName.getText().equals("")) {
            filled = false;
            firstName.setStyle("-fx-control-inner-background: #FF0000");
        }
        if (lastName.getText().equals("")) {
            filled = false;
            lastName.setStyle("-fx-control-inner-background: #FF0000");
        }
        if (idNum.getText().equals("")) {
            filled = false;
            idNum.setStyle("-fx-control-inner-background: #FF0000");
        }
        if (!filled) {
            Alerts.showError("Please fill the highlighted fields");
            return;
        }
        
        changeScene(event, "/view/HomeScene.fxml");
    }
    
    @FXML
    private void handleSubmitReturnButton(ActionEvent event) throws Exception {
        //check to make sure fields are filled
        boolean filled = true;
        if (firstName.getText().equals("")) {
            filled = false;
            firstName.setStyle("-fx-control-inner-background: #FF0000");
        }
        if (lastName.getText().equals("")) {
            filled = false;
            lastName.setStyle("-fx-control-inner-background: #FF0000");
        }
        if (idNum.getText().equals("")) {
            filled = false;
            idNum.setStyle("-fx-control-inner-background: #FF0000");
        }
        if (!filled) {
            Alerts.showError("Please fill the highlighted fields");
            return;
        }
        
    	maintainMemberInfo();
        member.setChanged();
        member.notifyObservers();
    	changeScene(event, "/view/HomeScene.fxml");
    }

    private void maintainMemberInfo() {
        member.setFirstName(firstName.getText());
        member.setLastName(lastName.getText());
        if(memberType instanceof Staff) {
        	((Staff)memberType).setSjsuID(idNum.getText());
        } else if (memberType instanceof Faculty) {
        	((Faculty)memberType).setSjsuID(idNum.getText());
        } else {
        	((Student)memberType).setStudentID(idNum.getText());
        }
        if (rider.isSelected())
            member.setDrivingType(new Passenger());
        else if (!member.getDrivingType().isDriver())
            member.setDrivingType(new Driver());
        member.setPhoneNumber(phone.getText());
        member.setMemberType(memberType);
        member.setAddress(new Address(street.getText(), "", city.getText(), "CA", zipCode.getText()));
    }
}
