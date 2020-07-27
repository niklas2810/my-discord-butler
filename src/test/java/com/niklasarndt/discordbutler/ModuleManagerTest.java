package com.niklasarndt.discordbutler;

import com.niklasarndt.discordbutler.modules.ButlerCommand;
import com.niklasarndt.discordbutler.modules.ButlerModule;
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

        assertNotNull(HEARTBEAT); //Should be populated by FakeModule
        Optional<ButlerModule> fake = instance.getModule("fake");
        assertTrue(fake.isPresent());
        assertEquals(1, fake.get().getCommands().size());

        Optional<ButlerCommand> fakeCmd = instance.findCommand("fake");
        assertTrue(fakeCmd.isPresent());
        assertEquals(fake.get(), fakeCmd.get().module());

        instance.unloadAll();
        assertNotNull(HEARTBEAT); //Should be populated by FakeModule
    }

    @Test
    public void testExecution() {
        ModuleManager instance = new ModuleManager(null);
        instance.loadAll();

        ResultBuilder result = instance.execute("fake", null);
        assertEquals("test", result.produceString());
    }


}