package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player testPlayer;

    @BeforeEach
    void runBefore() {
        testPlayer = new Player();
    }

    @Test
    void testConstructor() {
        assertEquals(10, testPlayer.getMaxHealth());
        assertEquals(1, testPlayer.getAttack());
        assertEquals(1, testPlayer.getSpeed());
        assertEquals(0, testPlayer.getExperience());
        assertEquals(1, testPlayer.getLevel());
    }

    @Test
    void addMaxHealth() {
        testPlayer.addMaxHealth(5);
        assertEquals(15, testPlayer.getMaxHealth());
    }

    @Test
    void subMaxHealth() {
        testPlayer.subMaxHealth(5);
        assertEquals(5, testPlayer.getMaxHealth());
    }

    @Test
    void addAttack() {
        testPlayer.addAttack(5);
        assertEquals(6, testPlayer.getAttack());
    }

    @Test
    void subAttack() {
        testPlayer.addAttack(5);
        testPlayer.subAttack(4);
        assertEquals(2, testPlayer.getAttack());
    }

    @Test
    void addSpeed() {
        testPlayer.addSpeed(5);
        assertEquals(6, testPlayer.getSpeed());
    }

    @Test
    void subSpeed() {
        testPlayer.addSpeed(5);
        testPlayer.subSpeed(4);
        assertEquals(2, testPlayer.getSpeed());
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