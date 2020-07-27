package com.niklasarndt.discordbutler.modules.fake.command;

import com.niklasarndt.discordbutler.modules.ButlerCommand;
import com.niklasarndt.discordbutler.modules.ButlerContext;

/**
 * Created by Niklas on 2020/07/27.
 */
public class FakeCommand extends ButlerCommand {

    public FakeCommand() {
        super("fake");
    }

    @Override
    public void execute(ButlerContext context) {
        context.resultBuilder().success("test");
    }
}
