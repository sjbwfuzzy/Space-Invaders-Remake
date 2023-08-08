package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private Inventory i;
    private Weapon w1;
    private Weapon w2;
    private Buff buff1;
    private Buff buff2;

    @BeforeEach
    void runBefore() {
        int money = 0;
        ArrayList<Weapon> weapons = new ArrayList<>();
        ArrayList<Buff> buffs = new ArrayList<>();

        i = new Inventory(money, weapons, buffs);
        buff1 = new Buff(0, 0, 0);
        buff2 = new Buff(3, 1, 1);
        w1 = new Weapon("LARGE", 2, 2);
        w2 = new Weapon("SMALL", 3, 3);
    }

    @Test
    void testConstructor() {
        assertEquals(0, i.getMoney());
        assertEquals(0, i.getBuffs().size());
        assertEquals(0, i.getWeapons().size());
    }

    @Test
    void testAddMoney() {
        i.addMoney(5);
        assertEquals(5, i.getMoney());
        i.addMoney(10);
        assertEquals(15, i.getMoney());
    }

    @Test
    void testRemoveMoney() {
        i.addMoney(15);

        i.removeMoney(5);
        assertEquals(10, i.getMoney());
        i.removeMoney(10);
        assertEquals(0, i.getMoney());
    }

    @Test
    void testAddWeapon() {
        assertTrue(i.addWeapon(w1));
        assertEquals(1, i.getWeapons().size());
        assertTrue(i.getWeapons().contains(w1));

        assertTrue(i.addWeapon(w2));
        assertEquals(2, i.getWeapons().size());
        assertTrue(i.getWeapons().contains(w2));

        assertTrue(i.addWeapon(w2));
        assertFalse(i.addWeapon(w2));
    }

    @Test
    void testRemoveWeapon() {
        assertFalse(i.removeWeapon(w1));

        i.addWeapon(w1);
        i.addWeapon(w2);

        assertTrue(i.removeWeapon(w1));
        assertEquals(1, i.getWeapons().size());
        assertTrue(i.getWeapons().contains(w2));

        assertTrue(i.removeWeapon(w2));
        assertEquals(0, i.getWeapons().size());
    }

    @Test
    void testAddBuff() {
        assertTrue(i.addBuff(buff1));
        assertEquals(1, i.getBuffs().size());
        assertTrue(i.getBuffs().contains(buff1));

        assertTrue(i.addBuff(buff2));
        assertEquals(2, i.getBuffs().size());
        assertTrue(i.getBuffs().contains(buff2));

        i.addBuff(buff1);
        i.addBuff(buff2);
        assertTrue(i.addBuff(buff1));
        assertFalse(i.addBuff(buff1));
    }

    @Test
    void testRemoveBuff() {
        assertFalse(i.removeBuff(buff1));
        i.addBuff(buff1);
        i.addBuff(buff2);

        assertTrue(i.removeBuff(buff1));
        assertEquals(1, i.getBuffs().size());
        assertTrue(i.getBuffs().contains(buff2));

        assertTrue(i.removeBuff(buff2));
        assertEquals(0, i.getBuffs().size());
    }

    @Test
    void testContainsBuff() {
        assertFalse(i.containsBuff("Increase Max Health"));
        i.addBuff(buff1);
        assertTrue(i.containsBuff("Increase Max Health"));
        assertEquals(1, i.getBuffs().size());
    }

    @Test
    void testContainsWeapon() {
        assertFalse(i.containsWeapon("Small Gun"));
        i.addWeapon(w2);
        assertTrue(i.containsWeapon("Small Gun"));
        assertEquals(1, i.getWeapons().size());
    }

    @Test
    void testGetBuff() {
        i.addBuff(buff1);
        i.addBuff(buff2);
        assertEquals(buff1, i.getBuff("Increase Max Health"));
        assertEquals(buff2, i.getBuff("Increase Fire Rate"));

        // this should never really be reached, I'm just adding this test for code coverage
        assertNotEquals(buff1, i.getBuff("buff3"));
    }
}