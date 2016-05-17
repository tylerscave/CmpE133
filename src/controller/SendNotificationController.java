package controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.NotificationSender;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the SendNotificationScene
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones, David Lerner
*/
public class SendNotificationController extends Controller{

    @FXML
    private TextField recipiantEmailField;
    @FXML
    private TextField notificationField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        recipiantEmailField.setText(Controller.getInfo());
    }
	
    @FXML
    private void handleCancelButton(ActionEvent event) {
    	changeScenePop(event);
    }
    
    @FXML
    private void handleSubmitButton(ActionEvent event) {
    	sendMessage(event);
    }
    
    @FXML
    private void onEnter(ActionEvent event) {
    	sendMessage(event);
    }
    
    private void sendMessage(ActionEvent event) {
        String email = recipiantEmailField.getText();
        String text = notificationField.getText();
        System.out.println(email+", "+text);
        if (recipiantEmailField.getText().equals("")) {
            recipiantEmailField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            Alerts.showError("You must enter the email address of the one you wish to end the message to");
            return;
        }
        if (notificationField.getText().equals("")) {
            notificationField.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            Alerts.showError("The message cannot be blank");
            return;
        }
        NotificationSender ns = new NotificationSender(member);
        ns.send(email, text);
        changeScenePop(event);
    }
}
