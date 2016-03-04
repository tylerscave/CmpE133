package controller;

import model.LoginHandler;
import model.LoginInformation;
import model.Member;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import ErrorCheck.LoginErrorChecker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Context;

/**
 * FXML Controller class
 *
 * @author David
 */
public class LoginSceneController implements Initializable {

    @FXML
    private Text loginMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    
    private Context context;
    private LoginInformation loginInfo;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        context = Context.getInstance();
    }    
    
    @FXML
    protected void handleSubmitButton(ActionEvent event) throws Exception {
        LoginErrorChecker.invalidEmail(emailField);
        LoginErrorChecker.invalidPass(passwordField);
    	loginMessage.setText("Sign in successful");
        System.out.println("Sign in button pressed");
        login(event);
<<<<<<< HEAD

        
    }   
=======
    }
>>>>>>> branch 'master' of https://github.com/tylerscave/CmpE133.git

    @FXML
    protected void onEnter(ActionEvent event) {
        System.out.println("Enter key pressed");
        login(event);
    }
    
    @FXML
    protected void handleCreateAccountButton(ActionEvent event) {
        //TODO
    }
    
    private void login(ActionEvent event) {
        context.setLogin(new LoginHandler());
        context.getLogin().handleLogin(new LoginInformation(emailField.getText(), passwordField.getText()));
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
}
