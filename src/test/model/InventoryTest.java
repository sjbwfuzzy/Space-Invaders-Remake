package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private Inventory testInventory;
    private Weapon w1;
    private Weapon w2;
    private Buff buff1;
    private Buff buff2;
    private Bullet b1;

    @BeforeEach
    void runBefore() {
        testInventory = new Inventory();
        buff1 = new Buff("buff1", new ArrayList<>());
        buff2 = new Buff("buff2", new ArrayList<>());
        b1 = new Bullet(1,2, 3, 4, true);
        w1 = new Weapon("w1", 5, b1);
        w2 = new Weapon("w2", 10, b1);

    }

    @Test
    void testConstructor() {
        assertEquals(0, testInventory.getMoney());
        assertEquals(0, testInventory.getBuffs().size());
        assertEquals(0, testInventory.getWeapons().size());
    }

    @Test
    void testAddMoney() {
        testInventory.addMoney(5);
        assertEquals(5, testInventory.getMoney());
        testInventory.addMoney(10);
        assertEquals(15, testInventory.getMoney());
    }

    @Test
    void testRemoveMoney() {
        testInventory.addMoney(15);

        testInventory.removeMoney(5);
        assertEquals(10, testInventory.getMoney());
        testInventory.removeMoney(10);
        assertEquals(0, testInventory.getMoney());
    }

    @Test
    void testAddWeapon() {
        assertTrue(testInventory.addWeapon(w1));
        assertEquals(1, testInventory.getWeapons().size());
        assertTrue(testInventory.getWeapons().contains(w1));

        assertTrue(testInventory.addWeapon(w2));
        assertEquals(2, testInventory.getWeapons().size());
        assertTrue(testInventory.getWeapons().contains(w2));

        assertTrue(testInventory.addWeapon(w2));
        assertFalse(testInventory.addWeapon(w2));
    }

    @Test
    void testRemoveWeapon() {
        assertFalse(testInventory.removeWeapon(w1));

        testInventory.addWeapon(w1);
        testInventory.addWeapon(w2);

        assertTrue(testInventory.removeWeapon(w1));
        assertEquals(1, testInventory.getWeapons().size());
        assertTrue(testInventory.getWeapons().contains(w2));

        assertTrue(testInventory.removeWeapon(w2));
        assertEquals(0, testInventory.getWeapons().size());
    }

    @Test
    void testAddBuff() {
        assertTrue(testInventory.addBuff(buff1));
        assertEquals(1, testInventory.getBuffs().size());
        assertTrue(testInventory.getBuffs().contains(buff1));

        assertTrue(testInventory.addBuff(buff2));
        assertEquals(2, testInventory.getBuffs().size());
        assertTrue(testInventory.getBuffs().contains(buff2));

        testInventory.addBuff(buff1);
        testInventory.addBuff(buff2);
        assertTrue(testInventory.addBuff(buff1));
        assertFalse(testInventory.addBuff(buff1));
    }

    @Test
    void testRemoveBuff() {
        assertFalse(testInventory.removeBuff(buff1));
        testInventory.addBuff(buff1);
        testInventory.addBuff(buff2);

        assertTrue(testInventory.removeBuff(buff1));
        assertEquals(1, testInventory.getBuffs().size());
        assertTrue(testInventory.getBuffs().contains(buff2));

        assertTrue(testInventory.removeBuff(buff2));
        assertEquals(0, testInventory.getBuffs().size());
    }

    @Test
    void testContainsBuff() {
        assertFalse(testInventory.containsBuff("buff1"));
        testInventory.addBuff(buff1);
        assertTrue(testInventory.containsBuff("buff1"));
        assertFalse(testInventory.containsBuff("buff2"));
    }

    @Test
    void testGetBuff() {
        testInventory.addBuff(buff1);
        testInventory.addBuff(buff2);
        ArrayList<Integer> testModifiers = new ArrayList<>();
        testModifiers.add(3);
        testModifiers.add(-1);
        testModifiers.add(-11);
        testModifiers.add(-20);
        testInventory.addBuff(new Buff("buff1", testModifiers));

        assertEquals(buff1, testInventory.getBuff("buff1"));
        assertEquals(buff2, testInventory.getBuff("buff2"));

        // this should never really be reached, I'm just adding this test for code coverage
        assertNotEquals(buff1, testInventory.getBuff("buff3"));
    }
}