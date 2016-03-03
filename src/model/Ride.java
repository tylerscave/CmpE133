package model;

/**
 *
 * @author David
 */
public class Ride {
    private Member member;
    private Route route;
    
    public Ride(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
    
    public void remove() {
        //TODO
    }
}
