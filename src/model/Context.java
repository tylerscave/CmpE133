package model;

/**
 *
 * Follows Singleton design pattern. Used to hold user information.
 * @author David
 */
public class Context {
    private static Context instance = null;
    private MemberHandler member;
    
    private Context() {
        // Exists only to defeat instantiation.
    }
    
    public static Context getInstance() {
        if(instance == null) {
            instance = new Context();
        }
        return instance;
    }

    public void setMember(MemberHandler member) {
        this.member = member;
    }
    
    
    public MemberHandler getMember() {
        if (this.member == null)
            this.member = new MemberHandler();
        return this.member;
    }
}
