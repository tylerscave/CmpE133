package controller;

import java.io.IOException;
import java.net.URL;
import java.util.GregorianCalendar;
import java.util.List;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Context;
import model.Drive;
import model.Location;
import model.Member;
import model.Ride;
import model.RideRequest;
import model.Route;

/**
 * FXML Controller class
 *
 * @author David
 */
public class ViewScheduleSceneController implements Initializable {

    private Context context;
    private Member member;
    
    @FXML
    Text text;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        context = Context.getInstance();
        member = context.getMember();
        text.setText(getText());
    }    
    
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

    private String getText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Drives:").append(System.lineSeparator());
        if (member.getDrives().isEmpty())
            sb.append("None").append(System.lineSeparator());
        for (int i = 0; i < member.getDrives().size(); i++) {
            Drive d = member.getDrives().get(i);
            sb.append("Drive details:").append(System.lineSeparator());
            Route route = d.getRoute();
            List<Location> stops = route.getStops();
            sb.append("\t").append(stops.get(0)).append(" at ").append(getTimeFromCalendar(route.getStartTime())).append(" to ").append(stops.get(stops.size()-1)).append(" at ").append(getTimeFromCalendar(route.getEndTime())).append(" on ").append(getDateFromCalendar(route.getEndTime())).append(System.lineSeparator());
            if (stops.size() > 2) {
                sb.append("\tStops at: ");
                for (int j = 1; j < stops.size()-1; j++) {
                    sb.append(stops.get(j)).append(", ");
                }
                sb.append(System.lineSeparator());
            }
            sb.append("\t").append(d.getNumSeats()).append(" seats available").append(System.lineSeparator());
            sb.append("\tPassengers:").append(System.lineSeparator());
            if (d.numberOfRides() == 0)
                sb.append("\t\tNone").append(System.lineSeparator());
            for (int j = 0; j < d.numberOfRides(); j++) {
                Ride ride = d.getRide(j);
                Route rideRoute = ride.getRoute();
                sb.append("\t\t").append(ride.getMember().getFirstName()).append(ride.getMember().getLastName()).append(": ");
                sb.append(rideRoute.getStops().get(0)).append(" at ").append(getTimeFromCalendar(rideRoute.getStartTime())).append(" to ");
                sb.append(rideRoute.getStops().get(stops.size()-1)).append(" at ").append(getTimeFromCalendar(rideRoute.getEndTime())).append(System.lineSeparator());
            }
        }
        sb.append(System.lineSeparator());
        
        sb.append("Rides:").append(System.lineSeparator());
        if (member.getRides().isEmpty())
            sb.append("None").append(System.lineSeparator());
        for (Ride r : member.getRides()) {
            sb.append("Ride details:").append(System.lineSeparator());
            Route route = r.getRoute();
            List<Location> stops = route.getStops();
            sb.append("\t").append(stops.get(0)).append(" at ").append(getTimeFromCalendar(route.getStartTime())).append(" to ");
            sb.append(stops.get(stops.size()-1)).append(" at ").append(getTimeFromCalendar(route.getEndTime())).append(" on ").append(getDateFromCalendar(route.getEndTime())).append(System.lineSeparator());
            Drive drive = r.getDrive();
            sb.append("\tPassenger in ").append(drive.getMember().getFirstName()).append(" ").append(drive.getMember().getLastName()).append("'s vehicle").append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        
        sb.append("Ride Requests:").append(System.lineSeparator());
        if (member.getRideRequests().isEmpty())
            sb.append("None").append(System.lineSeparator());
        for (RideRequest rr : member.getRideRequests()) {
            sb.append("Ride request details:").append(System.lineSeparator());
            if (rr.getStartType() == RideRequest.TimeType.AnyTime)
                sb.append("\tDepart from ").append(rr.getStartLocation()).append(" at AnyTime").append(System.lineSeparator());
            else
                sb.append("\tDepart from ").append(rr.getStartLocation()).append(" ").append(rr.getStartType().name()).append(" ").append(getTimeFromCalendar(rr.getStartTime())).append(" ").append(getDateFromCalendar(rr.getStartTime())).append(System.lineSeparator());
            if (rr.getEndType() == RideRequest.TimeType.AnyTime)
                sb.append("\tArrive at ").append(rr.getEndLocation()).append(" at AnyTime").append(System.lineSeparator());
            else
                sb.append("\tArrive at ").append(rr.getEndLocation()).append(" ").append(rr.getEndType().name()).append(" ").append(getTimeFromCalendar(rr.getEndTime())).append(" ").append(getDateFromCalendar(rr.getEndTime())).append(System.lineSeparator());
        }
        sb.append(System.lineSeparator());
        return sb.toString();
    }
    
    private String getDateFromCalendar(GregorianCalendar gc) {
        return gc.get(GregorianCalendar.MONTH)+"/"+gc.get(GregorianCalendar.DATE)+"/"+gc.get(GregorianCalendar.YEAR);
    }
    
    private String getTimeFromCalendar(GregorianCalendar gc) {
        String ampm[] = new String[2];
        ampm[0] = " AM";
        ampm[1] = " PM";
        String minute = Integer.toString(gc.get(GregorianCalendar.MINUTE));
        if (minute.length() == 1)
            minute = "0"+minute;
        return gc.get(GregorianCalendar.HOUR)+":"+minute+ampm[gc.get(GregorianCalendar.AM_PM)];
    }
}
