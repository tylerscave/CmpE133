package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import model.Context;
import model.member.Member;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the payment information form.  
 * Solves CmpE133 Assignment 2
 * @author Tyler Jones,
*/

public class PaymentInfoController implements Initializable {
	
    private Context context;
    private Member member;
    
	@Override
	public void initialize(URL url, ResourceBundle rb) {
        context = Context.getInstance();
        member = context.getMember();
	}

}
