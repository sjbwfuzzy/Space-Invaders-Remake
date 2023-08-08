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
        assertEquals(4, testBuff.getModifiers().size());
        assertEquals(0, testBuff.getType());
        assertEquals("Increase Max Health", testBuff.getName());
        assertEquals(3, testBuff.getOneModifier(0));
        assertEquals(0, testBuff.getOneModifier(1));
        assertEquals(0, testBuff.getOneModifier(2));
        assertEquals(0, testBuff.getOneModifier(3));
        testBuff = new Buff(1, 0, 0);
        assertEquals(1, testBuff.getType());
        assertEquals("Increase Bonus Attack", testBuff.getName());
        assertEquals(0, testBuff.getOneModifier(0));
        assertEquals(1, testBuff.getOneModifier(1));
        assertEquals(0, testBuff.getOneModifier(2));
        assertEquals(0, testBuff.getOneModifier(3));
        testBuff = new Buff(2, 0, 0);
        assertEquals(2, testBuff.getType());
        assertEquals("Increase Movement Speed", testBuff.getName());
        assertEquals(0, testBuff.getOneModifier(0));
        assertEquals(0, testBuff.getOneModifier(1));
        assertEquals(1, testBuff.getOneModifier(2));
        assertEquals(0, testBuff.getOneModifier(3));
        testBuff = new Buff(3, 0, 0);
        assertEquals(3, testBuff.getType());
        assertEquals("Increase Fire Rate", testBuff.getName());
        assertEquals(0, testBuff.getOneModifier(0));
        assertEquals(0, testBuff.getOneModifier(1));
        assertEquals(0, testBuff.getOneModifier(2));
        assertEquals(10, testBuff.getOneModifier(3));
    }
    
    @Test
    void testSetOneModifier() {
        testBuff.setOneModifier(2, 10);
        assertEquals(10, testBuff.getOneModifier(2));
    }

}