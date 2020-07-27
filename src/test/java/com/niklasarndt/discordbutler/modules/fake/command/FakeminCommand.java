package com.niklasarndt.discordbutler.modules.fake.command;

import com.niklasarndt.discordbutler.modules.ButlerCommand;
import com.niklasarndt.discordbutler.modules.ButlerContext;

/**
 * Created by Niklas on 2020/07/27.
 */
public class FakeminCommand extends ButlerCommand {

    public FakeminCommand() {
        super("fakemin", 1, 2);
    }

    @Override
    public void execute(ButlerContext context) {
        context.resultBuilder().success("test");
    }
}
