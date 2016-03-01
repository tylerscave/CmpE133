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
     * @param username
     * @param password
     */
    public void handleLogin(String username, String password) {
        loggedIn = true;
        //stub
        Context.getInstance().setMember(new Student("", "", "", "", new Address("", "", "", "", ""), "", new Passenger(), ""));
    }
    
    public void handleLogout() {
        //TODO
        loggedIn = false;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    
}
