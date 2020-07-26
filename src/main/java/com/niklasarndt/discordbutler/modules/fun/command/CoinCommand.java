package com.niklasarndt.discordbutler.modules.fun.command;

import com.niklasarndt.discordbutler.modules.ButlerCommand;
import com.niklasarndt.discordbutler.modules.ButlerContext;

import java.util.Random;

/**
 * Created by Niklas on 2020/07/26
 */
public class CoinCommand extends ButlerCommand {

    public CoinCommand() {
        super("coin", "Flips a coin (heads, tails).", "flip");
    }

    @Override
    public void execute(ButlerContext context) {
        context.resultBuilder().setOutput(String.format("It's %s!", new Random().nextBoolean() ? "heads" : "tails"));
    }
}
