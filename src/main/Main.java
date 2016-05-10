package main;
import java.io.FileNotFoundException;

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
import model.Database;
//This is Tyler pushing on new eclipse build
public class Main extends Application
{
    public static final int PREF_WIDTH = 600;
    public static final int PREF_HEIGHT = 400;
    public static final int MIN_WIDTH = 300;
    public static final int MIN_HEIGHT = 200;
    public static final double OPACITY = 1;
    public static final String APP_TITLE = "CarPool System";
    //Database database;
    
    public static void main(String[] args) {
    	/*Database database = new Database();
    	try {
			database.load();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        launch(args);	//call start(javafx.stage.Stage) method
    }

    //start(Stage stage) main entry point for application
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
