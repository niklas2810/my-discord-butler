package com.niklasarndt.discordbutler.modules.core.command;

import com.niklasarndt.discordbutler.modules.ButlerCommand;
import com.niklasarndt.discordbutler.modules.ButlerContext;
import com.niklasarndt.discordbutler.util.Emojis;

/**
 * Created by Niklas on 2020/07/26
 */
public class PingCommand extends ButlerCommand {

    public PingCommand() {
        super("ping", "Displays the ping to the Discord API", "pong");
    }

    @Override
    public void execute(ButlerContext context) {
        context.resultBuilder().success("`%s! %s | %dms`",
                context.command().equals("pong") ? "Ping" : "Pong",
                Emojis.TABLE_TENNIS, context.instance().getJda().getGatewayPing());
    }
}
