package controller;
import model.LoginHandler;
import model.member.LoginInformation;
import model.member.Member;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Context;

/**
 * FXML Controller class
 *
 * @author David
 */
public class LoginSceneController implements Initializable {

    @FXML
    private Text loginMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    
    private Context context;
    private LoginInformation loginInfo;
    private ArrayList<Member> member;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        context = Context.getInstance();
    }    
    
    @FXML
    protected void handleSubmitButton(ActionEvent event) {
    	loginInfo = new LoginInformation(emailField.getText(), passwordField.getText());
    	
    	//
    	//getting members from database to check the one who tries to login is registered.
    	//member.add();
    	//for(int i = 0; i < member.size(); i++){
    	//	if(loginInfo.getEmail() == member.get(i).getLoginInfo().getEmail()){
    	//		if(loginInfo.getPassword() == member.get(i).getLoginInfo().getPassword()){
    	//			context.setMember(member.get(i));
    	//			login(event);
    	//		}
    	//		else {
    	//			//wrong password
    	//		}
    	//	}
    	//	else{
    	//		//Email not registered
    	//	}
    		
    	//}
    	
    	
        //loginMessage.setText("Sign in successful");
    	loginMessage.setText("Incorrect login. Do you have an account yet?");
        System.out.println("Sign in button pressed");
        login(event);
    }

    @FXML
    protected void onEnter(ActionEvent event) {
    	
    	//loginInfo = new LoginInformation(emailField.getText(), passwordField.getText());
    	//getting members from database to check the one who tries to login is registered.
    	//member.add();
    	//for(int i = 0; i < member.size(); i++){
    	//	if(loginInfo.getEmail() == member.get(i).getLoginInfo().getEmail()){
    	//		if(loginInfo.getPassword() == member.get(i).getLoginInfo().getPassword()){
    	//			context.setMember(member.get(i));
    	//			login(event);
    	//		}
    	//		else {
    	//			//wrong password
    	//		}
    	//	}
    	//	else{
    	//		//Email not registered
    	//	}
    		
    	//}
    	
    	System.out.println("Enter key pressed");
        login(event);
    }
    
    @FXML
    protected void handleCreateAccountButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/CreateAccountScene.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @FXML
    protected void handleRetrieveAccount(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/RetrieveAccountScene.fxml"));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private void login(ActionEvent event) {
        context.setLogin(new LoginHandler());
        if (!context.getLogin().handleLogin(loginInfo).equals("")) {
            return;
        } 
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
}
