package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * FXML controller for HomeScene
 * @author David Lerner
 */
public class HomeSceneController extends Controller{
    
    @FXML
    private Text welcome;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        welcome.setText("Welcome, "+context.getMember().getFirstName());
    }    
    
    @FXML
    private void handleUpdateMember(ActionEvent event) {
        changeScene(event, "/view/MemberInfoScene.fxml");
    } 

    @FXML
    private void handleUpdateSchedule(ActionEvent event) {
        changeScenePush(event, "/view/MemberScheduleScene.fxml");
    } 
    
    @FXML
    private void handleRideRequest(ActionEvent event) {
        changeScenePush(event, "/view/RideRequestScene.fxml");
    } 
    
    @FXML
    private void handleDriveRequest(ActionEvent event) {
        changeScenePush(event, "/view/DriveRequestScene.fxml");
    } 
    
    @FXML
    private void handleViewSchedule(ActionEvent event) {
        changeScenePush(event, "/view/ViewScheduleScene.fxml");
    } 
    
    @FXML
    private void handleNotificationMenu(ActionEvent event) {
        changeScenePush(event, "/view/NotificationMenuScene.fxml");
    } 
    
    @FXML
    private void handleProcessPaymentMenu(ActionEvent event) {
        changeScenePush(event, "/view/ProcessPaymentMenuScene.fxml");
    }
    
    @FXML
    private void handleLogoutButton(ActionEvent event) {
        context.getLogin().handleLogout();
        changeScenePop(event);
    } 
}
