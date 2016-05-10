package controller;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Context;
import model.member.Member;
import model.payment.BankAccountInfo;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the BankAccountScene.  
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones,
*/
public class BankAccountController implements Initializable {

    private Context context;
    private Member member;
    
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
        context = Context.getInstance();
        member = context.getMember();
        BankAccountInfo bankAccountInfo = member.getBankAccountInfo();
        NameField.setText(bankAccountInfo.getNameOnAccount());
        bankField.setText(bankAccountInfo.getBank());
        accountField.setText(bankAccountInfo.getAccountNumber());
        routingField.setText(bankAccountInfo.getRoutingNumber());
	}
	
	@FXML
    private void handleCancelButton(ActionEvent event) {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/PaymentMenuScene.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
    @FXML
    private void handleSubmitButton(ActionEvent event) {
    	member.setBankAccountInfo(new BankAccountInfo(NameField.getText(), bankField.getText(), accountField.getText(), routingField.getText()));
    	
    	handleCancelButton(event);
    }

}
