package ui;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

// Inspiration from https://github.students.cs.ubc.ca/CPSC210/TellerApp

// Game application
public class Game {
    private Player player;
    private Inventory inventory;
    private Scanner input;

    // EFFECTS: initializes the game
    public Game() {
        player = new Player();
        inventory = new Inventory();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        runGame();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runGame() {
        boolean keepGoing = true;
        int choice;

        while (keepGoing) {
            printInfo();
            choice = input.nextInt();

            if (choice == 9) {
                keepGoing = false;
            } else {
                processInput(choice);
            }
        }
        System.out.println("Thanks for playing the demo!");
    }

    // EFFECTS: prints options
    private void printInfo() {
        System.out.println("\nPlease select an option: ");
        System.out.println("1: view inventory");
        System.out.println("2: view player stats");
        System.out.println("3: add money");
        System.out.println("4: remove money");
        System.out.println("5: add weapon");
        System.out.println("6: remove weapon");
        System.out.println("7: add buff");
        System.out.println("8: remove buff");
        System.out.println("9: quit\n");
    }

    // MODIFIES: this
    // EFFECTS: processes user input from main menu
    private void processInput(int choice) {
        if (choice == 1) {
            viewInventory();
        } else if (choice == 2) {
            viewStats();
        } else if (choice == 3) {
            addMoney();
        } else if (choice == 4) {
            removeMoney();
        } else if (choice == 5) {
            addWeapon();
        } else if (choice == 6) {
            removeWeapon();
        } else if (choice == 7) {
            addBuff();
        } else if (choice == 8) {
            removeBuff();
        } else {
            System.out.println("Not a valid option!");
        }
    }

    // EFFECTS: prints out inventory
    private void viewInventory() {
        System.out.println("Money: " + inventory.getMoney());
        System.out.println("Weapons: ");
        for (Weapon weapon : inventory.getWeapons()) {
            System.out.println("Weapon Name: " + weapon.getName() + ", Fire Rate: " + weapon.getFirerate()
                    + ", Bullet type: coming soon...");
        }
        System.out.println("Buffs: ");
        for (Buff buff : inventory.getBuffs()) {
            System.out.println("Buff Name: " + buff.getName());
        }
    }

    // EFFECTS: prints out player stats
    private void viewStats() {
        System.out.println("Health: " + player.getMaxHealth());
        System.out.println("Attack: " + player.getAttack());
        System.out.println("Speed: " + player.getSpeed());
        System.out.println("Experience: " + player.getExperience());
        System.out.println("Level: " + player.getLevel());
    }

    // MODIFIES: this
    // EFFECTS: adds money to the inventory
    private void addMoney() {
        System.out.println("How much money to add?");
        inventory.addMoney(input.nextInt());
    }

    // MODIFIES: this
    // EFFECTS: removes money from the inventory
    private void removeMoney() {
        System.out.println("How much money to remove?");
        inventory.removeMoney(input.nextInt());
    }

    // MODIFIES: this
    // EFFECTS: adds the weapon to the inventory
    private void addWeapon() {
        System.out.println("Name the weapon: ");
        input.nextLine();
        String name = input.nextLine();
        System.out.println("Give a fire rate: ");
        int rate = input.nextInt();
        Bullet bullet = new Bullet(1, 1, 1, 1, true);
        inventory.addWeapon(new Weapon(name, rate, bullet));
        System.out.println("Weapon was added");
    }

    // MODIFIES: this
    // EFFECTS: removes the weapon with the given name from the inventory
    private void removeWeapon() {
        System.out.println("Name the weapon: ");
        input.nextLine();
        String name = input.nextLine();
        for (Weapon weapon : inventory.getWeapons()) {
            if (Objects.equals(weapon.getName(), name)) {
                inventory.removeWeapon(weapon);
                System.out.println("Weapon was removed.");
                return;
            }
        }
        System.out.println("There is no such weapon!");
    }

    // MODIFIES: this
    // EFFECTS: asks the user to choose type of buff to add
    private void addBuff() {
        System.out.println("Stat Buff or Weapon Buff?");
        System.out.println("1. Stat Buff");
        System.out.println("2. Weapon Buff");
        int choice = input.nextInt();
        if (choice == 1) {
            addStatBuff();
        } else if (choice == 2) {
            addWeaponBuff();
        } else {
            System.out.println("Not a valid choice");
        }

    }

    // MODIFIES: this
    // EFFECTS: asks for input to add a Stat Buff
    private void addStatBuff() {
        System.out.println("Give a name: ");
        input.nextLine();
        String name = input.nextLine();
        System.out.println("Give stat to buff: ");
        System.out.println("1. Health");
        System.out.println("2. Attack");
        System.out.println("3. Speed");
        int choice = input.nextInt();
        if (choice != 1 && choice != 2 && choice != 3) {
            System.out.println("Not a valid choice");
            return;
        }
        System.out.println("Give amount to buff by: ");
        updateStatBuff(choice, name);
    }

    // Helper for addStatBuff
    // REQUIRES: choice is between 1 and 3
    // MODiFIES: this
    // EFFECTS: adds stat buff to inventory, updates player stats
    private void updateStatBuff(int choice, String name) {
        int amount = input.nextInt();
        List<String> targets = new ArrayList<>();
        List<Integer> amounts = new ArrayList<>();
        Buff buff;
        if (choice == 1) {
            player.addMaxHealth(amount);
            targets.add("health");
        } else if (choice == 2) {
            player.addAttack(amount);
            targets.add("attack");
        } else if (choice == 3) {
            player.addSpeed(amount);
            targets.add("speed");
        }
        amounts.add(amount);
        buff = new StatBuff(name, targets, amounts);
        inventory.addBuff(buff);
    }

    // MODIFIES: this
    // EFFECTS: adds chosen amount to chosen weapon stat (currently only one choice)
    private void addWeaponBuff() {
        System.out.println("Give a name: ");
        input.nextLine();
        String name = input.nextLine();
        System.out.println("Give stat to buff: ");
        System.out.println("1. Fire Rate");
        int choice = input.nextInt();
        if (choice == 1) {
            System.out.println("Give amount to buff by: ");
            int amount = input.nextInt();
            for (Weapon weapon : inventory.getWeapons()) {
                int temp = weapon.getFirerate();
                weapon.setFirerate(temp + amount);
            }
            List<String> targets = new ArrayList<>();
            targets.add("firerate");
            List<Integer> amounts = new ArrayList<>();
            amounts.add(amount);
            Buff buff = new WeaponBuff(name, targets, amounts);
            inventory.addBuff(buff);
            return;
        }
        System.out.println("Not a valid choice");
    }

    // Doesn't differentiate between weapon and stat buffs, this is a problem and should be fixed once the game
    // is actually implemented.
    // MODIFIES: this
    // EFFECTS: removes the buff from inventory
    private void removeBuff() {
        System.out.println("Name the buff: ");
        input.nextLine();
        String name = input.nextLine();
        for (Buff buff : inventory.getBuffs()) {
            String bufftype = buff.getClass().getSimpleName();
            if (Objects.equals(buff.getName(), name) && bufftype.equals("StatBuff")) {
                removeStatBuff(buff);
                inventory.removeBuff(buff);
                return;
            } else if (Objects.equals(buff.getName(), name) && bufftype.equals("WeaponBuff")) {
                removeWeaponBuff(buff);
                inventory.removeBuff(buff);
                return;
            }
        }
        System.out.println("Not a valid choice");
    }

    // MODIFIES: this
    // EFFECTS: removes the stats from the stat buff from the player
    private void removeStatBuff(Buff buff) {
        List<String> targets = buff.getTargets();
        List<Integer> amounts = buff.getAmounts();
        for (int counter = 0; counter < targets.size(); counter++) {
            if (Objects.equals(targets.get(counter), "health")) {
                player.subMaxHealth(amounts.get(counter));
            } else if (Objects.equals(targets.get(counter), "attack")) {
                player.subAttack(amounts.get(counter));
            } else if (Objects.equals(targets.get(counter), "speed")) {
                player.subSpeed(amounts.get(counter));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the weapon buff effect from all weapons
    private void removeWeaponBuff(Buff buff) {
        List<String> targets = buff.getTargets();
        List<Integer> amounts = buff.getAmounts();
        for (int counter = 0; counter < targets.size(); counter++) {
            if (Objects.equals(targets.get(counter), "firerate")) {
                for (Weapon weapon : inventory.getWeapons()) {
                    int temp = weapon.getFirerate();
                    weapon.setFirerate(temp - amounts.get(counter));
                }
            }
        }
    }
}



