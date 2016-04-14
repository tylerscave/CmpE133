package model.schedule;

import model.schedule.Route;
import model.schedule.Drive;

/**
 *
 * @author David
 */
public class DriveChoice {

    private Drive drive;
    private Route route;

    public DriveChoice(Drive drive, Route route) {
        this.drive = drive;
        this.route = route;
    }

    public Drive getDrive() {
        return drive;
    }

    public Route getRoute() {
        return route;
    }
    
}
