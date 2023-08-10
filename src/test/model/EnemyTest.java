package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnemyTest {
    private Enemy te;

    @BeforeEach
    void runBefore() {
        te = new Enemy("SMALL", 0, 0);
    }

    @Test
    void testConstructor() {
        assertEquals("SMALL", te.getSize());
        assertEquals(0, te.getX());
        assertEquals(0, te.getY());
        assertEquals(5, te.getScore());
    }

    @Test
    void testUpdateX() {
        te.updateX(1);
        assertEquals(1, te.getX());
    }

    @Test
    void testHealth() {
        te.setHealth(7);
        assertEquals(7, te.getHealth());
        te.reduceHealth(1);
        assertEquals(6, te.getHealth());
    }
}
