package com.niklasarndt.discordbutler.modules.core.command;

import com.niklasarndt.discordbutler.modules.ButlerCommand;
import com.niklasarndt.discordbutler.modules.ButlerContext;

/**
 * Created by Niklas on 2020/07/26.
 */
public class ThrowCommand extends ButlerCommand {
    public ThrowCommand() {
        super("throw", 0, 0, "Throws an exception.");
    }

    @Override
    public void execute(ButlerContext context) {
        throw new NullPointerException("I guess this is what you expected.");
    }
}
