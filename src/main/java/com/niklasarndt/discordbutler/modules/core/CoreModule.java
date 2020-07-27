package com.niklasarndt.discordbutler.modules.core;

import com.niklasarndt.discordbutler.modules.ButlerModule;
import com.niklasarndt.discordbutler.util.Emojis;

/**
 * Created by Niklas on 2020/07/25.
 */
public class CoreModule extends ButlerModule {

    public CoreModule() {
        super(Emojis.TOOLS, "core", "Core Functionality",
                "All basic features are implemented in this module.", "1.0");
    }
}
