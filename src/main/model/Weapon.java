package model;

import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// a weapon, having name, fire rate, bullet type
public class Weapon extends Item implements Writable {
    public static final Color COLOR = new Color(123, 13, 219);

    private int fireRate; // shots per second
    private String size;
    private boolean canFire = false;
    private Timer timer;

    // EFFECTS: creates a weapon with name, fire rate, and a bullet
    public Weapon(String size, int x, int y) {
        super(x, y);
        this.size = size;
        setIdentifier("Weapon");
        switch (size) {
            case "SMALL":
                name = "Small Gun";
                fireRate = 3;
                break;
            case "MEDIUM":
                name = "Medium Gun";
                fireRate = 2;
                break;
            case "LARGE":
                name = "Large Gun";
                fireRate = 1;
                break;
        }
    }

    // delay in milliseconds
    // MODIFIES: this
    // EFFECTS: initializes the timer to set canFire to true every delay milliseconds. Set canFire to true first.
    public void setTimer(int delay) {
        canFire = true;
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canFire = true;
            }
        });
        timer.start();
    }

    // MODIFIES: this
    // EFFECTS: restarts the timer
    public void restartTimer() {
        timer.restart();
    }

    public int getFireRate() {
        return fireRate;
    }

    public String getSize() {
        return size;
    }

    public boolean canFire() {
        return canFire;
    }

    public void setCanFire(boolean b) {
        this.canFire = b;
    }

    @Override
    // EFFECTS: returns weapon as Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("identifier", getIdentifier());
        json.put("xpos", getX());
        json.put("ypos", getY());
        json.put("size", size);
        return json;
    }
}
