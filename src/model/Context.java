package model;

import model.graph.GraphMap;

/**
 *
 * Follows Singleton design pattern. Used to hold user information.
 * @author David
 */
public class Context {
    private static Context instance = null;
    private Member member;
    private LoginHandler login;
    private LocationMap map;
    private DataHandler dataHandler;
    
    private Context() {
        // Exists only to defeat instantiation.
        map = new GraphMap();
        dataHandler = new NewDataHandler();
    }
    
    public static Context getInstance() {
        if(instance == null) {
            instance = new Context();
        }
        return instance;	
    }

    public void setMember(Member member) {
        this.member = member;
    }
    
    public Member getMember() {
        if (this.member == null)
            this.member = new Student(new LoginInformation("", ""), "", "", new Address("", "", "", "", ""), "", new Passenger(), "");
        return this.member;
    }

    public void setLogin(LoginHandler login) {
        this.login = login;
    }

    public LoginHandler getLogin() {
        if (this.login == null)
            this.login = new LoginHandler();
        return login;
    }

    public LocationMap getMap() {
        return map;
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }
    
}
