package main.java.com.github.elevator.components.internal;

import main.java.com.github.elevator.components.interfaces.KeyAccess;

public class FireInternalKeyAccessImpl implements KeyAccess{
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
