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
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads player data from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Player read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayer(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
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

        return new Player(stats, experience, level, inventory);
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
        return new Weapon(jsonObject.getString("name"), jsonObject.getInt("fireRate"),
                parseBullet(jsonObject.getJSONObject("bulletType")));
    }

    // EFFECTS: parses bullet from JSON object and returns it
    private Bullet parseBullet(JSONObject jsonObject) {
        return new Bullet(jsonObject.getInt("damage"), jsonObject.getInt("speed"),
                jsonObject.getInt("penetration"),
                jsonObject.getInt("radius"), jsonObject.getBoolean("mybullet"));
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
        return new Buff(jsonObject.getString("name"), parseModifiers(jsonObject.getJSONArray("modifiers")));
    }

    // EFFECTS: parses list of modifiers (for a buff) from JSONArray and returns it
    private ArrayList<Integer> parseModifiers(JSONArray jsonArray) {
        ArrayList<Integer> modifiers = new ArrayList<>(4);
        for (int index = 0; index < jsonArray.length(); index++) {
            modifiers.add(jsonArray.getInt(index));
        }
        return modifiers;
    }
}
