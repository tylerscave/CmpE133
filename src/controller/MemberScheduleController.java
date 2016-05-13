package controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import model.schedule.WeeklySchedule;
import model.schedule.Location;
import model.schedule.Request;
import model.schedule.Scheduler;
import model.schedule.SchedulingContext;

/**
 *COPYRIGHT (C) 2016 CmpE133_7. All Rights Reserved.
 * The controller for the Member Schedule Scene
 * Solves CmpE133 SpartanPool
 * @author Tyler Jones, David Lerner
*/
public class MemberScheduleController extends Controller{

    private Location centralLocation;
    private Location selectedLocation;
    private ObservableList<Location> locations = FXCollections.observableArrayList();   
    private ObservableList<GregorianCalendar> monTimes;
    private ObservableList<GregorianCalendar> tuesTimes;
    private ObservableList<GregorianCalendar> wedTimes;
    private ObservableList<GregorianCalendar> thursTimes;
    private ObservableList<GregorianCalendar> friTimes;

    private GregorianCalendar monTime, tuesTime,  wedTime,  thursTime, friTime; 
    private GregorianCalendar[] arriveTime, departTime;
    private boolean[] drive;
    private LocalDate start, end;
    
    @FXML
    private ComboBox<Location> location;
    @FXML
    private ComboBox<GregorianCalendar> monArrive;
    @FXML
    private ComboBox<GregorianCalendar> monDepart;
    @FXML
    private ComboBox<GregorianCalendar> tuesArrive;
    @FXML
    private ComboBox<GregorianCalendar> tuesDepart;
    @FXML
    private ComboBox<GregorianCalendar> wedArrive;        
    @FXML
    private ComboBox<GregorianCalendar> wedDepart;
    @FXML
    private ComboBox<GregorianCalendar> thursArrive;
    @FXML
    private ComboBox<GregorianCalendar> thursDepart;    
    @FXML
    private ComboBox<GregorianCalendar> friArrive;
    @FXML
    private ComboBox<GregorianCalendar> friDepart;
    @FXML
    private CheckBox monDriveCheck;        
    @FXML
    private CheckBox tuesDriveCheck;        
    @FXML
    private CheckBox wedDriveCheck; 
    @FXML
    private CheckBox thursDriveCheck;        
    @FXML
    private CheckBox friDriveCheck;
    @FXML
    private Text central;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize(url, rb);
        
        //get central location
        centralLocation = context.getCentral();
        
        //set up the location ComboBox
        for (Location l : context.getMap().getLocations()) {
            if (!l.equals(centralLocation))
                locations.add(l);
        }
        location.setItems(locations);
            
        //setup Arrival and Departure ComboBoxes
        monTimes = timesMaker(2, monTime);
        monArrive.setItems(monTimes);
        monDepart.setItems(monTimes);
        tuesTimes = timesMaker(3, tuesTime);
        tuesArrive.setItems(tuesTimes);
        tuesDepart.setItems(tuesTimes);
        wedTimes = timesMaker(4, wedTime);
        wedArrive.setItems(wedTimes);
        wedDepart.setItems(wedTimes);
        thursTimes = timesMaker(5, thursTime);
        thursArrive.setItems(thursTimes);
        thursDepart.setItems(thursTimes);
        friTimes = timesMaker(6, friTime);
        friArrive.setItems(friTimes);
        friDepart.setItems(friTimes);
        
        //setup current schedule
        WeeklySchedule ws = member.getWeeklySchedule();
        
        GregorianCalendar time = ws.getStartTime();
        if (time != null) {
            start = LocalDate.ofYearDay(time.get(GregorianCalendar.YEAR), time.get(GregorianCalendar.DAY_OF_YEAR));
            startDate.setValue(start);
        }
        time = ws.getEndTime();
        if (time != null) {
            end = LocalDate.ofYearDay(time.get(GregorianCalendar.YEAR), time.get(GregorianCalendar.DAY_OF_YEAR));
            endDate.setValue(end);
        }
        selectedLocation = ws.getPickupLocation();
        location.setValue(selectedLocation);
        
        arriveTime = new GregorianCalendar[5];
        departTime = new GregorianCalendar[5];
        drive = new boolean[5];
        
        for (int i = 0; i < 5; i++) {
            arriveTime[i] = ws.getArrive(i+2);
            departTime[i] = ws.getDepart(i+2);
            drive[i] = ws.isDrive(i+2);
        }
        monArrive.setValue(arriveTime[0]);
        monDepart.setValue(departTime[0]);
        monDriveCheck.setSelected(drive[0]);
        tuesArrive.setValue(arriveTime[1]);
        tuesDepart.setValue(departTime[1]);
        tuesDriveCheck.setSelected(drive[1]);
        wedArrive.setValue(arriveTime[2]);
        wedDepart.setValue(departTime[2]);
        wedDriveCheck.setSelected(drive[2]);
        thursArrive.setValue(arriveTime[3]);
        thursDepart.setValue(departTime[3]);
        thursDriveCheck.setSelected(drive[3]);
        friArrive.setValue(arriveTime[4]);
        friDepart.setValue(departTime[4]); 
        friDriveCheck.setSelected(drive[4]);
            
