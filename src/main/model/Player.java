package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.*;
import java.util.ArrayList;

// inspiration from https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

// Represents the player
public class Player implements Writable {
    public static final int SIZE = 10;
    public static final Color COLOR = new Color(250, 128, 20);

    // includes Max Health, Bonus Attack, Movement Speed, and Fire Rate
    private ArrayList<Integer> stats;

    private int experience;
    private int level;

    private int health;
    private int xpos;
    private int ypos;
    private int xdirection;
    private int ydirection;

    private Inventory inventory;

    // REQUIRES: s.size() is always 4
    // EFFECTS: constructs player with given parameters
    public Player(ArrayList<Integer> s, int exp, int lvl, Inventory i, int x, int y) {
        stats = s;
        experience = exp;
        level = lvl;
        inventory = i;

        health = getMaxHealth();
        xpos = x;
        ypos = y;
        xdirection = 0;
        ydirection = 0;
    }


    public int getX() {
        return xpos;
    }

    public int getY() {
        return ypos;
    }

    public int getXdirection() {
        return xdirection;
    }

    public int getYdirection() {
        return ydirection;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return stats.get(0);
    }

    public int getBonusAttack() {
        return stats.get(1);
    }

    public int getSpeed() {
        return stats.get(2);
    }

    public int getFireRate() {
        return stats.get(3);
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setX(int xpos) {
        this.xpos = xpos;
    }

    public void setY(int ypos) {
        this.ypos = ypos;
    }

    public void setXdir(int xdirection) {
        this.xdirection = xdirection;
    }

    public void setYdir(int ydirection) {
        this.ydirection = ydirection;
    }

    public Inventory getInventory() {
        return inventory;
    }

    // MODIFIES: this
    // EFFECTS: adds amount to health
    public void updateHealth(int amount) {
        health = health + amount;
    }

    // MODIFIES: this
    // EFFECTS: Faces player to the right
    public void faceRight() {
        xdirection = 1;
    }

    // MODIFIES: this
    // EFFECTS: Faces player to the left
    public void faceLeft() {
        xdirection = -1;
    }

    // MODIFIES: this
    // EFFECTS: Faces player up
    public void faceUp() {
        ydirection = -1;
    }

    // MODIFIES: this
    // EFFECTS: Faces player down
    public void faceDown() {
        ydirection = 1;
    }

    // Updates the tank on clock tick
    // modifies: this
    // effects:  tank is moved DX units in whatever direction it is facing and is
    //           constrained to remain within vertical boundaries of game
    public void move() {
        xpos = xpos + xdirection * getSpeed();
        ypos = ypos + ydirection * getSpeed();
        handleBoundary();
    }

    // Constrains tank so that it doesn't travel of sides of screen
    // modifies: this
    // effects: tank is constrained to remain within vertical boundaries of game
    private void handleBoundary() {
        if (xpos < 0) {
            xpos = 0;
        } else if (xpos > Game.WIDTH) {
            xpos = Game.WIDTH;
        }

        if (ypos > Game.HEIGHT) {
            ypos = Game.HEIGHT;
        } else if (ypos < 0) {
            ypos = 0;
        }
    }

    // REQUIRES: buff.getModifiers().size() = stats.size()
    // MODIFIES: this, buff
    // EFFECTS: If inventory has space, updates stats. If any updated value is less than 1, edit the buff such that
    // the updated value will be exactly one, and add buff to inventory, and return true. If Inventory has no space,
    // return false
    public boolean updateStats(Buff buff) {
        boolean isAdded = inventory.addBuff(buff);
        if (isAdded) {
            for (int index = 0; index < stats.size(); index++) {
                int modifier = buff.getOneModifier(index);
                int current = stats.get(index);
                int newValue = current + modifier;
                if (newValue >= 1) {
                    stats.set(index, newValue);
                } else {
                    buff.setOneModifier(index, 1 - current);
                    stats.set(index, 1);
                }
            }
        }
        return isAdded;
    }

    public boolean collidedWith(Enemy e) {
        Rectangle enemyHitbox = new Rectangle(e.getX() - e.getXsize() / 2, e.getY() - e.getYsize() / 2,
                e.getXsize(), e.getYsize());
        Rectangle playerHitbox = new Rectangle(getX() - Player.SIZE / 2, getY() - Player.SIZE / 2,
                Player.SIZE, Player.SIZE);
        return enemyHitbox.intersects(playerHitbox);
    }

    // MODIFIES: this
    // EFFECTS: If buff is in inventory, removes buff from inventory, updates stats accordingly, and returns true.
    // If buff is not in inventory, return false.
    public boolean removeStats(Buff buff) {
        boolean isRemoved = inventory.removeBuff(buff);
        if (isRemoved) {
            for (int index = 0; index < stats.size(); index++) {
                int reversedModifier = -buff.getOneModifier(index);
                int current = stats.get(index);
                int newValue = current + reversedModifier;
                stats.set(index, newValue);
            }
        }
        return isRemoved;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: adds experience to player
    public void addExperience(int amount) {
        experience += amount;
    }

    // REQUIRES: amount > 0 and amount < experience
    // MODIFIES: this
    // EFFECTS: subtracts experience from player
    public void subExperience(int amount) {
        experience -= amount;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: adds level to player
    public void addLevel(int amount) {
        level += amount;
    }

    // REQUIRES: amount > 0 and amount < level
    // MODIFIES: this
    // EFFECTS: subtracts level from player
    public void subLevel(int amount) {
        level -= amount;
    }

    @Override
    // EFFECTS: returns player as Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("stats", statsToJson());
        json.put("experience", experience);
        json.put("level", level);
        json.put("health", health);
        json.put("xpos", xpos);
        json.put("ypos", ypos);
        json.put("xdir", xdirection);
        json.put("ydir", ydirection);
        json.put("inventory", inventory.toJson());
        return json;
    }

    // EFFECTS: returns player stats as a JSON array
    private JSONArray statsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (int stat : stats) {
            jsonArray.put(stat);
        }
        return jsonArray;
    }
}
