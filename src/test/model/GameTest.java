package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game g;

    @BeforeEach
    void runBefore() {
        g = new Game();
    }

    @Test
    void testConstructor() {
        assertEquals(0, g.getEnemies().size());
        assertEquals(0, g.getItems().size());
        assertEquals(0, g.getPlayerBullets().size());
        assertEquals(0, g.getEnemyBullets().size());
        assertEquals(0, g.getPlayerBullets().size());
        assertFalse(g.isOver());
        assertEquals(10, g.getPlayer().getMaxHealth());
        assertEquals(1, g.getPlayer().getBonusAttack());
        assertEquals(2, g.getPlayer().getSpeed());
        assertEquals(100, g.getPlayer().getFireRate());
        assertEquals(0, g.getPlayer().getInventory().getMoney());
        assertEquals(1, g.getPlayer().getInventory().getWeapons().size());
        assertEquals("SMALL", g.getPlayer().getInventory().getWeapons().get(0).getSize());
        assertEquals(0, g.getPlayer().getInventory().getBuffs().size());
    }

    @Test
    void testUpdate() {
        g.update();
        assertNotEquals(0, g.getEnemies().size());
        assertEquals(0, g.getEnemyBullets().size());
        assertEquals(0, g.getItems().size());
        assertEquals(800 / 2, g.getPlayer().getX());
        assertEquals(600-40, g.getPlayer().getY());
        assertFalse(g.isOver());
    }

    @Test
    void testHandleKey() {
        g.handleKey(KeyEvent.VK_SPACE);
        assertTrue(g.getKeysPressed().contains(KeyEvent.VK_SPACE));
        g.handleKey(KeyEvent.VK_LEFT);
        assertTrue(g.getKeysPressed().contains(KeyEvent.VK_LEFT));
        g.handleKey(KeyEvent.VK_RIGHT);
        assertTrue(g.getKeysPressed().contains(KeyEvent.VK_RIGHT));
        g.handleKey(KeyEvent.VK_UP);
        assertTrue(g.getKeysPressed().contains(KeyEvent.VK_UP));
        g.handleKey(KeyEvent.VK_DOWN);
        assertTrue(g.getKeysPressed().contains(KeyEvent.VK_DOWN));
    }

    @Test
    void testHandleKeyReleased() {
        g.handleKey(KeyEvent.VK_SPACE);
        g.handleKey(KeyEvent.VK_LEFT);
        g.handleKey(KeyEvent.VK_RIGHT);
        g.handleKey(KeyEvent.VK_UP);
        g.handleKey(KeyEvent.VK_DOWN);

        g.handleKeyReleased(KeyEvent.VK_SPACE);
        assertFalse(g.getKeysPressed().contains(KeyEvent.VK_SPACE));
        g.getPlayer().setXdir(1);
        g.handleKeyReleased(KeyEvent.VK_LEFT);
        assertFalse(g.getKeysPressed().contains(KeyEvent.VK_LEFT));
        g.handleKeyReleased(KeyEvent.VK_RIGHT);
        assertFalse(g.getKeysPressed().contains(KeyEvent.VK_RIGHT));
        g.getPlayer().setYdir(1);
        g.handleKeyReleased(KeyEvent.VK_UP);
        assertFalse(g.getKeysPressed().contains(KeyEvent.VK_UP));
        g.handleKeyReleased(KeyEvent.VK_DOWN);
        assertFalse(g.getKeysPressed().contains(KeyEvent.VK_DOWN));
    }

    @Test
    void testSaveLoad() {
        g.getPlayer().setX(100);
        g.saveGame();
        g.getPlayer().setX(200);
        g.loadGame();
        assertEquals(100, g.getPlayer().getX());
    }
}
