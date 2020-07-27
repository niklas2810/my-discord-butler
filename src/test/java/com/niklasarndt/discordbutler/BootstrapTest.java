package com.niklasarndt.discordbutler;

import com.niklasarndt.testing.util.ButlerTest;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

/**
 * Created by Niklas on 2020/07/27.
 */
class BootstrapTest extends ButlerTest {

    @Test
    public void testMain() {
        assertDoesNotThrow(() -> Bootstrap.main(new String[]{"0", "1"}));
    }
}
