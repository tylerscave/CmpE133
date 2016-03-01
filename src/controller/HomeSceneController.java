package controller;


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
import javafx.stage.Stage;
import model.Context;
import model.LoginHandler;

/**
 *
 * @author David
 */
public class HomeSceneController implements Initializable {
    
    private Context context;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        context = Context.getInstance();
    }    
    
    @FXML
    protected void handleUpdateInfo(ActionEvent event) {
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
    
    @FXML
    protected void handleUpdateSchedule(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MemberScheduleScene.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    } 
    
    @FXML
    protected void handleViewSchedule(ActionEvent event) {
        try {
            //currently links to info scene, must change to appropriate scene when it's implemented
            Parent root = FXMLLoader.load(getClass().getResource("/view/MemberInfoScene.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }     
    } 
    
    @FXML
    protected void handleLogoutButton(ActionEvent event) {
        context.setLogin(new LoginHandler());
        context.getLogin().handleLogout();
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
}
