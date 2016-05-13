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
import model.payment.CreditCard;
import model.payment.Reward;
import model.schedule.Ride;
import model.schedule.ScheduleViewer;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the PayeeScene
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones, David Lerner
*/
public class PayeeController extends Controller{

    private boolean notifyMember;
    @FXML
    private ComboBox<Ride> rideCombo;
    @FXML
    private RadioButton waivePaymentRadio;
    @FXML
    private RadioButton notificationRadio;
    
    private ObservableList<Ride> ridesToPay = FXCollections.observableArrayList();
    private Ride payFor;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        ridesToPay.addAll((new ScheduleViewer()).getRidesToBePaid(member));
        rideCombo.setItems(ridesToPay);
        payFor = null;
    } 
    
    @FXML
    private void handleRideCombo(ActionEvent event) {
        payFor = rideCombo.getSelectionModel().getSelectedItem();
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
    	if (waivePaymentRadio.isSelected()) {
            if (!member.getDrivingType().isDriver()) {
                Alerts.showError("You are not a driver");
                return;
            }
            Driver d = (Driver) member.getDrivingType();
            Reward reward = new CreditCard(null, d.getPayBy());
            reward.waiveReward(payFor);
            Alerts.showInfo("Payment waived", null, "You have waived any payment for the folloing ride: \n"+payFor.toString());
            changeScenePop(event);
        }
        else if (notificationRadio.isSelected()) {
            changeScenePush(event, "/view/SendNotificationScene.fxml");
    	}
    }

}
