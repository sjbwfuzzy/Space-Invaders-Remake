package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Inspiration from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Player p = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderInitialGame() {
        JsonReader reader = new JsonReader("./data/testReaderInitialGame.json");
        try {
            Player p = reader.read();
            assertEquals(10, p.getMaxHealth());
            assertEquals(1, p.getBonusAttack());
            assertEquals(10, p.getSpeed());
            assertEquals(100, p.getFireRate());
            assertEquals(0, p.getExperience());
            assertEquals(1, p.getLevel());
            assertEquals(0, p.getInventory().getMoney());
            assertEquals(0, p.getInventory().getBuffs().size());
            assertEquals(0, p.getInventory().getWeapons().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGame.json");
        try {
            Player p = reader.read();
            assertEquals(11, p.getMaxHealth());
            assertEquals(3, p.getBonusAttack());
            assertEquals(13, p.getSpeed());
            assertEquals(104, p.getFireRate());
            assertEquals(0, p.getExperience());
            assertEquals(1, p.getLevel());
            assertEquals(150, p.getInventory().getMoney());

            Buff buff1 = p.getInventory().getBuffs().get(0);
            assertEquals(1, p.getInventory().getBuffs().size());
            assertEquals("buff1", buff1.getName());
            assertEquals(1, buff1.getOneModifier(0));
            assertEquals(2, buff1.getOneModifier(1));
            assertEquals(3, buff1.getOneModifier(2));
            assertEquals(4, buff1.getOneModifier(3));

            Weapon weapon1 = p.getInventory().getWeapons().get(0);
            assertEquals(1, p.getInventory().getWeapons().size());
            assertEquals("weapon1", weapon1.getName());
            assertEquals(15, weapon1.getFireRate());

            Bullet bullet = weapon1.getBullet();
            assertEquals(1, bullet.getRadius());
            assertEquals(1, bullet.getDamage());
            assertEquals(1, bullet.getPenetration());
            assertEquals(1, bullet.getSpeed());
            assertTrue(bullet.isMybullet());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
