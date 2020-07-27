package com.niklasarndt.discordbutler.modules.core.command;

import com.niklasarndt.discordbutler.modules.ButlerCommand;
import com.niklasarndt.discordbutler.modules.ButlerContext;

/**
 * Created by Niklas on 2020/07/27.
 */
public class RestartCommand extends ButlerCommand {

    public RestartCommand() {
        super("restart", "Closes the application with an error exit code. " +
                "Docker might react with a restart of the container when configured accordingly.");
    }

    @Override
    public void execute(ButlerContext context) {
        context.resultBuilder().success("The application will terminate in 5 seconds.");
        context.instance().shutdown(1);
    }
}
