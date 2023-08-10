package model;

import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;

// inspiration from https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

// represents an enemy
public class Enemy implements Writable {
    public static final Color COLOR = new Color(10, 50, 188);

    private int score;
    private int health;
    private int xsize;
    private int ysize;
    private int xpos;
    private int ypos;
    private Item item;
    private String size;

    // EFFECTS: constructs an enemy positioned at coordinates, with varying sizes
    public Enemy(String size, int x, int y) {
        this.size = size;
        this.xpos = x;
        this.ypos = y;
        switch (size) {
            case "SMALL":
                xsize = 15;
                ysize = 10;
                score = health = 5;
                break;
            case "MEDIUM":
                xsize = 25;
                ysize = 20;
                score = health = 10;
                break;
            case "LARGE":
                xsize = 35;
                ysize = 30;
                score = health = 20;
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds amount to xpos
    public void updateX(int amount) {
        xpos += amount;
    }

    // MODIFIES: this
    // EFFECTS: adds amount to ypos
    public void updateY(int amount) {
        ypos += amount;
    }

    public String getSize() {
        return size;
    }

    public int getXsize() {
        return xsize;
    }

    public int getYsize() {
        return ysize;
    }

    public int getX() {
        return xpos;
    }

    public int getY() {
        return ypos;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }

    public void setHealth(int h) {
        this.health = h;
    }

    // MODIFIES: this
    // EFFECTS: reduces health by given amount
    public void reduceHealth(int amount) {
        this.health -= amount;
    }

    @Override
    // EFFECTS: returns enemy as Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("health", health);
        json.put("xpos", getX());
        json.put("ypos", getY());
        json.put("size", size);
        if (item == null) {
            json.put("item", JSONObject.NULL);
        } else {
            json.put("item", item.toJson());
        }
        return json;
    }
}
