package model;

import java.util.List;
import model.member.LoginInformation;
import model.member.Member;
import model.schedule.Request;
import model.schedule.WeeklyScheduler;

/**
 * Handles logging in/out
 * @author David Lerner
 */
public class LoginHandler {
    
    private boolean loggedIn;
    private Context context;
    private DataHandler data;

    public LoginHandler() {
        context = Context.getInstance();
        data = context.getDataHandler();
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
        if (loginInfo.getEmail().equals(""))
            return "Email field empty";
        if (loginInfo.getPassword().equals(""))
            return "Passord field empty";
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
    
    /**
     * Logs out.
     */
    public void handleLogout() {
        loggedIn = false;
        context.getMember().deleteObservers();
        context.setMember(new Member());
    }

    /**
     *
     * @return whether currently logged in 
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    /**
     * Checks if email address is available
     * @param email
     * @return whether the email address is available
     */
    public boolean emailAvailable(String email) {
        List<Member> members = data.getMembers();
        for (Member m : members) {
            if (m.getLoginInfo().getEmail().equals(email))
                return false;
        }
        return true;
    }
}
