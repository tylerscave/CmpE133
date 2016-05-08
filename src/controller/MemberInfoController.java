package controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import ErrorCheck.MemberInfoErrorChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.member.Address;
import model.Context;
import model.member.Member;
import model.member.DrivingType;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the member information form 
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones,
*/
public class MemberInfoController implements Initializable {
	
    private Context context;
    private Member member;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField idNum;    
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;    
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
    
	@Override
	public void initialize(URL url, ResourceBundle rb) {
            context = Context.getInstance();
            member = context.getMember();
            email.setText(member.getLoginInfo().getEmail());
            password.setText(member.getLoginInfo().getPassword());
            firstName.setText(member.getFirstName());
            lastName.setText(member.getLastName());
            //TODO: fix ID numbers
            phone.setText(member.getPhoneNumber());
            setMemberRadio();
            setDriverRadio();
            Address address = member.getAddress();
            street.setText(address.getStreet1());
            city.setText(address.getCity());
            zipCode.setText(address.getZipCode());
	}
    
    @FXML
    protected void onEnter(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    protected void handleStatusRadio(ActionEvent event) {
    	//TODO
    }
       
    @FXML
    protected void handleVehicleButton(ActionEvent event) {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/VehicleInfoScene.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
    @FXML
    protected void handlePaymentButton(ActionEvent event) {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/PaymentMenuScene.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @FXML
    protected void handleReturnButton(ActionEvent event) {
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
    protected void handleSubmitReturnButton(ActionEvent event) throws Exception {
    	//MemberInfoErrorChecker.invalidBlank(firstName,lastName,idNum, email,phone, street, city, zipCode);
    	maintainMemberInfo();
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScene.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
            maintainMemberInfo();
            
            model.Database.add(email.getText(),password.getText(),lastName.getText(),firstName.getText(),
            		street.getText()+" "+city.getText()+ " CA "+ zipCode.getText(), phone.getText(),
            		getMemberType(),"payment method");
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }

    private void maintainMemberInfo() {
        //member.handlePersonalInfo(firstName.getText(), lastName.getText(), getStatus(), drive.isSelected());
        //member.handleContactInfo(email.getText(), phone.getText());
        //member.handleAddressInfo(street.getText(), city.getText(), zipCode.getText());
        //member.handleVehicleInfo(0, null, null, null, 0);
        //member.handlePaymentInfo();
        member.getLoginInfo().setEmail(email.getText());
        member.getLoginInfo().setPassword(password.getText());
        member.setFirstName(firstName.getText());
        member.setLastName(lastName.getText());
        member.setPhoneNumber(phone.getText());
        member.setDrivingType(getMemberType());
        member.setAddress(new Address(street.getText(), "", city.getText(), "CA", zipCode.getText()));
    }
    
    private DrivingType getMemberType() {
        //TODO: get the appropriate member type based on radio button
        if (student.isSelected())
            return null;
        else if (staff.isSelected())
            return null;
        else
            return null;
    }

    private void setMemberRadio() {
        //TODO: make the appropriate radiobutton get automatically selected
    }

    private void setDriverRadio() {
        //TODO: make the appropriate radiobutton get automatically selected    
    }

}
