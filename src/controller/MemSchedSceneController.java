package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
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

}
