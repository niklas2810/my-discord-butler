package com.niklasarndt.discordbutler;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


/**
 * Created by Niklas on 2020/08/01.
 */
public class ScheduleManagerTest {

    public static boolean VISITED = false;

    @Test
    public void testScheduleManager() {
        ScheduleManager manager = new ScheduleManager(null);

        manager.schedule(() -> {
                    System.out.println("Hello");
                    ScheduleManagerTest.VISITED = true;
                },
                990);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(VISITED);
        manager.shutdown();
        assertTrue(manager.isShutdown());
    }
}
