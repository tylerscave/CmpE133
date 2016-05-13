package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
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
 * @author Tyler Jones, David Lerner
*/
public class PayerController extends Controller{

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
        super.initialize(url, rb);
        
        ridesToPay.addAll((new ScheduleViewer()).getRidesToPay(member));
        rideCombo.setItems(ridesToPay);
        payFor = null;
    } 
    
    @FXML
    private void handleRideCombo(ActionEvent event) {
    	payFor = rideCombo.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleNewPayment(ActionEvent event) {
        changeScenePush(event, "/view/PaymentMenuScene.fxml");
    }
    
    @FXML
    private void handleCancelButton(ActionEvent event) {
        changeScenePop(event);
    }
    
    @FXML
    private void handleSubmitButton(ActionEvent event) {
    	if (payFor == null) {
            Alerts.showError("No ride selected");
            return;
        }
    	if (creditRadio.isSelected()) {
            ScheduleViewer sv = new ScheduleViewer();
            Member driver = context.getDataHandler().getMember(sv.getDriveById(payFor.getDriveId()).getMemberId());
            if (!driver.getDrivingType().isDriver()) {
                Alerts.showError("Member is not a driver");
                return;
            }
            Driver d = (Driver) driver.getDrivingType();
            Reward reward = new CreditCard(member.getCreditCardInfo(), d.getPayBy());
            double amount = (Double)reward.findReward(driver, payFor);
            if (reward.payReward(driver, payFor, amount))
                Alerts.showInfo("Payment accepted", null, "You have paid for the folloing ride: \n"+payFor.toString());
            else {
                Alerts.showError("Payment failed");
                return;
            }
            changeScenePop(event);
            
    	} else if (bankRadio.isSelected()){
            ScheduleViewer sv = new ScheduleViewer();
            Member driver = context.getDataHandler().getMember(sv.getDriveById(payFor.getDriveId()).getMemberId());
            if (!driver.getDrivingType().isDriver()) {
                Alerts.showError("Member is not a driver");
                return;
            }
            Driver d = (Driver) driver.getDrivingType();
            Reward reward = new BankAccount(member.getBankAccountInfo(), d.getPayBy());
            double amount = (Double)reward.findReward(driver, payFor);
            if (reward.payReward(driver, payFor, amount))
                Alerts.showInfo("Payment accepted", null, "You have paid for the folloing ride: \n"+payFor.toString());
            else {
                Alerts.showError("Payment failed");
                return;
            }
            changeScenePop(event);
            
    	} else if (notificationRadio.isSelected()) {
            changeScenePush(event, "/view/SendNotificationScene.fxml");
    	}
    }
}


