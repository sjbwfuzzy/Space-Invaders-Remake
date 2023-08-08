package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BuffTest {
    private Buff testBuff;

    @BeforeEach
    void runBefore() {
        testBuff = new Buff(0, 0, 0);
    }

    @Test
    void testConstructor() {
        testBuff = new Buff(0, 0, 0);
        assertEquals("Increase Max Health", testBuff.getName());
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