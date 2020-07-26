package com.niklasarndt.discordbutler.modules.fun;

import com.niklasarndt.discordbutler.modules.ButlerModule;
import com.niklasarndt.discordbutler.util.Emojis;

/**
 * Created by Niklas on 2020/07/26
 */
public class FunModule extends ButlerModule {
    public FunModule() {
        super(Emojis.PARTYING_FACE, "fun", "Funny Features",
                "You don't really need this module, but it has some entertaining features.", "1.0");
    }

    @Override
    public void onStartup() {
        loadCommandsFromDefaultPackage(this);
    }
}
