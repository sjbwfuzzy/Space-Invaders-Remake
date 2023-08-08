package model;

import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.util.ArrayList;

// represents a buff with a name, and a list of modifiers to increase player stats with
public class Buff extends Item implements Writable {
    public static final Color COLOR = new Color(30, 219, 13);

    private ArrayList<Integer> modifiers = new ArrayList<>(4);
    private int type;

    // EFFECTS: Initializes a buff depending on the type (a number between 0 and 3)
    public Buff(int type, int x, int y) {
        super(x, y);
        this.type = type;
        for (int counter = 0; counter < 4; counter++) {
            modifiers.add(0);
        }
        setIdentifier("Buff");
        switch (type) {
            case 0:
                name = "Increase Max Health";
                modifiers.set(0, 3);
                break;
            case 1:
                name = "Increase Bonus Attack";
                modifiers.set(1, 1);
                break;
            case 2:
                name = "Increase Movement Speed";
                modifiers.set(2, 1);
                break;
            case 3:
                name = "Increase Fire Rate";
                modifiers.set(3, 10);
        }
    }

    public ArrayList<Integer> getModifiers() {
        return modifiers;
    }

    // REQUIRES: 0 <= index < modifiers.size()
    // EFFECTS: gets modifier from modifiers with given index
    public int getOneModifier(int index) {
        return modifiers.get(index);
    }

    // REQUIRES: 0 <= index < modifiers.size()
    // EFFECTS: sets modifier to value from modifiers with given index
    public void setOneModifier(int index, int value) {
        modifiers.set(index, value);
    }

    public int getType() {
        return type;
    }

    @Override
    // EFFECTS: returns Buff as Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("identifier", getIdentifier());
        json.put("xpos", getX());
        json.put("ypos", getY());
        return json;
    }
}
