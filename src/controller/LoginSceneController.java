package controller;

import model.LoginHandler;
import model.member.LoginInformation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class for LoginScene
 *
 * @author David Lerner
 */
public class LoginSceneController extends Controller{

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    
    private LoginInformation loginInfo;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }    
    
    @FXML
    private void handleSubmitButton(ActionEvent event) {    	
        login(event);
    }

    @FXML
    private void onEnter(ActionEvent event) {
        login(event);
    }
    
    @FXML
    private void handleCreateAccountButton(ActionEvent event) {
        changeScenePush(event, "/view/CreateAccountScene.fxml");        
    }
    
    @FXML
    private void handleRetrieveAccount(ActionEvent event) {
        changeScenePush(event, "/view/RetrieveAccountScene.fxml");
    }
    
    private void login(ActionEvent event) {
        loginInfo = new LoginInformation(emailField.getText(), passwordField.getText());
        LoginHandler loginHandler = context.getLogin();
        if (loginHandler.isLoggedIn())
            loginHandler.handleLogout();
        String fail = loginHandler.handleLogin(loginInfo);
        if (!loginHandler.isLoggedIn()) {
            emailField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            passwordField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            Alerts.showError(fail);
            return;
        }
        changeScenePush(event, "/view/HomeScene.fxml");
    }
}
