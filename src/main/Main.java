package main;
//this is ronny pushing
//shubaan wuz here
/**
 *
 * @author David
 */
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
//This is Tyler pushing on new eclipse build
public class Main extends Application
{
    public static final int PREF_WIDTH = 550;
    public static final int PREF_HEIGHT = 350;
    public static final int MIN_WIDTH = 300;
    public static final int MIN_HEIGHT = 350;
    public static final double OPACITY = 1;
    public static final String APP_TITLE = "CarPool System";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setOpacity(OPACITY);
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScene.fxml"));
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
