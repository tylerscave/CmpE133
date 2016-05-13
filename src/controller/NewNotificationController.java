package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import model.Notification;
import model.StringFormat;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the NewNotificationScene
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones, David Lerner
*/
public class NewNotificationController extends Controller {

    @FXML
    Text text;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        text.setText(getText());
    }    
    
    @FXML
    private void handleReturnButton(ActionEvent event) {
        changeScenePop(event);
    }

    private String getText() {       
        List<Notification> notifications = member.readNewNotifications();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < notifications.size(); i++) {
            sb.append("Notification ").append(Integer.toString(i+1)).append(": Created ").append(StringFormat.getTimeFromCalendar(notifications.get(i).getTime())).append(" ").append(StringFormat.getDateFromCalendar(notifications.get(i).getTime())).append(System.lineSeparator());
            sb.append(notifications.get(i).getMessage()).append(System.lineSeparator());
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

}
