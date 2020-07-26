package com.niklasarndt.discordbutler.modules.core.command;

import com.niklasarndt.discordbutler.modules.ButlerCommand;
import com.niklasarndt.discordbutler.modules.ButlerContext;
import com.niklasarndt.discordbutler.util.ButlerUtils;

/**
 * Created by Niklas on 2020/07/25
 */
public class UptimeCommand extends ButlerCommand {

    public UptimeCommand() {
        super("uptime", 0, 0, "Displays the uptime of the bot.");
    }

    @Override
    public void execute(ButlerContext context) {
        long runtime = System.currentTimeMillis() - context.instance().getStartupTimestamp();
        String out = ButlerUtils.prettyPrintTime(runtime);
        context.resultBuilder().setOutput("Uptime: " + out + ".");
    }
}
