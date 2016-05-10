package model;

import java.util.List;
import model.member.Member;
import model.graph.GraphMap;
import model.payment.BankHandler;
import model.payment.CreditCardHandler;
import model.payment.StubBankAccount;
import model.payment.StubCreditCard;
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
    private ParkingNotifier parkingNotifier;
    
    private CreditCardHandler cardHandler;
    private BankHandler bankHandler;
    
    private Context() {
        // Exists only to defeat instantiation.
        map = new GraphMap();
        dataHandler = new NewDataHandler();
        tracker = new ScheduleTracker();
        central = new Location("San Jose State University");
        List<Location> locations = map.getLocations();
        for (Location l : locations) {
            if (l.equals(central)) {
                central = l;
                break;
            }
        }
        
        cardHandler = new StubCreditCard();
        bankHandler = new StubBankAccount();
        parkingNotifier = new NewParkingNotifier();
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

    public CreditCardHandler getCardHandler() {
        return cardHandler;
    }

    public BankHandler getBankHandler() {
        return bankHandler;
    }

    public ParkingNotifier getParkingNotifier() {
        return parkingNotifier;
    }
    
}
