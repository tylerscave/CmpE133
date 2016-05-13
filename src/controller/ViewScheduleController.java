package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import model.schedule.ScheduleViewer;

/**
 * FXML Controller class
 *
 * @author David
 */
public class ViewScheduleController extends Controller{
    
    @FXML
    Text text;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        ScheduleViewer sv = new ScheduleViewer();
        text.setText(sv.getScheduleText(member));
    }    
    
    @FXML
    private void handleReturnButton(ActionEvent event) {
    	changeScenePop(event);
    }

}
