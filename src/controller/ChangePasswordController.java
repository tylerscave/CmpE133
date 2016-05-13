package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import model.member.LoginInformation;

/**
 * FXML Controller class for ChangePasswordScene.
 *
 * @author David Lerner
 */
public class ChangePasswordController extends Controller {
    
    @FXML
    private PasswordField old;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
	
    @FXML
    public void handleCancelButton(ActionEvent event) throws Exception {
    	changeScenePop(event);
    }

    @FXML
    public void handleSubmitButton(ActionEvent event) throws Exception {
        //check to make sure passwords match and old passord is correct
        if (!old.getText().equals(member.getLoginInfo().getPassword())) {
            old.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            Alerts.showError("Incorrect passord!");
        }
        else if(!(password.getText().equals(confirmPassword.getText()))) {
            confirmPassword.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            Alerts.showError("Passwords must match!");
        } else {
            member.getLoginInfo().setPassword(password.getText());
            member.setChanged();
            member.notifyObservers();
            
            changeScenePop(event);
	}
    }
}
