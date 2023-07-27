package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WeaponTest {
    private Weapon testWeapon;
    private Bullet b1;

    @BeforeEach
    void runBefore() {
        b1 = new Bullet(1, 2, 3, 4, true);
        testWeapon = new Weapon("w1", 55, b1);
    }

    @Test
    void testConstructor() {
        assertEquals(55, testWeapon.getFireRate());
        assertEquals("w1", testWeapon.getName());
        assertEquals(b1, testWeapon.getBulletType());
    }

    @Test
    void testSetFirerate() {
        testWeapon.setFireRate(55);
        assertEquals(55, testWeapon.getFireRate());
    }

}