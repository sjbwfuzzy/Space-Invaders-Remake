package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Inspiration from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriterTest {
    Game g;

    @BeforeEach
    void runBefore() {
        g = new Game();
    }

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterInitialGame() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterInitialGame.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterInitialGame.json");
            g = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGame() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterInitialGame.json");
            g.getPlayer().addLevel(10);
            g.getPlayer().addExperience(10);

            Buff buff1 = new Buff(0, 0, 0);
            buff1.setOneModifier(0, 1);
            buff1.setOneModifier(1, 2);
            buff1.setOneModifier(2, 3);
            buff1.setOneModifier(3, 4);

            g.getPlayer().updateStats(buff1);

            Weapon weapon1 = new Weapon("SMALL", 0, 0);

            g.getInventory().addWeapon(weapon1);

            g.getInventory().addMoney(10);

            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterInitialGame.json");
            g = reader.read();
            Player p = g.getPlayer();
            assertEquals(11, p.getMaxHealth());
            assertEquals(3, p.getBonusAttack());
            assertEquals(5, p.getSpeed());
            assertEquals(104, p.getFireRate());
            assertEquals(10, p.getExperience());
            assertEquals(11, p.getLevel());
            assertEquals(10, p.getInventory().getMoney());
            assertEquals(1, p.getInventory().getBuffs().size());
            assertEquals(2, p.getInventory().getWeapons().size());

            assertEquals(1, p.getInventory().getBuffs().size());
            assertEquals("Increase Max Health", buff1.getName());
            assertEquals(1, buff1.getOneModifier(0));
            assertEquals(2, buff1.getOneModifier(1));
            assertEquals(3, buff1.getOneModifier(2));
            assertEquals(4, buff1.getOneModifier(3));

            assertEquals(2, g.getInventory().getWeapons().size());
            assertEquals("Small Gun", weapon1.getName());
            assertEquals(3, weapon1.getFireRate());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
