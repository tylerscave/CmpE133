package model;

/**
 *
 * @author David
 */
public class NotificationSender {
    private Member member;

    public NotificationSender(Member member) {
        this.member = member;
    }
    
    public void send(int toMember, String message) {
        Context.getInstance().getDataHandler().notify(toMember, new Notification("From "+member.getFirstName()+" "+member.getLastName()+": "+message));
    }
}
