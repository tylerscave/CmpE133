package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Context;
import model.Member;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the CreateAccountScene
 * Solves CmpE133 Assignment 2
 * @author Tyler Jones,
*/
public class CreateAccountSceneController implements Initializable {

    private Context context;
    private Member member;
    
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
        context = Context.getInstance();
        member = context.getMember();		
	}
	
	@FXML
	public void handleCancelButton(ActionEvent event) throws Exception {
		
	}

	@FXML
	public void handleSubmitButton(ActionEvent event) throws Exception {
		
	}
}
