package main.java.com.github.elevator.component.internal;

public class Scale {
    float weightKilograms;

    public float getWeightKilograms() {
        return weightKilograms;
    }

    public void setWeightKilograms(float weightKilograms) {
        this.weightKilograms = weightKilograms;
    }

    public float getCurrentWeightPounds() {
        return weightKilograms * 2.2f;
    }

    public void setCurrentWeightPounds(float weightPounds) {
        this.weightKilograms = weightPounds / 2.2f;
    }
}
