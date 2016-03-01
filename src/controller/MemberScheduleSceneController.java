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
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import model.Context;
import model.Member;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the member information form 
 * Solves CmpE133 Assignment 2
 * @author Tyler Jones,
*/

public class MemberScheduleSceneController implements Initializable{
	
    private Context context;
    private Member member;
    
	@Override
	public void initialize(URL url, ResourceBundle rb) {
            context = Context.getInstance();
            member = context.getMember();
	}
	
	@FXML
    private ComboBox location;        
    @FXML
    private ComboBox monArrive;
    @FXML
    private ComboBox monDepart;
    @FXML
    private ComboBox tuesArrive;
    @FXML
    private ComboBox tuesDepart;
    @FXML
    private ComboBox wedArrive;        
    @FXML
    private ComboBox wedDepart;
    @FXML
    private ComboBox thursArrive;
    @FXML
    private ComboBox thursDepart;    
    @FXML
    private ComboBox friArrive;
    @FXML
    private ComboBox friDepart;
    @FXML
    private RadioButton monDrive;        
    @FXML
    private RadioButton tuesDrive;        
    @FXML
    private RadioButton wedDrive; 
    @FXML
    private RadioButton thursDrive;        
    @FXML
    private RadioButton friDrive;
	
    @FXML
    protected void handleReturnButton(ActionEvent event) {
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
    
    /* After thinking about this for a little while we may be able to
     * handle all of the dropdowns in one method, or probably better would 
     * be to have two methods, one for arrival and one for departure,
     * Then do if/else or case based on id.
     */
    @FXML
    protected void handleLocationDrop(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    protected void handleMonArriveDrop(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    protected void handleMonDepartDrop(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    protected void handleTuesArriveDrop(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    protected void handleTuesDepartDrop(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    protected void handleWedArriveDrop(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    protected void handleWedDepartDrop(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    protected void handleThursArriveDrop(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    protected void handleThursDepartDrop(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    protected void handleFriArriveDrop(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    protected void handleFriDepartDrop(ActionEvent event) {
    	//TODO
    }
    
    @FXML
    protected void handleRadioButtons(ActionEvent event) {
    	//TODO
    }

}
