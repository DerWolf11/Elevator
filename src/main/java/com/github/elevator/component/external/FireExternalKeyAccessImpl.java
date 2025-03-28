package main.java.com.github.elevator.component.external;

import main.java.com.github.elevator.component.interfaces.KeyAccess;

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
