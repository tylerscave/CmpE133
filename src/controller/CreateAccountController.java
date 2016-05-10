package controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Context;
import model.LoginHandler;
import model.member.LoginInformation;
import model.member.Member;
import model.member.MemberBuilder;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the CreateAccountScene
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones,
*/
public class CreateAccountController implements Initializable {

    private Context context;
    
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        context = Context.getInstance();		
    }
	
    @FXML
    public void handleCancelButton(ActionEvent event) throws Exception {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScene.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        //check to make sure passwords match
	if(!(password.getText().equals(confirmPassword.getText()))) {
            confirmPassword.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            JOptionPane.showMessageDialog(null, "Passwords must match!", "Error", JOptionPane.ERROR_MESSAGE);
	} else {
            MemberBuilder mb = new MemberBuilder();
            LoginInformation newInfo = new LoginInformation(email.getText(), password.getText()); 
            mb.setLoginInfo(newInfo);
            if (mb.build() == -1)
                return;
            LoginHandler loginHandler = context.getLogin();
            if (loginHandler.isLoggedIn())
                loginHandler.handleLogout();
            loginHandler.handleLogin(newInfo);
            if (!loginHandler.isLoggedIn())
                return;
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/view/MemberInfoScene.fxml"));
                Scene scene = new Scene(root);
                Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                primaryStage.setScene(scene);
                primaryStage.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
