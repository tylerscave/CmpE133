package controller;
/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the member information form 
 * Solves CmpE133 Assignment 2
 * @author Tyler Jones,
*/

import model.LoginHandler;
import model.MemberHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class MemberSceneController implements Initializable {
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO	
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
    protected void handleStatusRadio() {
    	//TODO
    }
    
    @FXML
    protected void handleRidePrefRadio() {
    	//TODO
    }
    
    @FXML
    protected void handleVehicleButton() {
    	//TODO
    }
    
    @FXML
    protected void handlePaymentButton() {
    	//TODO
    }

    private void maintainMember(ActionEvent event) {
        MemberHandler handler = new MemberHandler();
        //TODO
    }

}
