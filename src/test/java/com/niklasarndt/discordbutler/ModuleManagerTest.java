package com.niklasarndt.discordbutler;

import com.niklasarndt.discordbutler.enums.ResultType;
import com.niklasarndt.discordbutler.modules.ButlerCommand;
import com.niklasarndt.discordbutler.modules.ButlerModule;
import com.niklasarndt.discordbutler.modules.fake.FakeModule;
import com.niklasarndt.discordbutler.util.ResultBuilder;
import com.niklasarndt.testing.util.ButlerTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Optional;

/**
 * Created by Niklas on 2020/07/27.
 */
public class ModuleManagerTest extends ButlerTest {

    public static String HEARTBEAT;

    @Test
    public void testRegistration() {
        HEARTBEAT = null;
        ModuleManager instance = new ModuleManager(null);
        instance.loadAll();

        assertFalse(instance.getModule("broken").isPresent());
        assertNotNull(HEARTBEAT); //Should be populated by FakeModule
        assertThrows(IllegalStateException.class, () ->
                instance.registerModule(new FakeModule()));

        Optional<ButlerModule> fake = instance.getModule("fake");
        assertTrue(fake.isPresent());
        assertEquals(2, fake.get().getCommands().size());

        Optional<ButlerCommand> fakeCmd = instance.findCommand("fake");
        assertTrue(fakeCmd.isPresent());
        assertEquals(fake.get(), fakeCmd.get().module());

        int oldCount = instance.getModules().size();

        instance.unregisterModule("fake");
        assertEquals(oldCount - 1, instance.getModules().size());

        instance.unloadAll();
        assertNotNull(HEARTBEAT); //Should be populated by FakeModule
    }

    @Test
    public void testExecution() {
        ModuleManager instance = new ModuleManager(null);
        instance.loadAll();

        ResultBuilder result = instance.execute("fake", null);
        assertEquals("test", result.produceString());

        //Error checks
        assertEquals(ResultType.ERROR, instance.execute("fake 1 2", null).getType());
        assertEquals(ResultType.ERROR, instance.execute("fakemin", null).getType());
        assertEquals(ResultType.ERROR, instance.execute("throw", null).getType());
        assertEquals(ResultType.NOT_FOUND, instance.execute("abc", null).getType());
    }


}