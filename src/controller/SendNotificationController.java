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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Context;
import model.member.Member;

public class SendNotificationController implements Initializable {
	
    private Context context;
    private Member member;
    @FXML
    private TextField recipiantEmailField;
    @FXML
    private TextField notificationField;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
            context = Context.getInstance();
            member = context.getMember();
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
