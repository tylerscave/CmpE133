package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.PasswordSender;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the RetrieveAccountScene.  
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones, David Lerner
*/
public class RetrieveAccountController extends Controller{

    @FXML
    private TextField emailField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }
	
    @FXML
    private void handleCancelButton(ActionEvent event) {
    	changeScenePop(event);
    }
	
    @FXML
    private void handleSubmitButton(ActionEvent event) {
    	sendPassword(event);
    }
    
    @FXML
    private void onEnter(ActionEvent event) {
        sendPassword(event);
    }
    
    private void sendPassword(ActionEvent event) {
        PasswordSender ps = new PasswordSender();
        
        if (emailField.getText().equals("")) {
            Alerts.showError("You must enter an email");
            return;
        }
    	ps.sendPassword(emailField.getText());
        
        changeScenePop(event);
    }
}
