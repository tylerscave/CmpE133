package controller;
import java.io.IOException;
import java.net.URL;
import java.util.List;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Context;
import model.DataHandler;
import model.Notification;
import model.StringFormat;
import model.member.Member;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the NewNotificationScene
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones,
*/
public class NewNotificationController implements Initializable {

    private Context context;
    private Member member;
    private DataHandler data;
    @FXML
    Text text;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        context = Context.getInstance();
        member = context.getMember();
        data = context.getDataHandler();
        text.setText(getText());
    }    
    
    @FXML
    protected void handleReturnButton(ActionEvent event) {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/NotificationMenuScene.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }     
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
