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
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import model.Context;
import model.member.Member;
import model.schedule.Ride;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the PayerScene
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones,
*/
public class PayerController implements Initializable {

    private Context context;
    private Member member;
    private boolean notifyMember = false;
    @FXML
    private ComboBox<Ride> rideCombo;
    @FXML
    private RadioButton creditRadio;
    @FXML
    private RadioButton bankRadio;
    @FXML
    private RadioButton notificationRadio;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        context = Context.getInstance();
        member = context.getMember();        
    } 
    
    @FXML
    private void handleRideCombo(ActionEvent event) {
    	
    }
    
	@FXML
	private void handlePaymentRadios(ActionEvent event) {
    	RadioButton radio = (RadioButton) event.getSource();
    	if (radio == creditRadio) {
    		
    	} else if (radio == bankRadio){
    		
    	} else if (radio == notificationRadio) {
    		notifyMember = true;
    	}
	}
    
    @FXML
    private void handleNewPayment(ActionEvent event) {
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
    private void handleCancelButton(ActionEvent event) {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/ProcessPaymentMenuScene.fxml"));
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
    	
    	if (notifyMember) {
    		handleNotification(event);
    	}
    	handleCancelButton(event);
    }
    
    private void handleNotification(ActionEvent event) {
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/SendNotificationScene.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


