package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Context;
import model.member.Member;

/**
 *
 * Superclass for all scene controllers
 * @author David Lerner
 */
public class Controller implements Initializable{

    protected Context context;
    protected Member member;

    private static String currentScenePath = "/view/LoginScene.fxml";    
    private static Deque<String> sceneStack = new ArrayDeque<>();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        context = Context.getInstance();
        member = context.getMember();
    }

    public static void setCurrentScenePath(String currentScenePath) {
        Controller.currentScenePath = currentScenePath;
    }
    
    /**
     * Changes the scene to the one specified by sceneFilePath.
     * @param event
     * @param sceneFilePath
     */
    public void changeScene(ActionEvent event, String sceneFilePath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(sceneFilePath));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
            currentScenePath = sceneFilePath;
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Pushes the current scene to the stack and changes the scene to 
     * the one specified by sceneFilePath.
     * @param event
     * @param sceneFilePath
     */    
    public void changeScenePush(ActionEvent event, String sceneFilePath) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(sceneFilePath));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
            sceneStack.push(currentScenePath);
            currentScenePath = sceneFilePath;
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Pops a scene from the stack and changes to it. 
     * @param event
     */      
    public void changeScenePop(ActionEvent event) {
        if (sceneStack.isEmpty())
            return;
        String sceneFilePath = sceneStack.pop();
        try {
            Parent root = FXMLLoader.load(getClass().getResource(sceneFilePath));
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
            currentScenePath = sceneFilePath;
        } catch (IOException ex) {
            Logger.getLogger(LoginSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
