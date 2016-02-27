package model;

/**
 *
 * Follows Singleton design pattern. Used to hold user information.
 * @author David
 */
public class Context {
    private static Context instance = null;
    
    private Context() {
      // Exists only to defeat instantiation.
    }
    
    public static Context getInstance() {
        if(instance == null) {
            instance = new Context();
        }
        return instance;
    }
}
