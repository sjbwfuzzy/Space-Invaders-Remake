package model;

import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;

// an item that has a position, fall speed, name, and radius.
public abstract class Item implements Writable {
    public static final int FALL_SPEED = 2;
    public static final int RADIUS = 7;

    protected String name;

    private String identifier;

    private int xpos;
    private int ypos;

    // EFFECTS: sets coordinates to given values
    public Item(int x, int y) {
        xpos = x;
        ypos = y;
    }

    public int getX() {
        return xpos;
    }

    public int getY() {
        return ypos;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setX(int x) {
        xpos = x;
    }

    public void setY(int y) {
        ypos = y;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }


    // MODIFIES: this
    // EFFECTS: moves item down by FALL_SPEED
    public void move() {
        ypos = ypos + FALL_SPEED;
    }

    public boolean collidedWith(Player p) {
        Rectangle playerHitbox = new Rectangle(p.getX() - Player.SIZE / 2, p.getY() - Player.SIZE / 2,
                Player.SIZE, Player.SIZE);
        Rectangle itemHitbox = new Rectangle(getX() - RADIUS, getY() - RADIUS,
                RADIUS * 2, RADIUS * 2);
        return playerHitbox.intersects(itemHitbox);
    }

    @Override
    // EFFECTS: returns item as Json object
    public abstract JSONObject toJson();
}
