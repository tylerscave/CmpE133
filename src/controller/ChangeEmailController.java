package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class for ChangeEmailScene
 *
 * @author David Lerner
 */
public class ChangeEmailController extends Controller{
    
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        
        email.setText(member.getLoginInfo().getEmail());
    }

    @FXML
    public void handleCancelButton(ActionEvent event) {
    	changeScenePop(event);
    }

    @FXML
    public void handleSubmitButton(ActionEvent event) {
        changeEmail(event);
    }
    
    @FXML
    public void onEnter(ActionEvent event) {
        changeEmail(event);
    }
    
    private void changeEmail(ActionEvent event) {
        //check to make sure fields are not empty and that passwords match
        String em = email.getText();
        String pw = password.getText();
        if (em.equals(member.getLoginInfo().getEmail())) {
            changeScenePop(event);
        } else if(em.equals("")) {
            email.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            Alerts.showError("Email field must be filled");
        } else if(pw.equals("")) {
            password.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            Alerts.showError("Password field must be filled");
        } else if(!(password.getText().equals(member.getLoginInfo().getPassword()))) {
            password.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            Alerts.showError("Incorrect password");
	} else if(!context.getLogin().emailAvailable(em)){
            email.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            Alerts.showError("An account with this email already exists");
        } else {
            member.getLoginInfo().setEmail(em);
            member.setChanged();
            member.notifyObservers();
            
            changeScenePop(event);
        }
    }
}