package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

// Inspiration from https://github.students.cs.ubc.ca/CPSC210/TellerApp

// Game application
public class Game {
    private static final String JSON_STORE = "./data/game.json";
    private Player player;
    private Inventory inventory;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: initializes the game
    public Game() {
        ArrayList<Integer> stats = new ArrayList<>(4);
        stats.add(10); //max health
        stats.add(1); //bonus attack
        stats.add(10); //speed
        stats.add(100); //fire rate (percentage)

        int experience = 0;
        int level = 1;

        int money = 0;
        ArrayList<Weapon> weapons = new ArrayList<>();
        ArrayList<Buff> buffs = new ArrayList<>();

        player = new Player(stats, experience, level, new Inventory(money, weapons, buffs));
        inventory = player.getInventory();

        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
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

            if (choice == 11) {
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
        System.out.println("9: save game");
        System.out.println("10: load game");
        System.out.println("11: quit\n");
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
        } else if (choice == 9) {
            saveGame();
        } else if (choice == 10) {
            loadGame();
        } else {
            System.out.println("Not a valid option!");
        }
    }

    // EFFECTS: prints out inventory
    private void viewInventory() {
        System.out.println("Your inventory: ");
        System.out.println("Money: " + inventory.getMoney());
        System.out.println("Weapons: ");
        for (Weapon weapon : inventory.getWeapons()) {
            System.out.println("Weapon Name: " + weapon.getName() + ", Fire Rate: " + weapon.getFireRate()
                    + ", Bullet type: coming soon...");
        }
        System.out.println("Buffs: ");
        for (Buff buff : inventory.getBuffs()) {
            System.out.println("Buff Name: " + buff.getName());
            ArrayList<Integer> modifiers = buff.getModifiers();
            for (int index = 0; index < modifiers.size(); index++) {
                if (index == 0) {
                    System.out.println("\tHealth modifier: " + modifiers.get(index));
                } else if (index == 1) {
                    System.out.println("\tAttack modifier: " + modifiers.get(index));
                } else if (index == 2) {
                    System.out.println("\tSpeed modifier: " + modifiers.get(index));
                } else if (index == 3) {
                    System.out.println("\tFire Rate modifier: " + modifiers.get(index));
                }
            }
        }
    }

    // EFFECTS: prints out player stats
    private void viewStats() {
        System.out.println("Your stats: ");
        System.out.println("Max Health: " + player.getMaxHealth());
        System.out.println("Bonus Attack: " + player.getBonusAttack());
        System.out.println("Speed: " + player.getSpeed());
        System.out.println("Fire Rate: " + player.getFireRate());
        System.out.println("Experience: " + player.getExperience());
        System.out.println("Level: " + player.getLevel());
    }

    // MODIFIES: this
    // EFFECTS: adds money to the inventory
    private void addMoney() {
        System.out.println("How much money to add?");
        inventory.addMoney(input.nextInt());
        System.out.println("Money was added.");
    }

    // MODIFIES: this
    // EFFECTS: removes money from the inventory
    private void removeMoney() {
        System.out.println("How much money to remove?");
        inventory.removeMoney(input.nextInt());
        System.out.println("Money was removed.");
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
    // EFFECTS: adds a buff with given modifiers to player's inventory, and updates player stats, prints message if
    // inventory is full.
    private void addBuff() {
        ArrayList<Integer> modifiers = new ArrayList<>();
        System.out.println("Give a name to the buff: ");
        input.nextLine();
        String name = input.nextLine();

        System.out.println("For the following, note that you can choose negative integers as well. However, "
                + "a stat cannot be decreased below 1.");
        System.out.println("Increase health by: ");
        modifiers.add(input.nextInt());
        System.out.println("Increase bonus attack by: ");
        modifiers.add(input.nextInt());
        System.out.println("Increase speed by: ");
        modifiers.add(input.nextInt());
        System.out.println("Increase fire rate by: ");
        modifiers.add(input.nextInt());

        if (player.updateStats(new Buff(name, modifiers))) {
            System.out.println("Buff: " + name + " was added to inventory.");
        } else {
            System.out.println("Buff was not added, you have reached the maximum number of buffs!");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the buff from inventory and updates player stats accordingly. If more than one buff with the
    // same name, remove the first one (when the game is implemented, all buffs with the same name would have the same
    // modifiers, so this wouldn't be a problem). If buff doesn't exist, print a message
    private void removeBuff() {
        System.out.println("Name the buff to be removed: ");
        input.nextLine();
        String name = input.nextLine();
        if (inventory.containsBuff(name)) {
            player.removeStats(inventory.getBuff(name));
            System.out.println("Buff was removed.");
            return;
        }
        System.out.println("There is no such buff!");
    }

    // EFFECTS: saves the game to file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(player);
            jsonWriter.close();
            System.out.println("Saved game to: " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads game from file
    private void loadGame() {
        try {
            player = jsonReader.read();
            inventory = player.getInventory();
            System.out.println("Loaded game from: " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}



