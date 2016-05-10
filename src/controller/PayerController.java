package controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
import javax.swing.JOptionPane;
import model.Context;
import model.member.Driver;
import model.member.Member;
import model.payment.BankAccount;
import model.payment.CreditCard;
import model.payment.Reward;
import model.schedule.Ride;
import model.schedule.ScheduleViewer;

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
    private ObservableList<Ride> ridesToPay = FXCollections.observableArrayList();
    private Ride payFor;
    
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
        
        ScheduleViewer sv = new ScheduleViewer();
        List<Ride> rides = sv.getRidesToPay(member);
        for (Ride r : rides) {
            Member driver = context.getDataHandler().getMember(sv.getDriveById(r.getDriveId()).getMemberId());
            if (!driver.getDrivingType().isDriver())
                continue;
            Driver d = (Driver) driver.getDrivingType();
            Reward reward = new CreditCard(null, d.getPayBy());
            double amount = (Double)reward.findReward(driver, r);
            r.setDescription(String.format("Amount to pay: $%.2f", amount));
        }
        ridesToPay.addAll(rides);
        rideCombo.setItems(ridesToPay);
        payFor = null;
    } 
    
    @FXML
    private void handleRideCombo(ActionEvent event) {
    	payFor = rideCombo.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handlePaymentRadios(ActionEvent event) {
    	RadioButton radio = (RadioButton) event.getSource();
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
    	if (payFor == null)
            return;
        
    	if (creditRadio.isSelected()) {
            ScheduleViewer sv = new ScheduleViewer();
            Member driver = context.getDataHandler().getMember(sv.getDriveById(payFor.getDriveId()).getMemberId());
            if (!driver.getDrivingType().isDriver())
                return;
            Driver d = (Driver) driver.getDrivingType();
            Reward reward = new CreditCard(member.getCreditCardInfo(), d.getPayBy());
            double amount = (Double)reward.findReward(driver, payFor);
            if (!reward.payReward(driver, payFor, amount))
                JOptionPane.showMessageDialog(null, "Payment failed", "Error", JOptionPane.ERROR_MESSAGE);
            handleCancelButton(event);
            
    	} else if (bankRadio.isSelected()){
            ScheduleViewer sv = new ScheduleViewer();
            Member driver = context.getDataHandler().getMember(sv.getDriveById(payFor.getDriveId()).getMemberId());
            if (!driver.getDrivingType().isDriver())
                return;
            Driver d = (Driver) driver.getDrivingType();
            Reward reward = new BankAccount(member.getBankAccountInfo(), d.getPayBy());
            double amount = (Double)reward.findReward(driver, payFor);
            if (!reward.payReward(driver, payFor, amount))
                JOptionPane.showMessageDialog(null, "Payment failed", "Error", JOptionPane.ERROR_MESSAGE);
            handleCancelButton(event);
            
    	} else if (notificationRadio.isSelected()) {
    		handleNotification(event);
    	}
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


