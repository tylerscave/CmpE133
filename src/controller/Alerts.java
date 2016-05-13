package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;

/**
 *
 * @author David Lerner
 */
public class Alerts {

    /**
     * Creates an information alert box and waits for the user to press OK.
     * @param title title of the alert window
     * @param header header of alert text
     * @param text text of the alert
     */
    public static void showInfo(String title, String header, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, text, ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
    
    /**
     * Creates an error alert box and waits for the user to press OK.
     * @param text text of the alert
     */
    public static void showError(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
