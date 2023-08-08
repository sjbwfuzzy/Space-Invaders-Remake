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
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGame.json");
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

            Enemy e1 = new Enemy("LARGE", 0, 0);
            Enemy e2 = new Enemy("SMALL", 1, 1);

            e1.setItem(null);
            e2.setItem(buff1);

            g.getEnemies().add(e1);
            g.getEnemies().add(e2);

            Bullet bullet = new Bullet("SMALL", true, 0, 0);
            g.getPlayerBullets().add(bullet);

            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGame.json");
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

            assertEquals("LARGE", g.getEnemies().get(0).getSize());
            assertEquals(40, g.getEnemies().get(0).getHealth());
            assertEquals(0, g.getEnemies().get(0).getX());
            assertEquals(0, g.getEnemies().get(0).getY());
            assertNull(g.getEnemies().get(0).getItem());

            assertEquals("SMALL", g.getEnemies().get(1).getSize());
            assertEquals(10, g.getEnemies().get(1).getHealth());
            assertEquals(1, g.getEnemies().get(1).getX());
            assertEquals(1, g.getEnemies().get(1).getY());
            assertEquals("Buff", g.getEnemies().get(1).getItem().getIdentifier());

            assertEquals("SMALL", g.getPlayerBullets().get(0).getSize());
            assertTrue(g.getPlayerBullets().get(0).isMybullet());
            assertEquals(0, g.getPlayerBullets().get(0).getX());
            assertEquals(0, g.getPlayerBullets().get(0).getY());
            assertEquals(0, g.getPlayerBullets().get(0).getXdirection());
            assertEquals(-1, g.getPlayerBullets().get(0).getYdirection());






        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
