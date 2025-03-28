package main.java.com.github.elevator.manager;

import java.util.logging.Logger;

import main.java.com.github.elevator.config.LoggerConfig;

public class AuditManager {
    private static final Logger logger = LoggerConfig.getLogger(AuthenticationManager.class.getName());
    private static volatile AuditManager INSTANCE;
    private static final Object MUTEX = new Object();

    public static AuditManager getInstance() {
        if (INSTANCE != null) {
            return INSTANCE;
        }
        // Double-Check Locking, make new instance
        synchronized (MUTEX) {
            if (INSTANCE == null) {
                INSTANCE = new AuditManager();
            }
            return INSTANCE;
        }
    }

    public void auditEvent(String eventMsg) {
        // This is where an audit event would be output to an 'events' table
        // The event would include details like the timstamp, elevator number, floor, and details of the request
        logger.info("Audit event: " + eventMsg);
    }
}