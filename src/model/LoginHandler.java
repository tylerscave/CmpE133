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
    
    
    public void handleLogin(String username, String password) {
        //TODO
        loggedIn = true;
    }
    
    public void handleLogout() {
        //TODO
        loggedIn = false;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    
}
