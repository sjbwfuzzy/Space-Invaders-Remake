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
    }
}
