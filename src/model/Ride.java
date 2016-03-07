package model;

/**
 *
 * @author David
 */
public class Ride {
    private Member member;
    private Route route;
    private Drive drive;
    
    public Ride(Member member, Drive drive) {
        this.member = member;
        this.drive = drive;
    }

    public Member getMember() {
        return member;
    }

    public Drive getDrive() {
        return drive;
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
