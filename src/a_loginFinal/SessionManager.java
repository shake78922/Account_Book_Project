package a_loginFinal;

public class SessionManager {
    private String currentID;
    private String currentName;
    private static SessionManager instance;

    // Private constructor to prevent direct instantiation
    private SessionManager() {
        // Initialize variables or perform other setup if needed
    }

    // Method to get the singleton instance
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public String getID() {
        return currentID;
    }

    public String getName() {
        return currentName;
    }

    public void setCurrentUser(String ID) {
        this.currentID = ID;
        DB db = new DB(); // Create an instance of DBuser
        this.currentName = db.getNameByID(ID); // Fetch the name from the database based on ID
    }
    
    public void logout() {
        // Clear the current session information
        currentID = null;
        currentName = null;
        // Perform any additional logout-related tasks if needed
    }

    // Add other session-related methods if needed
}
