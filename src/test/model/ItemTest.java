package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    Item buff = new Buff(0, 0, 0);
    Item weapon = new Weapon("SMALL", 0, 0);

    @Test
    void testConstructor() {
        assertEquals(0, buff.getX());
        assertEquals(0, buff.getY());
        assertEquals("Buff", buff.getIdentifier());
        assertEquals("Increase Max Health", buff.getName());
        weapon.setX(1);
        weapon.setY(1);
        assertEquals(1, weapon.getX());
        assertEquals(1, weapon.getY());
    }

    @Test
    void testMove() {
        buff.move();
        assertEquals(2, buff.getY());
    }

    @Test
    void testCollidedWith() {
        ArrayList<Integer> stats = new ArrayList<>(4);
        stats.add(10); //max health
        stats.add(1); //bonus attack
        stats.add(10); //speed
        stats.add(100); //fire rate (percentage)
        Player p = new Player(stats, 0, 0,
                new Inventory(0, new ArrayList<>(), new ArrayList<>()),
                0, 0);
        assertTrue(buff.collidedWith(p));
        p.setX(100);
        assertFalse(buff.collidedWith(p));
    }
}
