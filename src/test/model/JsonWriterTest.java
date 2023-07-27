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
    Player p;

    @BeforeEach
    void runBefore() {
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

        p = new Player(stats, experience, level, new Inventory(money, weapons, buffs));
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
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterInitialGame.json");
            p = reader.read();
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
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGame() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterInitialGame.json");
            p.addLevel(10);
            p.addExperience(10);

            ArrayList<Integer> modifiers = new ArrayList<>(4);
            modifiers.add(1);
            modifiers.add(2);
            modifiers.add(3);
            modifiers.add(4);
            Buff buff1 = new Buff("buff1", modifiers);

            p.updateStats(buff1);

            Bullet bullet = new Bullet(1,2, 3, 4, true);
            Weapon weapon1 = new Weapon("weapon1", 10, bullet);

            p.getInventory().addWeapon(weapon1);

            p.getInventory().addMoney(10);

            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterInitialGame.json");
            p = reader.read();
            assertEquals(11, p.getMaxHealth());
            assertEquals(3, p.getBonusAttack());
            assertEquals(13, p.getSpeed());
            assertEquals(104, p.getFireRate());
            assertEquals(10, p.getExperience());
            assertEquals(11, p.getLevel());
            assertEquals(10, p.getInventory().getMoney());
            assertEquals(1, p.getInventory().getBuffs().size());
            assertEquals(1, p.getInventory().getWeapons().size());

            assertEquals(1, p.getInventory().getBuffs().size());
            assertEquals("buff1", buff1.getName());
            assertEquals(1, buff1.getOneModifier(0));
            assertEquals(2, buff1.getOneModifier(1));
            assertEquals(3, buff1.getOneModifier(2));
            assertEquals(4, buff1.getOneModifier(3));

            assertEquals(1, p.getInventory().getWeapons().size());
            assertEquals("weapon1", weapon1.getName());
            assertEquals(10, weapon1.getFireRate());

            assertEquals(4, bullet.getRadius());
            assertEquals(1, bullet.getDamage());
            assertEquals(3, bullet.getPenetration());
            assertEquals(2, bullet.getSpeed());
            assertTrue(bullet.isMybullet());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
