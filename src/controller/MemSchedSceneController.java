package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import model.Context;
import model.MemberHandler;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the member information form 
 * Solves CmpE133 Assignment 2
 * @author Tyler Jones,
*/

public class MemSchedSceneController implements Initializable{
	
    private Context context;
    private MemberHandler member;
    
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
