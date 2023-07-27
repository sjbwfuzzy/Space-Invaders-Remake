package model;

import java.util.ArrayList;

// Represents the player, having health, attack, speed, experience, level
public class Player {
    private ArrayList<Integer> stats;

    private int experience;
    private int level;

    private Inventory inventory;

    // EFFECTS: constructs player with starting stats, experience and level, and starting inventory
    public Player() {
        stats = new ArrayList<>(4);
        stats.add(10); //max health
        stats.add(1); //bonus attack
        stats.add(10); //speed
        stats.add(100); //fire rate (percentage)

        experience = 0;
        level = 1;

        inventory = new Inventory();
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
}
