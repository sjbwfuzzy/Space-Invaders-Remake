package model;

import java.util.ArrayList;
import java.util.List;

// The inventory of a player, having money, list of Weapon, and list of Buff
public class Inventory {
    private static final int MAX_WEAPONS = 3;
    private static final int MAX_BUFFS = 5;

    private int money;
    private List<Weapon> weapons;
    private List<Buff> buffs;

    // EFFECTS: creates the default inventory
    public Inventory() {
        money = 0;
        weapons = new ArrayList<>();
        buffs = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds amount of money to inventory
    public void addMoney(int amount) {
        money += amount;
    }

    // REQUIRES: amount <= money
    // MODIFIES: this
    // EFFECTS: removes amount of money from inventory
    public void removeMoney(int amount) {
        money -= amount;
    }

    // MODIFIES: this
    // EFFECTS: if inventory still has space for weapon, return true and add weapon. Else, return false
    public boolean addWeapon(Weapon weapon) {
        if (weapons.size() < MAX_WEAPONS) {
            weapons.add(weapon);
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if weapon is in weapons, remove it and return true. Else, return false
    public boolean removeWeapon(Weapon weapon) {
        return weapons.remove(weapon);
    }

    // MODIFIES: this
    // EFFECTS: if inventory still has space for buff, return true and add buff. Else, return false
    public boolean addBuff(Buff buff) {
        if (buffs.size() < MAX_BUFFS) {
            buffs.add(buff);
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: if buff is in buffs, remove it and return true. Else, return false
    public boolean removeBuff(Buff buff) {
        return buffs.remove(buff);
    }

    public int getMoney() {
        return money;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    public List<Buff> getBuffs() {
        return buffs;
    }
}
