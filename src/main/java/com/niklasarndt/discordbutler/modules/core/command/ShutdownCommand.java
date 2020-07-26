package com.niklasarndt.discordbutler.modules.core.command;

import com.niklasarndt.discordbutler.modules.ButlerCommand;
import com.niklasarndt.discordbutler.modules.ButlerContext;

/**
 * Created by Niklas on 2020/07/26.
 */
public class ShutdownCommand extends ButlerCommand {

    public ShutdownCommand() {
        super("shutdown", "Closes the bot application.", "poweroff", "exit");
    }

    @Override
    public void execute(ButlerContext context) {
        context.resultBuilder().success("The application will terminate in 5 seconds.");
        context.instance().shutdown(0);
    }
}
