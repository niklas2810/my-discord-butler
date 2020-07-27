package com.niklasarndt.discordbutler.modules.broken;

import com.niklasarndt.discordbutler.modules.ButlerModule;

/**
 * Created by Niklas on 2020/07/27.
 */
public class BrokenModule extends ButlerModule {

    // That's an illegal constructor (should have no parameters).
    public BrokenModule(String emoji, String name, String displayName,
                        String description, String version) {
        super(emoji, name, displayName, description, version);
    }
}
