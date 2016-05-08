package model;

import java.util.List;
import model.member.Member;
import model.graph.GraphMap;
import model.schedule.Location;

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
    private Tracker tracker;
    private Location central;
    
    private Context() {
        // Exists only to defeat instantiation.
        map = new GraphMap();
        dataHandler = new NewDataHandler();
        tracker = new GPSTracker();
        central = new Location("San Jose State University");
        List<Location> locations = map.getLocations();
        for (Location l : locations) {
            if (l.equals(central)) {
                central = l;
                break;
            }
        }
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
            this.member = new Member();
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

    public Tracker getTracker() {
        return tracker;
    }

    public Location getCentral() {
        return central;
    }
    
}
