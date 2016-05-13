package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.LoginHandler;
import model.member.LoginInformation;
import model.member.MemberBuilder;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the CreateAccountScene
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones, David Lerner
*/
public class CreateAccountController extends Controller{
    
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    @FXML
    public void handleCancelButton(ActionEvent event) {
    	   changeScenePop(event);
    }

    @FXML
    public void handleSubmitButton(ActionEvent event) {
        createAccount(event);
    }
    
    @FXML
    public void onEnter(ActionEvent event) {
        createAccount(event);
    }
    
    private void createAccount(ActionEvent event) {
        //check to make sure fields are not empty and that passwords match
        String em = email.getText();
        String pw = password.getText();
        String cp = confirmPassword.getText();
        if(em.equals("")) {
            email.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            Alerts.showError("Email field must be filled");
        } else if(pw.equals("")) {
            password.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            Alerts.showError("Password field must be filled");
        } else if(cp.equals("")) {
            confirmPassword.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            Alerts.showError("Confirm password field must be filled");
        } else if(!(password.getText().equals(confirmPassword.getText()))) {
            confirmPassword.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            Alerts.showError("Passwords must match");
	} else {
            MemberBuilder mb = new MemberBuilder();
            LoginInformation newInfo = new LoginInformation(em, pw); 
            mb.setLoginInfo(newInfo);
            if (mb.build() < 0) {
                Alerts.showError("An account with this email already exists");
                return;
            }
            LoginHandler loginHandler = context.getLogin();
            if (loginHandler.isLoggedIn())
                loginHandler.handleLogout();
            loginHandler.handleLogin(newInfo);
            if (!loginHandler.isLoggedIn()) {
                Alerts.showError("Login failed");
                return;
            }
            changeScene(event, "/view/MemberInfoScene.fxml");
        }
    }
}
