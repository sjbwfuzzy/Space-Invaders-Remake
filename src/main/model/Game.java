package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.awt.event.KeyEvent;
import java.util.*;

// inspiration from https://github.students.cs.ubc.ca/CPSC210/B02-SpaceInvadersBase

// Represents a game
public class Game implements Writable {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private List<Bullet> enemyBullets;
    private List<Bullet> playerBullets;
    private List<Item> items;
    private List<Enemy> enemies;
    private Player player;
    private boolean isGameOver;
    private Inventory inventory;
    private int score;
    private Boolean invading;

    // used for enemy related actions
    private int lowest;
    private int leftmost;
    private int rightmost;
    private int movementUnits;

    // MODIFIES: this
    // EFFECTS: initializes game
    public Game() {
        enemyBullets = new ArrayList<>();
        playerBullets = new ArrayList<>();
        items = new ArrayList<>();
        enemies = new ArrayList<>();
        setUp();
    }

    // MODIFIES: this
    // EFFECTS:  clears list of bullets and enemies, initializes player
    private void setUp() {
        enemies.clear();
        enemyBullets.clear();
        isGameOver = false;
        invading = false;
        movementUnits = 1;
        score = 0;

        ArrayList<Integer> stats = new ArrayList<>(4);
        stats.add(10); //max health
        stats.add(0); //bonus attack
        stats.add(1); //speed
        stats.add(100); //fire rate (percentage)

        int experience = 0;
        int level = 1;

        int money = 0;
        ArrayList<Weapon> weapons = new ArrayList<>();
        weapons.add(new Weapon("SMALL", 0, 0));
        ArrayList<Buff> buffs = new ArrayList<>();

        player = new Player(stats, experience, level, new Inventory(money, weapons, buffs), WIDTH / 2, HEIGHT - 40);
        inventory = player.getInventory();
    }

    public boolean isOver() {
        return isGameOver;
    }

    public int getNumBullets() {
        return enemyBullets.size();
    }

