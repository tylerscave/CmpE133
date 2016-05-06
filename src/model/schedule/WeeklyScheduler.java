package model.schedule;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David
 */
public class WeeklyScheduler extends Scheduler{

    @Override
    public String schedule(Request r, Schedulable s) {
        WeeklySchedule ws = r.getMember().getWeeklySchedule();
        return "Success";
    }

    @Override
    public List<Schedulable> getAvailable(Request r) {
        return new ArrayList<>();
    }

}
