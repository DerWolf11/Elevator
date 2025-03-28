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
}