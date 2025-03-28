package main.java.com.github.elevator.manager;

import java.util.logging.Logger;

public class AuthenticationManager {
    private static final Logger logger = Logger.getLogger(AuthenticationManager.class.getName());
    private static volatile AuthenticationManager INSTANCE;
    private static final Object MUTEX = new Object();

    public static AuthenticationManager getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        // Double-Check Locking, make new instance
        synchronized (MUTEX) {
            if (INSTANCE == null) {
                INSTANCE = new AuthenticationManager();
            }
            return INSTANCE;
        }
    }

    public boolean isAuthorized(String requestor) {
        // This is where service request authorization would be checked against an 'auth' database table
        // Assume in this case that the 'requestor' is a public key only identifiable to our system (i.e. a keycard access number)
        String eventMsg = "Authorization request made by " + requestor;
        logger.info(eventMsg);
        AuditManager.getInstance().auditEvent(eventMsg);
        return true;
    }

    public void addAuthorization(String auth) {
        /*
         * This call would take in a series of auth details for new keycards/users and update the 'auth' database table
         * Columns would include details like: user id, elevator number, access issue/revoke dates, floor and operational mode access, and active status
         */
        return;
    }

    public void revokeAuthorization(String auth) {
        // This call would revoke auth access for a specified user.
        // It could also be enhanced to just deactivate access, rather than removing entirely
        return;
    }
}