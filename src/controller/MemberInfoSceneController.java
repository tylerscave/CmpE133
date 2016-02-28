package controller;
/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the member information form 
 * Solves CmpE133 Assignment 2
 * @author Tyler Jones,
*/

import java.io.IOException;
import model.LoginHandler;
import model.MemberHandler;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Context;

public class MemberInfoSceneController implements Initializable {
	
    private Context context;
    private MemberHandler member;
    
	@Override
	public void initialize(URL url, ResourceBundle rb) {
            context = Context.getInstance();
            member = context.getMember();
	}
	
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;    
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
    private RadioButton drive;        
    @FXML
    private RadioButton ride;
    
    @FXML
    protected void onEnter(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    protected void handleStatusRadio(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    protected void handleRidePrefRadio(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    protected void handleVehicleButton(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    protected void handlePaymentButton(ActionEvent event) {
    	//TODO
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
    protected void handleSubmitReturnButton(ActionEvent event) {
        maintainMemberInfo();
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

    private void maintainMemberInfo() {
        member.handlePersonalInfo(firstName.getText(), lastName.getText(), getStatus(), drive.isSelected());
        member.handleContactInfo(email.getText(), phone.getText());
        member.handleAddressInfo(street.getText(), city.getText(), zipCode.getText());
        //member.handleVehicleInfo(0, null, null, null, 0);
        //member.handlePaymentInfo();
    }
    
    private String getStatus() {
        if (student.isSelected())
            return "Student";
        else if (staff.isSelected())
            return "Staff";
        else
            return "Faculty";
    }

}
