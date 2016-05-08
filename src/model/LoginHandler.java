package model;

import java.util.List;
import model.member.LoginInformation;
import model.member.Member;
import model.schedule.Request;
import model.schedule.WeeklyScheduler;

/**
 *
 * @author David
 */
public class LoginHandler {
    
    private boolean loggedIn;
    private Context context;

    public LoginHandler() {
        context = Context.getInstance();
        loggedIn = false;
    }
    
    /**
     * Logs in.
     *
     * @param loginInfo
     * @return a string explaining if anything went wrong, "" otherwise 
     */
    public String handleLogin(LoginInformation loginInfo) {
        if (loggedIn)
            return "Already logged in";
        DataHandler data = context.getDataHandler();
        List<Member> members = data.getMembers();
        for (Member m : members) {
            if (m.getLoginInfo().equals(loginInfo)) {
                loggedIn = true;
                context.setMember(data.getMember(m.getIdNumber()));
                context.getMember().addObserver(data);
                (new WeeklyScheduler()).schedule(new Request(m, m.getWeeklySchedule().getLastUpdate(), context.getCentral()), null);
                return "";
            }
        }
        return "Account with that email and password combination not found";
    }
    
    public void handleLogout() {
        loggedIn = false;
        context.getMember().deleteObservers();
        context.setMember(new Member());
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    
}
