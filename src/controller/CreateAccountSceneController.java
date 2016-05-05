package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Context;
import model.member.Member;

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
    	try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScene.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
	}

	@FXML
	public void handleSubmitButton(ActionEvent event) throws Exception {
		//check to make sure passwords match
		if(!(password.getText().equals(confirmPassword.getText()))) {
			confirmPassword.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
			JOptionPane.showMessageDialog(null, "Passwords must match!", "Error",
											JOptionPane.ERROR_MESSAGE);
		} else {
	        member.getLoginInfo().setEmail(email.getText());
	        member.getLoginInfo().setPassword(password.getText());
			try {
				Parent root = FXMLLoader.load(getClass().getResource("/view/MemberInfoScene.fxml"));
				Scene scene = new Scene(root);
				Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException ex) {
				Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}
