package model.schedule;

import model.schedule.Scheduler;
import model.schedule.ScheduleRequest;
import model.schedule.Schedulable;
import java.util.List;

/**
 *
 * @author David
 */
public class WeeklyScheduler extends Scheduler{

    @Override
    public String schedule(ScheduleRequest r, Schedulable s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Schedulable> getAllAvailable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAvailable(ScheduleRequest r, Schedulable s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}