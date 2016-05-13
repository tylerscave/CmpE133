package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.payment.BankAccountInfo;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the BankAccountScene.  
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones, David Lerner
*/
public class BankAccountController extends Controller{

    @FXML
    private TextField NameField;
    @FXML
    private TextField bankField;
    @FXML
    private TextField accountField;
    @FXML
    private TextField routingField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        //load fields with current data
        BankAccountInfo bankAccountInfo = member.getBankAccountInfo();
        NameField.setText(bankAccountInfo.getNameOnAccount());
        bankField.setText(bankAccountInfo.getBank());
        accountField.setText(bankAccountInfo.getAccountNumber());
        routingField.setText(bankAccountInfo.getRoutingNumber());
    }
	
    @FXML
    private void handleCancelButton(ActionEvent event) {
        changeScenePop(event);
    }
	
    @FXML
    private void handleSubmitButton(ActionEvent event) {
    	member.setBankAccountInfo(new BankAccountInfo(NameField.getText(), bankField.getText(), accountField.getText(), routingField.getText()));
    	member.setChanged();
        member.notifyObservers();
        
    	changeScenePop(event);
    }

}
