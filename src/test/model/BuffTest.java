package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BuffTest {
    private Buff testBuff;

    @BeforeEach
    void runBefore() {
        ArrayList<Integer> modifiers = new ArrayList<>();
        modifiers.add(3);
        modifiers.add(-1);
        modifiers.add(-11);
        modifiers.add(-20);
        testBuff = new Buff("testBuff", modifiers);
    }

    @Test
    void testConstructor() {
        assertEquals("testBuff", testBuff.getName());
        assertEquals(4, testBuff.getModifiers().size());

        assertEquals(3, testBuff.getOneModifier(0));
        assertEquals(-1, testBuff.getOneModifier(1));
        assertEquals(-11, testBuff.getOneModifier(2));
        assertEquals(-20, testBuff.getOneModifier(3));
    }
    
    @Test
    void testSetOneModifier() {
        testBuff.setOneModifier(2, 10);
        assertEquals(10, testBuff.getOneModifier(2));
    }

}