package model;

import java.util.ArrayList;

// represents a buff with a name, and a list of modifiers to increase player stats with
public class Buff {
    private String name;
    private ArrayList<Integer> modifiers;

    // EFFECTS: Initializes a buff with name, and list of modifier amounts
    public Buff(String name, ArrayList<Integer> amounts) {
        this.name = name;
        this.modifiers = amounts;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getModifiers() {
        return modifiers;
    }

    // REQUIRES: 0 <= index < modifiers.size()
    public int getOneModifier(int index) {
        return modifiers.get(index);
    }

    // REQUIRES: 0 <= index < modifiers.size()
    public void setOneModifier(int index, int value) {
        modifiers.set(index, value);
    }
}
