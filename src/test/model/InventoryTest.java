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
    private StatBuff sb1;
    private WeaponBuff wb1;
    private Bullet b1;

    @BeforeEach
    void runBefore() {
        testInventory = new Inventory();
        List<String> targets = new ArrayList<>();
        List<Integer> amounts = new ArrayList<>();
        sb1 = new StatBuff("sb1", targets, amounts);
        wb1 = new WeaponBuff("wb1", targets, amounts);
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
        assertTrue(testInventory.addBuff(sb1));
        assertEquals(1, testInventory.getBuffs().size());
        assertTrue(testInventory.getBuffs().contains(sb1));

        assertTrue(testInventory.addBuff(wb1));
        assertEquals(2, testInventory.getBuffs().size());
        assertTrue(testInventory.getBuffs().contains(wb1));

        testInventory.addBuff(sb1);
        testInventory.addBuff(wb1);
        assertTrue(testInventory.addBuff(sb1));
        assertFalse(testInventory.addBuff(sb1));
    }

    @Test
    void testRemoveBuff() {
        assertFalse(testInventory.removeBuff(sb1));
        testInventory.addBuff(sb1);
        testInventory.addBuff(wb1);

        assertTrue(testInventory.removeBuff(sb1));
        assertEquals(1, testInventory.getBuffs().size());
        assertTrue(testInventory.getBuffs().contains(wb1));

        assertTrue(testInventory.removeBuff(wb1));
        assertEquals(0, testInventory.getBuffs().size());
    }
}