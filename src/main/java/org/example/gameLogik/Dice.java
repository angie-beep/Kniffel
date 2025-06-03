package org.example.gameLogik;

public class Dice {
    private int value;
    private boolean held;

    public Dice() {
        roll();
        held = false;
    }

    public void roll() {
        if (!held) {
            this.value = (int) (Math.random() * 6) + 1;
        }
    }

    public int getValue() {
        return value;
    }

    public boolean isHeld() {
        return held;
    }

    public void setHeld(boolean held) {
        this.held = held;
    }

    public void toggleHold() {
        this.held = !this.held;
    }
}