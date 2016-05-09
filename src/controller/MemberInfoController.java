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
import model.member.MemberBuilder;
import model.member.MemberType;
import model.member.Staff;
import model.member.Student;
import model.member.DrivingType;
import model.member.Faculty;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the member information form 
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones,
*/
public class MemberInfoController implements Initializable {
	
    private Context context;
    private Member member;
    private MemberType memberType;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField idNum;    
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
    
	@Override
	public void initialize(URL url, ResourceBundle rb) {
            context = Context.getInstance();
            member = context.getMember();
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
            email.setText(member.getLoginInfo().getEmail());
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
    private void handlePaymentButton(ActionEvent event) {
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
    private void handleUpdateLogin(ActionEvent event) {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/CreateAccountScene.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        } 
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
    
    @FXML
    private void handleSubmitReturnButton(ActionEvent event) throws Exception {
    	//MemberInfoErrorChecker.invalidBlank(firstName,lastName,idNum, email,phone, street, city, zipCode);
    	maintainMemberInfo();
        member.setChanged();
        member.notifyObservers();
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScene.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
            
            model.Database.add(email.getText(),member.getLoginInfo().getPassword(),lastName.getText(),firstName.getText(),
            		street.getText()+" "+city.getText()+ " CA "+ zipCode.getText(), phone.getText(),
            		memberType,"payment method");
            
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }     
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
        member.getLoginInfo().setEmail(email.getText());
        member.setPhoneNumber(phone.getText());
        member.setMemberType(memberType);
        member.setAddress(new Address(street.getText(), "", city.getText(), "CA", zipCode.getText()));
    }
}
