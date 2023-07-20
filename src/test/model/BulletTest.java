package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BulletTest {
    private Bullet testBullet;

    @BeforeEach
    void runBefore() {
        testBullet = new Bullet(1, 2, 3, 4, true);
    }

    @Test
    void testConstructor() {
        assertEquals(1, testBullet.getDamage());
        assertEquals(2, testBullet.getSpeed());
        assertEquals(3, testBullet.getPenetration());
        assertEquals(4, testBullet.getRadius());
        assertTrue(testBullet.isMybullet());

    }

}