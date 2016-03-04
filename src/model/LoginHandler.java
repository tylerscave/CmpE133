package model;

/**
 *
 * @author David
 */
public class LoginHandler {
    
    private boolean loggedIn;

    public LoginHandler() {
        loggedIn = false;
    }
    
    /**
     * Currently a stub. Creates a new account with the given username and password
     * TODO: Make this access a database of members
     *
     * @param email
     * @param password
     */
    public void handleLogin(LoginInformation loginInfo) {
        loggedIn = true;
        //stub
        Context.getInstance().setMember(new Student(loginInfo, "", "", new Address("", "", "", "", ""), "", new Passenger(), ""));
    }
    
    public void handleLogout() {
        //TODO
        loggedIn = false;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    
}
