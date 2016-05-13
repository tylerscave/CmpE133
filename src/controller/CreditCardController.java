package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.payment.CreditCardInfo;
import model.payment.CreditCardInfo.CardType;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the CreditCardScene.  
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones, David Lerner
*/
public class CreditCardController extends Controller{

    private CardType cardType;
    private int month;
    private int year;
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
    
    private int currentYear;
    private final int yearOffset = 2000;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        ArrayList<Integer> months = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        monthList.addAll(months);
        expMonthCombo.setItems(monthList);
        currentYear = (new GregorianCalendar()).get(GregorianCalendar.YEAR);
        ArrayList<Integer> years = new ArrayList<>();
        for (int i = 0; i < 7; i++)
            years.add(i+currentYear);
        yearList.addAll(years);
        expYearCombo.setItems(yearList);
        //setup cardType ComboBox
        cardTypeCombo.getItems().setAll(CardType.values());
        
        CreditCardInfo cardInfo = member.getCreditCardInfo();
        NameField.setText(cardInfo.getNameOnCard());
        cardNumberField.setText(cardInfo.getCardNumber());
        cardSecurityCodeField.setText(cardInfo.getCardSecurityCode());
        cardType = cardInfo.getCardType();
        cardTypeCombo.setValue(cardType);
        month = cardInfo.getExpMonth(); 
        expMonthCombo.setValue(month);
        year = yearOffset+cardInfo.getExpYear();
        expYearCombo.setValue(year);
    }
	
    @FXML
    private void handleCardTypeCombo(ActionEvent event) {
        cardType = cardTypeCombo.getSelectionModel().getSelectedItem();
    }
	
    @FXML
    private void handleExpMonthCombo(ActionEvent event) {
        month = expMonthCombo.getSelectionModel().getSelectedItem();
    }
	
    @FXML
    private void handleExpYearCombo(ActionEvent event) {
        year = expYearCombo.getSelectionModel().getSelectedItem();
    }
	
    @FXML
    private void handleCancelButton(ActionEvent event) {
    	changeScenePop(event);
    }
	
    @FXML
    private void handleSubmitButton(ActionEvent event) {
    	member.setCreditCardInfo(new CreditCardInfo(NameField.getText(), cardType, cardNumberField.getText(), cardSecurityCodeField.getText(), month, year-yearOffset));
    	member.setChanged();
        member.notifyObservers();
        
    	changeScenePop(event);
    }
}
