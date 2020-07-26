package com.niklasarndt.discordbutler.util;

import com.niklasarndt.testing.util.ButlerTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Niklas on 2020/07/26
 */
class ButlerUtilsTest extends ButlerTest {

    @Test
    void prettyPrintTime() {
        assertEquals("01 second", ButlerUtils.prettyPrintTime(1_000));
        assertEquals("01 minute", ButlerUtils.prettyPrintTime(60_000));
        assertEquals("01 hour", ButlerUtils.prettyPrintTime(3_600_000));
        assertEquals("01 day", ButlerUtils.prettyPrintTime(86_400_000));
        assertEquals("01 minute, 01 second", ButlerUtils.prettyPrintTime(61_000));
        assertEquals("01 minute, 02 seconds", ButlerUtils.prettyPrintTime(62_000));
        assertEquals("02 minutes, 01 second", ButlerUtils.prettyPrintTime(121_000));
        assertEquals("01 day, 05 hours, 35 minutes, 12 seconds",
                ButlerUtils.prettyPrintTime(106_512_000));
    }

    @Test
    void trimString() {
        String text = "Hello, World!";
        assertEquals(".", ButlerUtils.trimString(text, 1));
        assertEquals("...", ButlerUtils.trimString(text, 3));
        assertEquals("H...", ButlerUtils.trimString(text, 4));
        assertEquals("Hello...", ButlerUtils.trimString(text, 8));
        assertEquals("Hello, W...", ButlerUtils.trimString(text, 11));
        assertEquals(text, ButlerUtils.trimString(text, text.length()));
    }

    @Test
    void parseInt() {
        assertEquals(1, ButlerUtils.parseInt("1", -1));
        assertEquals(1, ButlerUtils.parseInt("0001", -1));
        assertEquals(-1, ButlerUtils.parseInt("abc", -1));
        assertEquals(-1, ButlerUtils.parseInt(Integer.MAX_VALUE + "0", -1));
    }
}