package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import ui.Game;

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
            assertEquals(0, g.getEnemyBullets().size());
            assertEquals(0, g.getPlayerBullets().size());
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
            assertEquals(0, g.getScore());
            assertEquals(10, p.getMaxHealth());
            assertEquals(1, p.getBonusAttack());
            assertEquals(2, p.getSpeed());
            assertEquals(100, p.getFireRate());
            assertEquals(0, p.getExperience());
            assertEquals(1, p.getLevel());
            assertEquals(0, p.getInventory().getMoney());

            Bullet eb = g.getEnemyBullets().get(0);
            assertEquals("SMALL", eb.getSize());
            assertFalse(eb.isMybullet());
            assertEquals(123, eb.getX());
            assertEquals(538, eb.getY());

            Bullet pb = g.getPlayerBullets().get(0);
            assertEquals("SMALL", pb.getSize());
            assertTrue(pb.isMybullet());
            assertEquals(400, pb.getX());
            assertEquals(254, pb.getY());

            Enemy e = g.getEnemies().get(0);
            assertEquals("SMALL", e.getSize());
            assertEquals(211, e.getX());
            assertEquals(30, e.getY());
            assertEquals(10, e.getHealth());

            Enemy e2 = g.getEnemies().get(1);
            assertNull(e2.getItem());

            Item i = e.getItem();
            assertEquals("Increase Movement Speed", i.getName());
            assertEquals("Buff", i.getIdentifier());
            assertEquals(0, i.getX());
            assertEquals(0, i.getY());

            Item i2 = g.getItems().get(0);
            assertEquals("Small Gun", i2.getName());
            assertEquals("Weapon", i2.getIdentifier());
            assertEquals(1, i2.getX());
            assertEquals(1, i2.getY());

            Buff buff1 = p.getInventory().getBuffs().get(0);
            assertEquals(1, p.getInventory().getBuffs().size());
            assertEquals("Increase Max Health", buff1.getName());
            assertEquals(3, buff1.getOneModifier(0));
            assertEquals(0, buff1.getOneModifier(1));
            assertEquals(0, buff1.getOneModifier(2));
            assertEquals(0, buff1.getOneModifier(3));

            Weapon weapon1 = p.getInventory().getWeapons().get(0);
            assertEquals(1, p.getInventory().getWeapons().size());
            assertEquals("Small Gun", weapon1.getName());
            assertEquals(3, weapon1.getFireRate());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
