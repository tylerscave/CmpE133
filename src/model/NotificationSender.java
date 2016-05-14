package model;

import java.util.List;
import model.member.Member;

/**
 *
 * @author David Lerner
 */
public class NotificationSender {
    private Member member;

    /**
     * Constructor for NotificationSender.
     * @param member the member who will be sending notifications
     */
    public NotificationSender(Member member) {
        this.member = member;
    }
    
    /**
     * Sends a notification to another member
     * 
     * @param email the email of the member you wish to send the notification to
     * @param message the message to send in the notification
     */
    public void send(String email, String message) {
        DataHandler data = Context.getInstance().getDataHandler();
        List<Member> members = data.getMembers();
        for (Member m : members) {
            if (m.getLoginInfo().getEmail().equals(email)) {
                Member loggedIn = Context.getInstance().getMember();
                if (m.getIdNumber() == loggedIn.getIdNumber())
                    loggedIn.addNewNotification(new Notification("From "+member.toString()+": "+message));
                else
                    data.notify(m.getIdNumber(), new Notification("From "+member.toString()+": "+message));
                break;
            }       
        }
    }
}
