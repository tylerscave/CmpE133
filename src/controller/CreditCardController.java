package controller;
/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the CreditCardSene.  
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones,
*/
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Context;
import model.member.Member;
import model.payment.CreditCardInfo.CardType;

public class CreditCardController implements Initializable {

    private Context context;
    private Member member;
    private CardType cardType;
    private ObservableList<Integer> monthList = FXCollections.observableArrayList();
    private ObservableList<Integer> yearList = FXCollections.observableArrayList();
    
    @FXML
    private TextField NameField;
    @FXML
    private TextField cardNumberField;
    @FXML
    private TextField cardSecurityCodeField;
    @FXML
    private ComboBox<CardType> cardTypeCombo;
    @FXML
    private ComboBox<Integer> expMonthCombo;
    @FXML
    private ComboBox<Integer> expYearCombo;
    
	@Override
	public void initialize(URL url, ResourceBundle rb) {
        context = Context.getInstance();
        member = context.getMember();
        
        ArrayList<Integer> months = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        monthList.addAll(months);
        expMonthCombo.setItems(monthList);
        ArrayList<Integer> years = new ArrayList<>(Arrays.asList(2016, 2017, 2018, 2019, 2020, 2021, 2022));
        yearList.addAll(years);
        expYearCombo.setItems(yearList);
        
      //setup cardType ComboBox
        cardTypeCombo.getItems().setAll(CardType.values());
	}
	
	@FXML
	private void handleCardTypeCombo(ActionEvent event) {
		//TODO
	}
	
	@FXML
	private void handleExpMonthCombo(ActionEvent event) {
		//TODO
	}
	
	@FXML
	private void handleExpYearCombo(ActionEvent event) {
		//TODO
	}
	
	@FXML
    private void handleCancelButton(ActionEvent event) {
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
	
    @FXML
    private void handleSubmitButton(ActionEvent event) {
    	//TODO
    	
    	handleCancelButton(event);
    }
}
