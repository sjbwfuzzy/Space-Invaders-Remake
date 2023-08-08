package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnemyTest {
    private Enemy testEnemy;

    @BeforeEach
    void runBefore() {
        testEnemy = new Enemy(Size.SMALL, 1, 1);
    }

    @Test
    void testConstructor() {
        testEnemy.reduceHealth(1);
        assertEquals(10, testEnemy.getScore());
    }
}
