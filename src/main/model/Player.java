package model;

// Represents the player, having health, attack, speed, experience, level
public class Player {
    private int maxhealth;
    private int attack;
    private int speed;
    private int experience;
    private int level;

    // EFFECTS: constructs player with starting stats and inventory
    public Player() {
        maxhealth = 10;
        attack = 1;
        speed = 1;
        experience = 0;
        level = 1;
    }

    public int getMaxHealth() {
        return maxhealth;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: adds health to player
    public void addMaxHealth(int amount) {
        maxhealth += amount;
    }

    // REQUIRES: amount > 0 and amount < maxhealth
    // MODIFIES: this
    // EFFECTS: subtracts health from player
    public void subMaxHealth(int amount) {
        maxhealth -= amount;
    }

    public int getAttack() {
        return attack;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: adds attack to player
    public void addAttack(int amount) {
        attack += amount;
    }

    // REQUIRES: amount > 0 and amount < attack
    // MODIFIES: this
    // EFFECTS: subtracts attack from player
    public void subAttack(int amount) {
        attack -= amount;
    }

    public int getSpeed() {
        return speed;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: adds speed to player
    public void addSpeed(int amount) {
        speed += amount;
    }

    // REQUIRES: amount > 0 and amount < speed
    // MODIFIES: this
    // EFFECTS: subtracts speed from player
    public void subSpeed(int amount) {
        speed -= amount;
    }

    public int getExperience() {
        return experience;
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

    public int getLevel() {
        return level;
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
