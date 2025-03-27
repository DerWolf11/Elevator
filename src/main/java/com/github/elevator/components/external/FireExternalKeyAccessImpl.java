package main.java.com.github.elevator.components.external;

import main.java.com.github.elevator.components.interfaces.KeyAccess;

public class FireExternalKeyAccessImpl implements KeyAccess {
    private boolean keyTurned;

    @Override
    public boolean isKeyTurned() {
        return keyTurned;
    }

    @Override
    public void setKeyTurned() {
        keyTurned = !keyTurned;
    }
}
