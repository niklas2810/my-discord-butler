package com.niklasarndt.discordbutler.modules.fake;

import com.niklasarndt.discordbutler.ModuleManagerTest;
import com.niklasarndt.discordbutler.modules.ButlerModule;
import com.niklasarndt.discordbutler.util.Emojis;

/**
 * Created by Niklas on 2020/07/27.
 */
public class FakeModule extends ButlerModule {

    public FakeModule() {
        super(Emojis.WASTEBASKET, "fake", null, null, "1");
    }

    @Override
    public void onStartup() {
        super.onStartup();
        ModuleManagerTest.HEARTBEAT = "Hello, World!";
    }

    @Override
    public void onShutdown() {
        super.onShutdown();
        ModuleManagerTest.HEARTBEAT = "Hello, World!";
    }
}
