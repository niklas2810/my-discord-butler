package com.niklasarndt.discordbutler.util;

import com.niklasarndt.testing.util.ButlerTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Created by Niklas on 2020/07/25
 */
public class BuildInformationTest extends ButlerTest {

    @Test
    public void isSet() {
        logger.info(BuildInfo.NAME);
        logger.debug(BuildInfo.VERSION);

        assertNotEquals("UNKNOWN", BuildInfo.NAME);
        assertNotEquals("UNKNOWN", BuildInfo.DESCRIPTION);
        assertNotEquals("UNKNOWN", BuildInfo.VERSION);
        assertNotEquals("UNKNOWN", BuildInfo.TARGET_JDK);
        assertNotEquals("UNKNOWN", BuildInfo.TIMESTAMP);
        assertNotEquals("UNKNOWN", BuildInfo.URL);
    }

}