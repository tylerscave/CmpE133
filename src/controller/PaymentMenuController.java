package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the PaymentMenuScene
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones, David Lerner
*/
public class PaymentMenuController extends Controller{

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
    }     
    
    @FXML
    private void handleCreditCard(ActionEvent event) {
        changeScenePush(event, "/view/CreditCardScene.fxml");
    } 
    
    @FXML
    private void handleBankAccount(ActionEvent event) {
        changeScenePush(event, "/view/BankAccountScene.fxml");
    }
    
    @FXML
    private void handleCancelButton(ActionEvent event) {
        changeScenePop(event);
    }
}
