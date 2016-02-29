package controller;

import model.LoginHandler;
import java.io.IOException;
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
    private TextField usernameField;
    
    private Context context;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        context = Context.getInstance();
    }    
    
    @FXML
    protected void handleSubmitButton(ActionEvent event) {
        loginMessage.setText("Sign in successful");
        System.out.println("Sign in button pressed");
        login(event);
    }   

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
        context.getLogin().handleLogin(usernameField.getText(), passwordField.getText());
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
