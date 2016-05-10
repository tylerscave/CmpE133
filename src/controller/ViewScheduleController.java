package controller;

import java.io.IOException;
import java.net.URL;
import java.util.GregorianCalendar;
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
import model.schedule.Drive;
import model.schedule.Location;
import model.member.Member;
import model.schedule.Ride;
import model.schedule.RideRequest;
import model.schedule.Route;
import model.schedule.ScheduleViewer;

/**
 * FXML Controller class
 *
 * @author David
 */
public class ViewScheduleController implements Initializable {

    private Context context;
    private Member member;
    
    @FXML
    Text text;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        context = Context.getInstance();
        member = context.getMember();
        ScheduleViewer sv = new ScheduleViewer();
        text.setText(sv.getScheduleText(member));
    }    
    
    @FXML
    protected void handleReturnButton(ActionEvent event) {
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
