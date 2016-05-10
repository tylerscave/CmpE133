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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Context;
import model.member.LoginInformation;
import model.member.Member;
import model.member.MemberBuilder;

/**
 * FXML Controller class
 *
 * @author David
 */
public class ChangePasswordSceneController implements Initializable {

    private Context context;
    private Member member;
    
    @FXML
    private PasswordField old;
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
            Parent root = FXMLLoader.load(getClass().getResource("/view/MemberInfoScene.fxml"));
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
		//check to make sure passwords match and old passord is correct
                if (!old.getText().equals(member.getLoginInfo().getPassword())) {
                    old.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
                    JOptionPane.showMessageDialog(null, "Incorrect passord!", "Error",
											JOptionPane.ERROR_MESSAGE);
                }
		else if(!(password.getText().equals(confirmPassword.getText()))) {
			confirmPassword.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
			JOptionPane.showMessageDialog(null, "Passwords must match!", "Error",
											JOptionPane.ERROR_MESSAGE);
		} else {
                member.setLoginInfo(new LoginInformation(member.getLoginInfo().getEmail(), password.getText()));
                
                handleCancelButton(event);
	}
        }
}
