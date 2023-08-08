package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Inspiration from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Game g = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderInitialGame() {
        JsonReader reader = new JsonReader("./data/testReaderInitialGame.json");

        try {
            Game g = reader.read();
            Player p = g.getPlayer();
            assertEquals(10, p.getMaxHealth());
            assertEquals(1, p.getBonusAttack());
            assertEquals(2, p.getSpeed());
            assertEquals(100, p.getFireRate());
            assertEquals(0, p.getExperience());
            assertEquals(1, p.getLevel());
            assertEquals(0, p.getInventory().getMoney());
            assertEquals(0, p.getInventory().getBuffs().size());
            assertEquals(1, p.getInventory().getWeapons().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGame.json");
        try {
            Game g = reader.read();
            Player p = g.getPlayer();
            assertEquals(13, p.getMaxHealth());
            assertEquals(1, p.getBonusAttack());
            assertEquals(2, p.getSpeed());
            assertEquals(100, p.getFireRate());
            assertEquals(0, p.getExperience());
            assertEquals(1, p.getLevel());
            assertEquals(150, p.getInventory().getMoney());

            Buff buff1 = p.getInventory().getBuffs().get(0);
            assertEquals(1, p.getInventory().getBuffs().size());
            assertEquals("Increase Max Health", buff1.getName());
            assertEquals(3, buff1.getOneModifier(0));
            assertEquals(0, buff1.getOneModifier(1));
            assertEquals(0, buff1.getOneModifier(2));
            assertEquals(0, buff1.getOneModifier(3));

            Weapon weapon1 = p.getInventory().getWeapons().get(1);
            assertEquals(2, p.getInventory().getWeapons().size());
            assertEquals("Medium Gun", weapon1.getName());
            assertEquals(2, weapon1.getFireRate());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
