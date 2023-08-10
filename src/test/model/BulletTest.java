package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BulletTest {
    private Bullet tb;

    @BeforeEach
    void runBefore() {
        tb = new Bullet("SMALL", true, 0, 0);
    }

    @Test
    void testConstructor() {
        assertEquals(1, tb.getDamage());
        assertEquals(6, tb.getSpeed());
        assertEquals(4, tb.getRadius());
        assertTrue(tb.isMybullet());
    }

    @Test
    void testMove() {
        tb.setXdirection(0);
        tb.setYdirection(1);
        tb.move();
        assertEquals(0, tb.getX());
        assertEquals(6, tb.getY());
    }

    @Test
    void testCollidedWithEnemy() {
        Enemy e = new Enemy("LARGE", 0, 0);
        assertTrue(tb.collidedWith(e));
        e = new Enemy("SMALL", 100, 100);
        assertFalse(tb.collidedWith(e));
        e = new Enemy("SMALL", 0, 0);
        tb = new Bullet("MEDIUM", false, 0, 0);
        assertFalse(tb.collidedWith(e));
    }

    @Test
    void testCollidedWithPlayer() {
        ArrayList<Integer> stats = new ArrayList<>(4);
        stats.add(10); //max health
        stats.add(1); //bonus attack
        stats.add(10); //speed
        stats.add(100); //fire rate (percentage)
        Player p = new Player(stats, 1, 1,
                new Inventory(1, new ArrayList<>(), new ArrayList<>()), 0, 0);
        tb = new Bullet("LARGE", false, 0, 0);
        assertTrue(tb.collidedWith(p));
        p.setX(100);
        assertFalse(tb.collidedWith(p));
        p.setX(0);
        tb = new Bullet("MEDIUM", true, 0, 0);
        assertFalse(tb.collidedWith(p));
    }

}