package controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the ProcessPaymentMenuScene
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones, David Lerner
*/
public class ProcessPaymentMenuController extends Controller{

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }   
    
    @FXML
    private void handlePayer(ActionEvent event) {
        changeScenePush(event, "/view/PayerScene.fxml");
    } 
    
    @FXML
    private void handlePayee(ActionEvent event) {
        changeScenePush(event, "/view/PayeeScene.fxml");
    }
    
    @FXML
    private void handleCancelButton(ActionEvent event) {
        changeScenePop(event);
    }   
}
