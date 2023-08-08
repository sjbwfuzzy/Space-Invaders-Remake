package persistence;

// Inspiration from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads game data from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private Game parseGame(JSONObject jsonObject) {
        Game g = new Game();
        g.setPlayer(parsePlayer(jsonObject.getJSONObject("player")));
        g.setInventory(parseInventory(jsonObject.getJSONObject("inventory")));
        g.setScore(jsonObject.getInt("score"));
        g.setInvading(jsonObject.getBoolean("invading"));
        g.setLowest(jsonObject.getInt("lowest"));
        g.setLeftmost(jsonObject.getInt("leftmost"));
        g.setRightmost(jsonObject.getInt("rightmost"));
        g.setMovementUnits(jsonObject.getInt("movementUnits"));
        g.setEnemyBullets(parseEnemyBullets(jsonObject.getJSONArray("enemyBullets")));
        g.setPlayerBullets(parsePlayerBullets(jsonObject.getJSONArray("playerBullets")));
        g.setItems(parseItems(jsonObject.getJSONArray("items")));
        g.setEnemies(parseEnemies(jsonObject.getJSONArray("enemies")));
        g.setGameOver(jsonObject.getBoolean("isGameOver"));

        return g;
    }

    // EFFECTS: parses enemy from JSON object and returns it
    private Enemy parseEnemy(JSONObject jsonObject) {
        Enemy e = new Enemy(jsonObject.getString("size"), jsonObject.getInt("xpos"),
                jsonObject.getInt("ypos"));
        e.setHealth(jsonObject.getInt("health"));
        try {
            e.setItem(parseItem(jsonObject.getJSONObject("item")));

        } catch (Exception exception) {
            e.setItem(null);
        }
        return e;
    }

    // EFFECTS: parses enemies from JSON array and returns it
    private ArrayList<Enemy> parseEnemies(JSONArray jsonArray) {
        ArrayList<Enemy> e = new ArrayList<>();
        for (int index = 0; index < jsonArray.length(); index++) {
            e.add(parseEnemy(jsonArray.getJSONObject(index)));
        }
        return e;
    }

    // EFFECTS: parses items from JSON array and returns it
    private ArrayList<Item> parseItems(JSONArray jsonArray) {
        ArrayList<Item> i = new ArrayList<>();
        for (int index = 0; index < jsonArray.length(); index++) {
            i.add(parseItem(jsonArray.getJSONObject(index)));
        }
        return i;
    }

    // EFFECTS: parses JSONObject as either a buff or a weapon depending on the identifier
    private Item parseItem(JSONObject jsonObject) {
        if (Objects.equals(jsonObject.getString("identifier"), "Buff")) {
            return parseBuff(jsonObject);
        } else if (Objects.equals(jsonObject.getString("identifier"), "Weapon")) {
            return parseWeapon(jsonObject);
        }
        return null;
    }

    // EFFECTS: parses player bullets from JSON array and returns it
    private ArrayList<Bullet> parsePlayerBullets(JSONArray jsonArray) {
        ArrayList<Bullet> pb = new ArrayList<>();
        for (int index = 0; index < jsonArray.length(); index++) {
            pb.add(parseBullet(jsonArray.getJSONObject(index)));
        }
        return pb;
    }

    // EFFECTS: parses enemy bullets from JSON array and returns it
    private ArrayList<Bullet> parseEnemyBullets(JSONArray jsonArray) {
        ArrayList<Bullet> eb = new ArrayList<>();
        for (int index = 0; index < jsonArray.length(); index++) {
            eb.add(parseBullet(jsonArray.getJSONObject(index)));
        }
        return eb;
    }

    // EFFECTS: parses player from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("stats");
        ArrayList<Integer> stats = new ArrayList<>(4);
        for (int index = 0; index < jsonArray.length(); index++) {
            stats.add(jsonArray.getInt(index));
        }
        int experience = jsonObject.getInt("experience");
        int level = jsonObject.getInt("level");

        Inventory inventory = parseInventory(jsonObject.getJSONObject("inventory"));

        Player p = new Player(stats, experience, level, inventory, jsonObject.getInt("xpos"),
                jsonObject.getInt("ypos"));

        p.setHealth(jsonObject.getInt("health"));
        p.setXdir(jsonObject.getInt("xdir"));
        p.setYdir(jsonObject.getInt("ydir"));
        return p;
    }

    // EFFECTS: parses inventory from JSON object and returns it
    private Inventory parseInventory(JSONObject jsonObject) {
        int money = jsonObject.getInt("money");
        ArrayList<Weapon> weapons = parseWeapons(jsonObject.getJSONArray("weapons"));
        ArrayList<Buff> buffs = parseBuffs(jsonObject.getJSONArray("buffs"));

        return new Inventory(money, weapons, buffs);
    }

    // EFFECTS: parses list of weapons from JSONArray and returns it
    private ArrayList<Weapon> parseWeapons(JSONArray jsonArray) {
        ArrayList<Weapon> weapons = new ArrayList<>();
        for (int index = 0; index < jsonArray.length(); index++) {
            weapons.add(parseWeapon(jsonArray.getJSONObject(index)));
        }
        return weapons;
    }

    // EFFECTS: parses weapon from JSON object and returns it
    private Weapon parseWeapon(JSONObject jsonObject) {
        Weapon w = new Weapon(jsonObject.getString("size"), jsonObject.getInt("xpos"),
                jsonObject.getInt("ypos"));
        w.setIdentifier(jsonObject.getString("identifier"));
        return w;
    }

    // EFFECTS: parses bullet from JSON object and returns it
    private Bullet parseBullet(JSONObject jsonObject) {
        Bullet b = new Bullet(jsonObject.getString("size"), jsonObject.getBoolean("mybullet"),
                jsonObject.getInt("xpos"),
                jsonObject.getInt("ypos"));
        b.setDirection(jsonObject.getInt("xdir"), jsonObject.getInt("ydir"));
        return b;
    }

    // EFFECTS: parses list of buffs from JSONArray and returns it
    private ArrayList<Buff> parseBuffs(JSONArray jsonArray) {
        ArrayList<Buff> buffs = new ArrayList<>();
        for (int index = 0; index < jsonArray.length(); index++) {
            buffs.add(parseBuff(jsonArray.getJSONObject(index)));
        }
        return buffs;
    }

    // EFFECTS: parses buff from JSON object and returns it
    private Buff parseBuff(JSONObject jsonObject) {
        Buff b = new Buff(jsonObject.getInt("type"),
                jsonObject.getInt("xpos"), jsonObject.getInt("ypos"));
        b.setIdentifier(jsonObject.getString("identifier"));
        return b;
    }

}
