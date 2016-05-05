package model.schedule;

import java.util.GregorianCalendar;
import model.schedule.Request;
import model.schedule.Schedulable;
import java.util.Iterator;
import java.util.List;
import model.Context;
import model.DataHandler;

/**
 *
 * @author David
 */
public abstract class Scheduler {
    protected DataHandler data;
    protected Context context;

    public Scheduler() {
        context = Context.getInstance();
        data = context.getDataHandler();
    }
    
    public abstract List<Schedulable> getAvailable(Request r);
    
    public abstract String schedule(Request r, Schedulable s);
    
    protected String correctData(Request r) {
        if (r == null)
            return "Failure: No request given";
        if (r.getStartLocation() == null || r.getStartTime() == null || r.getStartType() == null
                || r.getEndLocation() == null || r.getEndTime() == null || r.getEndType() == null)
            return "Failure: Incorrect info given";
        if (r.getStartTime().after(r.getEndTime()) && r.getStartType() != Request.TimeType.Anytime && r.getEndType() != Request.TimeType.Anytime)
            return "Failure: Start time after end time";
        if (r.getEndTime().before(new GregorianCalendar()) && r.getEndType() != Request.TimeType.Anytime)
            return "Failure: Scheduled time is in the past";
        return "";
    }
}
