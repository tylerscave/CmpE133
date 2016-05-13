package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the NotificationMenuScene
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones, David Lerner
*/
public class NotificationMenuController extends Controller{
    
    @FXML
    private Button newNotifications;
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        newNotifications.setText("New Notifications ("+member.getNumberOfNewNotifications()+")");
    }     
    
    @FXML
    private void handleNewNotifications(ActionEvent event) {
        changeScenePush(event, "/view/NewNotificationScene.fxml");
    } 
    
    @FXML
    private void handleOldNotifications(ActionEvent event) {
        changeScenePush(event, "/view/OldNotificationScene.fxml");
    }
    
    @FXML
    private void handleSendNotification(ActionEvent event) {
        Controller.setInfo("");
        changeScenePush(event, "/view/SendNotificationScene.fxml");
    }
    
    @FXML
    private void handleCancelButton(ActionEvent event) {
        changeScenePop(event);
    }
	
}
