package com.niklasarndt.discordbutler.modules.fun.command;

import com.niklasarndt.discordbutler.modules.ButlerCommand;
import com.niklasarndt.discordbutler.modules.ButlerContext;

/**
 * Created by Niklas on 2020/07/26
 */
public class GimmeCommand extends ButlerCommand {

    public GimmeCommand() {
        super("gimme", 1, 10,
                "Let the butler serve you something delicious (e.g. gimme sausage).", "give");
    }

    @Override
    public void execute(ButlerContext context) {
        context.resultBuilder().success("Of course, please enjoy your `%s`.",
                String.join(" ", context.args()).trim());
    }
}
