package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuffTest {
    private Buff testStatBuff;
    private Buff testWeaponBuff;

    @BeforeEach
    void runBefore() {
        List<String> targets = new ArrayList<>();
        List<Integer> amounts = new ArrayList<>();
        testStatBuff = new StatBuff("testStatBuff", targets, amounts);
    }

    @Test
    void testConstructor() {
        assertEquals("testStatBuff", testStatBuff.getName());
        assertEquals(0, testStatBuff.getTargets().size());
        assertEquals(0, testStatBuff.getAmounts().size());
    }

}