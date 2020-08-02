package com.niklasarndt.discordbutler.scheduler;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


/**
 * Created by Niklas on 2020/08/01.
 */
public class ScheduleManagerTest {

    public static boolean VISITED = false;

    @Test
    public void testScheduleManager() {
        ScheduleManager manager = new ScheduleManager(null);

        ScheduledTask task = manager.schedule("test", () -> {
            ScheduleManagerTest.VISITED = true;
        }, 50);

        assertFalse(task.shouldBeExecuted());
        assertEquals("01 second", task.getFancyTimeUntilExecution());
        assertFalse(task.wasExecuted());
        assertFalse(task.wasSuccessfullyExecuted());
        assertFalse(task.hasFailed());
        assertEquals(1, task.getIndex());

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(manager.getScheduledTasks().isEmpty());
        assertTrue(VISITED);
        assertTrue(task.wasSuccessfullyExecuted());
        assertTrue(task.shouldBeExecuted());

        assertTrue(manager.getFailedTasks(false).isEmpty());
    }

    @Test
    public void testFailingManager() {
        ScheduleManager manager = new ScheduleManager(null);
        ScheduledTask task = manager.schedule("Test", () -> {
            throw new NullPointerException("test exception");
        }, 50);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(task.wasExecuted());
        assertTrue(task.hasFailed());
        assertFalse(manager.getFailedTasks(false).isEmpty());
        assertFalse(manager.getFailedTasks(false).isEmpty());
        assertEquals("Test", manager.getFailedTasks(false).get(0).getName());
        System.out.println(manager.getFailedTasks(false).get(0).getTimeSinceExecution());
        assertNotEquals(0, manager.getFailedTasks(true)
                .get(0).getTimeSinceExecution());
        assertTrue(manager.getFailedTasks(false).isEmpty());

        manager.shutdown();
        assertTrue(manager.isShutdown());
    }
}
