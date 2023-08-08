package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeaponTest {
    private Weapon testWeapon;
    private Bullet b1;

    @BeforeEach
    void runBefore() {
        testWeapon = new Weapon("SMALL", 0, 0);
    }

    @Test
    void testConstructor() {
        assertTrue(testWeapon.canFire());
        assertEquals(3, testWeapon.getFireRate());
        assertEquals("Small Gun", testWeapon.getName());
        assertEquals("SMALL", testWeapon.getSize());
        assertEquals("Weapon", testWeapon.getIdentifier());
        assertEquals(3, testWeapon.getFireRate());
        testWeapon.setCanFire(false);
        assertFalse(testWeapon.canFire());
    }

    @Test
    void testSetTimer() {
        assertEquals(100000000, testWeapon.getTimer().getDelay());
        testWeapon.setTimer(100);
        assertEquals(100, testWeapon.getTimer().getDelay());
    }

}