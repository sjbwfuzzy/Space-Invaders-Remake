package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player testPlayer;

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

        Inventory inventory = new Inventory(money, weapons, buffs);
        testPlayer = new Player(stats, experience, level, inventory);
    }

    @Test
    void testConstructor() {
        assertEquals(10, testPlayer.getMaxHealth());
        assertEquals(1, testPlayer.getBonusAttack());
        assertEquals(10, testPlayer.getSpeed());
        assertEquals(100, testPlayer.getFireRate());

        assertEquals(0, testPlayer.getExperience());
        assertEquals(1, testPlayer.getLevel());
    }

    @Test
    void testUpdateStats() {
        ArrayList<Integer> testModifiers = new ArrayList<>();
        testModifiers.add(3);
        testModifiers.add(-1);
        testModifiers.add(-11);
        testModifiers.add(-20);
        Buff testBuff = new Buff("test", testModifiers);
        assertTrue(testPlayer.updateStats(testBuff));
        assertEquals(13, testPlayer.getMaxHealth());
        assertEquals(1, testPlayer.getBonusAttack());
        assertEquals(1, testPlayer.getSpeed());
        assertEquals(80, testPlayer.getFireRate());

        assertEquals(3, testBuff.getOneModifier(0));
        assertEquals(0, testBuff.getOneModifier(1));
        assertEquals(-9, testBuff.getOneModifier(2));
        assertEquals(-20, testBuff.getOneModifier(3));

        assertEquals(1, testPlayer.getInventory().getBuffs().size());
        assertEquals(testBuff, testPlayer.getInventory().getBuffs().get(0));

        testPlayer.updateStats(testBuff);
        testPlayer.updateStats(testBuff);
        testPlayer.updateStats(testBuff);
        assertTrue(testPlayer.updateStats(testBuff));
        assertFalse(testPlayer.updateStats(testBuff));

    }

    @Test
    void testRemoveStats() {
        ArrayList<Integer> testModifiers = new ArrayList<>();
        testModifiers.add(3);
        testModifiers.add(-1);
        testModifiers.add(-11);
        testModifiers.add(-20);
        Buff testBuff = new Buff("test", testModifiers);
        testPlayer.updateStats(testBuff);
        assertTrue(testPlayer.removeStats(testBuff));

        assertEquals(10, testPlayer.getMaxHealth());
        assertEquals(1, testPlayer.getBonusAttack());
        assertEquals(10, testPlayer.getSpeed());
        assertEquals(100, testPlayer.getFireRate());

        assertEquals(0, testPlayer.getInventory().getBuffs().size());

        assertFalse(testPlayer.removeStats(new Buff("asdf", new ArrayList<>())));
    }

    @Test
    void addExperience() {
        testPlayer.addExperience(5);
        assertEquals(5, testPlayer.getExperience());
    }

    @Test
    void subExperience() {
        testPlayer.addExperience(5);
        testPlayer.subExperience(4);
        assertEquals(1, testPlayer.getExperience());
    }

    @Test
    void addLevel() {
        testPlayer.addLevel(5);
        assertEquals(6, testPlayer.getLevel());
    }

    @Test
    void subLevel() {
        testPlayer.addLevel(5);
        testPlayer.subLevel(4);
        assertEquals(2, testPlayer.getLevel());
    }
}