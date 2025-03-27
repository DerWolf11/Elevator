package main.java.com.github.elevator.component.internal;

import main.java.com.github.elevator.component.interfaces.Button;

public class CallButtonImpl implements Button {
    private String phoneNumber;
    private boolean pressed;
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean isPressed() {
        return pressed;
    }

    @Override
    public void setPressed() {
        pressed = !pressed;
    }
}
