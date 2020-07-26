package com.niklasarndt.discordbutler.modules;

import com.niklasarndt.testing.util.ButlerTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by Niklas on 2020/07/26.
 */
class ButlerModuleInformationTest extends ButlerTest {

    private ButlerModuleInformation shortInfo = new ButlerModuleInformation("test",
            "test module");

    @Test
    void generateTitle() {
        assertEquals(ButlerModuleInformation.DEFAULT_EMOJI + " **test** (_test_)",
                shortInfo.generateTitle());
    }

    @Test
    void appliesLimits() {
        assertThrows(NullPointerException.class, () -> new ButlerModuleInformation(null));
        assertThrows(IllegalArgumentException.class, () -> new ButlerModuleInformation(""));
        assertThrows(IllegalArgumentException.class, () -> new ButlerModuleInformation("abc", ""));
        assertThrows(IllegalArgumentException.class, () ->
                new ButlerModuleInformation("abc", "abc",
                        null, null, null));
    }
}