        //change text
        central.setText(centralLocation.toString());
    }
	
    @FXML
    private void handleLocationCombo(ActionEvent event) {
    	selectedLocation = location.getSelectionModel().getSelectedItem();
    }
   
    @FXML
    private void handleMonArriveCombo(ActionEvent event) {
        arriveTime[0] = monArrive.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleMonDepartCombo(ActionEvent event) {
        departTime[0] = monDepart.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleTuesArriveCombo(ActionEvent event) {
    	arriveTime[1] = tuesArrive.getSelectionModel().getSelectedItem();

    }
    
    @FXML
    private void handleTuesDepartCombo(ActionEvent event) {
    	departTime[1] = tuesDepart.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleWedArriveCombo(ActionEvent event) {
    	arriveTime[2] = wedArrive.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleWedDepartCombo(ActionEvent event) {
    	departTime[2] = wedDepart.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleThursArriveCombo(ActionEvent event) {
    	arriveTime[3] = thursArrive.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleThursDepartCombo(ActionEvent event) {
    	departTime[3] = thursDepart.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleFriArriveCombo(ActionEvent event) {
    	arriveTime[4] = friArrive.getSelectionModel().getSelectedItem();
    }
    
    @FXML
    private void handleFriDepartCombo(ActionEvent event) {
    	departTime[4] = friDepart.getSelectionModel().getSelectedItem();
    }
     
    @FXML
    private void handleDriveChecks(ActionEvent event) {
        drive[0] = monDriveCheck.isSelected();
    	drive[1] = tuesDriveCheck.isSelected();
    	drive[2] = wedDriveCheck.isSelected();
    	drive[3] = thursDriveCheck.isSelected();
    	drive[4] = friDriveCheck.isSelected();
    }
    
    @FXML
    private void handleReturnButton(ActionEvent event) {
    	   changeScenePop(event);
    }
    
    @FXML
    private void handleStartDate(ActionEvent event) {
        start = startDate.getValue();
    }
    
    @FXML
    private void handleEndDate(ActionEvent event) {
        end = endDate.getValue();
    }
    
    @FXML
    private void handleSubmitButton(ActionEvent event) {
        //return if invalid
        if (start == null || end == null || selectedLocation == null) {
            Alerts.showError("You must have a start and end date as well as a location selected");
            return;
        }
        
        WeeklySchedule ws = member.getWeeklySchedule();
        //set weekly schedule to given fields    
        ws.setStartTime(new GregorianCalendar(start.getYear(), start.getMonthValue()-1, start.getDayOfMonth()));
        ws.setEndTime(new GregorianCalendar(end.getYear(), end.getMonthValue()-1, end.getDayOfMonth()));
        ws.setPickupLocation(selectedLocation);
        for (int i = 0; i < 5; i++) {
            if (arriveTime[i] != null) {
                GregorianCalendar tg = new GregorianCalendar();
                tg.setTime(arriveTime[i].getTime());
                ws.setArrive(i+2, tg);
            }
            if (departTime[i] != null) {
                GregorianCalendar tg  = new GregorianCalendar();
                tg.setTime(departTime[i].getTime());
                ws.setDepart(i+2, tg);
            }
            ws.setDrive(i+2, drive[i]);
        }
        
        SchedulingContext sc = new SchedulingContext();
        //schedule weekly, starting from now
    	String fail = sc.schedule(new Request(member, new GregorianCalendar(), centralLocation), null);
        if (fail.equals(Scheduler.SUCCESS)) {
            Alerts.showInfo("Schedule Information", "Weekly Schedule Updated!", "Your weekly schedule has been updated. \nCheck your schedule to see the status of your upcoming rides.");
        } else {
            Alerts.showError(fail);
        }
        
    	//return to home screen
    	changeScenePop(event);
    }
    
    /**
     * timesMaker is a helper method to add GregorianCalendar objects (representing week days 
     * with specific times) to the comboboxes with appropriate Strings printed in the dropdown list
     * @param weekDay the int for that day of the week, Sunday=1,Saturday=7
     * @param day the gregorian calendar week day that you are adding times to
     * @return list of times for the Comboboxes with reference to their weekday
     */
    private ObservableList<GregorianCalendar> timesMaker(int weekDay, GregorianCalendar day) {
        ObservableList<GregorianCalendar> times = FXCollections.observableArrayList();
        // get times for 6AM to 11AM
        for (int i = 6; i <= 11; i++) {
            day = new GregorianCalendar() {
                public String toString() {
                    return Integer.toString(this.get(GregorianCalendar.HOUR))
                            + this.getDisplayName(GregorianCalendar.AM_PM, 
                                    GregorianCalendar.LONG, Locale.getDefault());
                    
                }
            };
            day.set(GregorianCalendar.DAY_OF_WEEK, weekDay);
            day.set(GregorianCalendar.AM_PM, GregorianCalendar.AM);
            day.set(GregorianCalendar.HOUR, i);
            day.clear(GregorianCalendar.MINUTE);
            day.clear(GregorianCalendar.SECOND);
            day.clear(GregorianCalendar.MILLISECOND);
            times.add(day);
        }
        
        // get times for noon to 10pm
        for (int i = 12; i <= 22; i++) {
            day = new GregorianCalendar() {
                public String toString() {
                    if (this.get(GregorianCalendar.HOUR) == 0) {
                        return 12 + this.getDisplayName(GregorianCalendar.AM_PM, 
                                        GregorianCalendar.LONG, Locale.getDefault());
                    } else {
                        return Integer.toString(this.get(GregorianCalendar.HOUR))
                                + this.getDisplayName(GregorianCalendar.AM_PM, 
                                        GregorianCalendar.LONG, Locale.getDefault());
                    }
                }
            };
            day.set(GregorianCalendar.DAY_OF_WEEK, weekDay);
            day.set(GregorianCalendar.AM_PM, GregorianCalendar.PM);
            day.set(GregorianCalendar.HOUR_OF_DAY, i);
            day.clear(GregorianCalendar.MINUTE);
            day.clear(GregorianCalendar.SECOND);
            day.clear(GregorianCalendar.MILLISECOND);
            times.add(day);
        }
        return times;
    }

}
