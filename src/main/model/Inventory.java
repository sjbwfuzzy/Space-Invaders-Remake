package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Objects;

// The inventory of a player, having money, list of Weapon, and list of Buff
public class Inventory implements Writable {
    public static final int MAX_WEAPONS = 3;
    public static final int MAX_BUFFS = 5;

    private int money;
    private ArrayList<Weapon> weapons;
    private ArrayList<Buff> buffs;

    // EFFECTS: creates the default inventory
    public Inventory(int m, ArrayList<Weapon> w, ArrayList<Buff> b) {
        money = m;
        weapons = w;
        buffs = b;
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
    protected boolean addBuff(Buff buff) {
        boolean canAdd = buffs.size() < MAX_BUFFS;
        if (canAdd) {
            buffs.add(buff);
        }
        return canAdd;
    }

    // MODIFIES: this
    // EFFECTS: if buff is in buffs, remove it and return true. Else, return false
    protected boolean removeBuff(Buff buff) {
        return buffs.remove(buff);
    }

    public int getMoney() {
        return money;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    // EFFECTS: returns true if there is a buff with given name, otherwise return false
    public boolean containsBuff(String name) {
        for (Buff buff : buffs) {
            if (Objects.equals(buff.getName(), name)) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: returns true if there is a weapon with given name, otherwise return false
    public boolean containsWeapon(String name) {
        for (Weapon weapon : weapons) {
            if (Objects.equals(weapon.getName(), name)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: containsBuff(name) is true
    // EFFECTS: returns the first buff with given name
    public Buff getBuff(String name) {
        for (Buff buff : buffs) {
            if (Objects.equals(buff.getName(), name)) {
                return buff;
            }
        }
        return null;
    }

    // REQUIRES: containsWeapon(name) is true
    // EFFECTS: returns the first weapon with given name
    public Weapon getWeapon(String name) {
        for (Weapon weapon : weapons) {
            if (Objects.equals(weapon.getName(), name)) {
                return weapon;
            }
        }
        return null;
    }

    @Override
    // EFFECTS: returns inventory as Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("money", money);
        json.put("weapons", weaponsToJson());
        json.put("buffs", buffsToJson());
        return json;
    }

    // EFFECTS: returns buffs as a JSON array
    private JSONArray buffsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Buff buff : buffs) {
            jsonArray.put(buff.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns weapons as a JSON array
    private JSONArray weaponsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Weapon weapon : weapons) {
            jsonArray.put(weapon.toJson());
        }
        return jsonArray;
    }
}
