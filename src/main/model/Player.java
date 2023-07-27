package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents the player, having health, attack, speed, experience, level
public class Player implements Writable {
    private ArrayList<Integer> stats;

    private int experience;
    private int level;

    private Inventory inventory;

    // EFFECTS: constructs player with given parameters
    public Player(ArrayList<Integer> s, int exp, int lvl, Inventory i) {
        stats = s;
        experience = exp;
        level = lvl;
        inventory = i;
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

    public Inventory getInventory() {
        return inventory;
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
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("stats", statsToJson());
        json.put("experience", experience);
        json.put("level", level);
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
