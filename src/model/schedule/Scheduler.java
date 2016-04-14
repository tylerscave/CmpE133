package model.schedule;

import model.schedule.ScheduleRequest;
import model.schedule.Schedulable;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author David
 */
public abstract class Scheduler {
    public List<Schedulable> getAvailable(ScheduleRequest r) {
        List<Schedulable> available = getAllAvailable();
        Iterator<Schedulable> it = available.iterator();
        while (it.hasNext()) {
            Schedulable s = it.next();
            if (!isAvailable(r, s))
                it.remove();
        }
        return available;
    }
    
    public abstract String schedule(ScheduleRequest r, Schedulable s);

    public abstract List<Schedulable> getAllAvailable();
    
    public abstract boolean isAvailable(ScheduleRequest r, Schedulable s);
}
