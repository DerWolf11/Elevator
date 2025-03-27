package main.java.com.github.elevator.environment;

import main.java.com.github.elevator.components.internal.AudioSystem;
import main.java.com.github.elevator.components.internal.Display;
import main.java.com.github.elevator.components.internal.FireAccessPanel;
import main.java.com.github.elevator.components.internal.InternalPanel;
import main.java.com.github.elevator.components.internal.KeycardReader;
import main.java.com.github.elevator.components.internal.Scale;

public class Elevator {
    private AudioSystem audioSystem;
    private Display display;
    private FireAccessPanel fireAccessPanel;
    private InternalPanel internalPanel;
    private KeycardReader keycardReader;
    private Scale scale;

    public Elevator(int floorCount) {
        audioSystem = new AudioSystem();
        fireAccessPanel = new FireAccessPanel();
        internalPanel = new InternalPanel(floorCount);
        keycardReader = new KeycardReader();
        scale = new Scale();
    }

    public AudioSystem getAudioSystem() {
        return audioSystem;
    }
    
    public Display getDisplay() {
        return display;
    }
    
    public FireAccessPanel getFireAccessPanel() {
        return fireAccessPanel;
    }
    
    public InternalPanel getInternalPanel() {
        return internalPanel;
    }
    
    public KeycardReader getKeycardReader() {
        return keycardReader;
    }
    
    public Scale getScale() {
        return scale;
    }
}