    public int getScore() {
        return score;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    public List<Bullet> getPlayerBullets() {
        return playerBullets;
    }

    public Player getPlayer() {
        return player;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setEnemyBullets(List<Bullet> enemyBullets) {
        this.enemyBullets = enemyBullets;
    }

    public void setPlayerBullets(List<Bullet> playerBullets) {
        this.playerBullets = playerBullets;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setInvading(Boolean invading) {
        this.invading = invading;
    }

    public void setLowest(int lowest) {
        this.lowest = lowest;
    }

    public void setLeftmost(int leftmost) {
        this.leftmost = leftmost;
    }

    public void setRightmost(int rightmost) {
        this.rightmost = rightmost;
    }

    public void setMovementUnits(int movementUnits) {
        this.movementUnits = movementUnits;
    }

    // MODIFIES: this
    // EFFECTS:  updates player, bullets and enemies if there are still enemies, else do the invading sequence
    public void update() {
        if (enemies.size() == 0) {
            invading = true;
            setupEnemies();
        }
        if (!invading) {
            enemyFire();
            moveBullets();
            moveEnemies();
            moveItems();
            player.move();

            handleBoundary();

            checkCollisions();
            checkCollisionItems();
            updateBounds(); // probably poorly implemented...
            checkGameOver();
        } else {
            moveEnemies();
        }
    }


    // MODIFIES: this
    // EFFECTS:  turns player, fires bullets, depending on the keycode
    public void handleKey(Set<Integer> keyCodes) {
        if (keyCodes.contains(KeyEvent.VK_SPACE)) {
            fireBullet();
        } else {
            playerControl(keyCodes);
        }
    }

    // Controls the tank
    // modifies: this
    // effects: turns tank in response to key code 
    private void playerControl(Set<Integer> keyCodes) {
        if (keyCodes.contains(KeyEvent.VK_LEFT)) {
            player.faceLeft();
        }
        if (keyCodes.contains(KeyEvent.VK_RIGHT)) {
            player.faceRight();
        }
        if (keyCodes.contains(KeyEvent.VK_UP)) {
            player.faceUp();
        }
        if (keyCodes.contains(KeyEvent.VK_DOWN)) {
            player.faceDown();
        }
//        } else {
//            player.resetDirection();
//        }
    }


    // MODIFIES: this
    // EFFECTS: for each weapon in inventory, if the weapon can fire, fire, set canFire to false, and restart the timer.
    public void fireBullet() {
        for (Weapon w : inventory.getWeapons()) {
            if (w.canFire()) {
                playerBullets.add(new Bullet(w.getSize(), true, player.getX(), player.getY()));
                w.setCanFire(false);
                w.restartTimer();
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: updates leftmost and rightmost values
    private void updateBounds() {
        int min = 800;
        int max = 0;
        for (Enemy e : enemies) {
            int ex = e.getX();
            if (ex > max) {
                max = ex;
            }
            if (ex < min) {
                min = ex;
            }
        }
        rightmost = max;
        leftmost = min;
    }

    // MODIFIES: this
    // EFFECTS: if player health is less than 0, set isGameOver to true
    private void checkGameOver() {
        if (player.getHealth() <= 0) {
            isGameOver = true;
        }
        if (isGameOver) {
            enemies.clear();
            enemyBullets.clear();
            playerBullets.clear();
            items.clear();
        }
    }

    // MODIFIES: this
    // EFFECTS: if the enemy has an item, set the coordinates of the item to the enemy's coordinates and add it to items
    private void dropItem(Enemy e) {
        Item item = e.getItem();
        if (item == null) {
            return;
        } else {
            item.setX(e.getX());
            item.setY(e.getY());
            items.add(item);
        }
    }

    // MODIFIES: this
    // EFFECTS: checks for collisions, updates player and enemy health accordingly. If enemy dies and it has an item,
    // drop it, then remove enemy from the game.
    private void checkCollisions() {
        for (Bullet bullet : enemyBullets) {
            if (bullet.collidedWith(player)) {
                player.updateHealth(-bullet.getDamage());
            }
        }
        for (Bullet bullet : playerBullets) {
            for (Enemy enemy : enemies) {
                if (bullet.collidedWith(enemy)) {
                    enemy.reduceHealth(bullet.getDamage());
                    if (enemy.getHealth() <= 0) {
                        dropItem(enemy);
                        score += enemy.getScore();
                        enemies.remove(enemy);
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: if any item has collided with player, add it to corresponding list in inventory.
    private void checkCollisionItems() {
        for (Item item : items) {
            if (item.collidedWith(player)) {
                if (item.getIdentifier() == "Buff") {
                    Buff b = (Buff) item;
                    inventory.addBuff(b);
                    player.updateStats(b);
                    if (b.getType() == 3) {
                        updateWeaponTimers();
                    }
                } else {
                    Weapon w = (Weapon) item;
                    w.setTimer(calcTimerDelay(w));
                    inventory.addWeapon((Weapon) item);
                }
            }
        }
    }

    // EFFECTS: re-initializes all weapon timers with correct timer delay.
    private void updateWeaponTimers() {
        for (Weapon w : inventory.getWeapons()) {
            w.setTimer(calcTimerDelay(w));
        }
    }

    // EFFECTS: calculates the correct timer delay given a weapon (and using the player's firerate bonus)
    private int calcTimerDelay(Weapon w) {
        return (int) ((1.0 / (w.getFireRate() * (player.getFireRate() / 100.0))) * 1000);
    }

    // MODIFIES: this
    // EFFECTS: chooses a random enemy to fire a bullet every update if not invading.
    private void enemyFire() {
        int num = new Random().nextInt(enemies.size());
        Enemy chosen = enemies.get(num);
        int x = chosen.getX();
        int y = chosen.getY();
        Bullet b = new Bullet(chosen.getSize(), false, x, y);
        b.setDirection(0, 1);
        enemyBullets.add(b);
    }

    // MODIFIES: this
    // EFFECTS: moves the bullets
    private void moveBullets() {
        for (Bullet b : enemyBullets) {
            b.move();
        }
        for (Bullet b : playerBullets) {
            b.move();
        }
    }

    // MODIFIES: this
    // EFFECTS: moves the items
    private void moveItems() {
        for (Item item : items) {
            item.move();
        }
    }

    // REQUIRES: enemies.size() == 0
    // MODIFIES: this
    // EFFECTS: sets up a wave of enemies, gives a random item to some of them to hold. Clears all bullets afterwards.
    private void setupEnemies() {
        for (int y = -230; y != -20; y += 70) {
            for (int x = 80; x != 720; x += 80) {
                int type = new Random().nextInt(3);
                addEnemy(type, x, y);
            }
        }
        for (int counter = 0; counter < 3; counter++) {
            int num = new Random().nextInt(enemies.size());
            Enemy chosen = enemies.get(num);
            chosen.setItem(randomItem());
        }
        lowest = -20;
        leftmost = 80;
        rightmost = 720;
        playerBullets.clear();
        enemyBullets.clear();
    }

    // EFFECTS: returns a random item
    private Item randomItem() {
        int num = new Random().nextInt(2);
        if (num == 0) {
            return new Buff(new Random().nextInt(4), 0, 0);
        } else {
            List<String> temp = new ArrayList<>();
            Collections.addAll(temp, "SMALL", "MEDIUM", "LARGE");
            return new Weapon(temp.get(new Random().nextInt(temp.size())), 0, 0);
        }
    }

    // REQUIRES: type is 0, 1, or 2
    // MODIFIES: this
    // EFFECTS: adds enemies to game in blocks.
    private void addEnemy(int type, int xpos, int ypos) {
        switch (type) {
            case 0:
                for (int y = ypos - 20; y <= ypos + 20; y += 20) {
                    for (int x = xpos - 25; x <= xpos + 25; x += 25) {
                        enemies.add(new Enemy("SMALL", x, y));
                    }
                }
                break;
            case 1:
                for (int y = xpos - 15; y <= xpos + 15; y += 30) {
                    for (int x = ypos - 18; x <= ypos + 18; x += 36) {
                        enemies.add(new Enemy("MEDIUM", x, y));
                    }
                }
                System.out.println("a;lsdkjf");
                break;
            case 2:
                enemies.add(new Enemy("LARGE", xpos, ypos));
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: moves the enemies in a pattern, different pattern if invading
    private void moveEnemies() {
        if (invading) {
            for (Enemy enemy : enemies) {
                enemy.updateY(1);
            }
            lowest += 1;
            if (lowest >= 230) {
                invading = false;
            }
        } else {
            if (rightmost > 770 || leftmost < 30) {
                movementUnits = -movementUnits;
            } else {
                for (Enemy enemy : enemies) {
                    enemy.updateX(movementUnits);
                }
                rightmost += movementUnits;
                leftmost += movementUnits;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: handles bullets or items exiting boundary
    private void handleBoundary() {
        boundaryBullets();
        boundaryItems();
    }

    // MODIFIES: this
    // EFFECTS: removes any bullet that has travelled off the screen
    private void boundaryBullets() {
        List<Bullet> bulletsToRemove = new ArrayList<>();
        for (Bullet b : enemyBullets) {
            if (b.getY() < 0
                    || b.getY() > HEIGHT
                    || b.getX() < 0
                    || b.getX() > WIDTH) {
                bulletsToRemove.add(b);
            }
        }
        enemyBullets.removeAll(bulletsToRemove);
        bulletsToRemove = new ArrayList<>();
        for (Bullet b : playerBullets) {
            if (b.getY() < 0
                    || b.getY() > HEIGHT
                    || b.getX() < 0
                    || b.getX() > WIDTH) {
                bulletsToRemove.add(b);
            }
        }
        playerBullets.removeAll(bulletsToRemove);
    }

    // MODIFIES: this
    // EFFECTS: removes any item that has travelled off-screen
    private void boundaryItems() {
        List<Item> itemsToRemove = new ArrayList<>();
        for (Item i : items) {
            if (i.getY() > HEIGHT) {
                itemsToRemove.add(i);
            }
        }
        items.removeAll(itemsToRemove);
    }


    @Override
    // EFFECTS: returns game as json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("player", player.toJson());
        json.put("inventory", inventory.toJson());
        json.put("invading", invading);
        json.put("lowest", lowest);
        json.put("leftmost", leftmost);
        json.put("rightmost", rightmost);
        json.put("movementUnits", movementUnits);
        json.put("enemyBullets", enemyBulletsToJson());
        json.put("playerBullets", playerBulletsToJson());
        json.put("items", itemsToJson());
        json.put("enemies", enemiesToJson());
        return json;
    }

    // EFFECTS: returns enemy bullets as a JSON array
    private JSONArray enemyBulletsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Bullet b : enemyBullets) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns player bullets as a JSON array
    private JSONArray playerBulletsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Bullet b : playerBullets) {
            jsonArray.put(b.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns items as a JSON array
    private JSONArray itemsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Item i : items) {
            jsonArray.put(i.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns enemies as a JSON array
    private JSONArray enemiesToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Enemy e : enemies) {
            jsonArray.put(e.toJson());
        }
        return jsonArray;
    }

}
