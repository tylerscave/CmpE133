package model.schedule;

import java.util.GregorianCalendar;
import java.util.List;
import model.Context;
import model.DataHandler;
import model.StringFormat;

/**
 *
 * @author David
 */
public abstract class Scheduler {
    protected DataHandler data;
    protected Context context;
    
    public static final String SUCCESS = "Success";

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
        if (r.getStartTime().after(r.getEndTime()) && r.getStartType() != Request.TimeType.Anytime 
                && r.getEndType() != Request.TimeType.Anytime)
            return "Failure: Start time after end time";
        GregorianCalendar currentTime = new GregorianCalendar();
        if ((r.getEndTime().before(currentTime) && r.getEndType() != Request.TimeType.Anytime)
                || (r.getStartTime().before(currentTime) && r.getStartType() != Request.TimeType.Anytime))
            return "Failure: Scheduled time is in the past";
        return SUCCESS;
    }
    
    protected String getDateFromCalendar(GregorianCalendar gc) {
        return StringFormat.getDateFromCalendar(gc);
    }
    
    protected String getTimeFromCalendar(GregorianCalendar gc) {
        return StringFormat.getTimeFromCalendar(gc);
    }
}
